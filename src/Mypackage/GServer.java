package Mypackage;

/**
 * Created by Dmitry on 04.12.2016.
 */

import java.net.*;
import java.io.*;
import java.util.*;

import static Mypackage.GServer.handlers;

public class GServer
{
    public static final int PORT = 8087;
    public static String message = "";
    public static boolean parsing;

    public static Vector handlers = new Vector();
    public static Vector players = new Vector();

    public GServer (int port) throws IOException
    {
        int number = 0;
        // создание серверного сокета
        ServerSocket server = new ServerSocket (port);
        new Broadcast();
        new Parce();
        // цикл ожидания сообщения
        while (true)
        {
            Socket client = server.accept ();
            System.out.println ("CONNECTED: " + client.getInetAddress ());
            ICSTQueueHandler handler = new ICSTQueueHandler(client, number++);
            handler.start ();
        }
    }

    public static void main (String args[]) throws IOException
    {
        new GServer (8087); //работа через порт 8087
    }
}

class ICSTQueueHandler extends Thread
{
    protected Socket s;
    public int number;
    protected DataInputStream i;
    protected DataOutputStream o;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    public MessageFromClient msg;


    public ICSTQueueHandler (Socket s, int number) throws IOException {
        this.s = s;
        this.number=number;
        i = new DataInputStream(s.getInputStream());
        o = new DataOutputStream(s.getOutputStream());
        oos = new ObjectOutputStream(s.getOutputStream());
        ois = new ObjectInputStream(s.getInputStream());
    }



    public void run ()
    {
        Player player = new Player(number, 0);
        GServer.players.addElement(player);
        handlers.addElement(this);
        String name = s.getInetAddress ().toString ();
        try {
            o.writeUTF("Hello packet: " + number);
            while (true)
            {
                msg = (MessageFromClient) ois.readObject();
                System.out.println(msg.A+"  "+msg.B);

                player = (Player) GServer.players.get(msg.number);
                player.A = msg.A;
                player.B = msg.B;
                GServer.players.setElementAt(player, msg.number);
                Thread.sleep(5);
            }
        } catch (IOException e) {
            e.printStackTrace ();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            handlers.removeElement (this);
            Player ply = new Player(number, number);
            GServer.players.setElementAt( ply, number);
            try {
                s.close ();
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }


}
    class Broadcast extends Thread {
        Thread broadcast;

        Broadcast() {
            broadcast = new Thread(this);
            broadcast.start();
        }

        public void run() {
            while (true) {
                if (!GServer.handlers.isEmpty()) {
                    Enumeration e = GServer.handlers.elements();
                    while (e.hasMoreElements()) {
                        ICSTQueueHandler c = (ICSTQueueHandler) e.nextElement();
                        if ((c != null) & (c.oos != null)) {
                            try {
                                synchronized (GServer.message) {
                                    if ((GServer.message != "") & (!GServer.parsing)) {
                                        c.oos.writeUTF(GServer.message);
                                        System.out.println(GServer.message);
                                        Thread.sleep(5);
                                        c.oos.flush();
                                        //System.out.println(GServer.message);
                                    }
                                }
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                c.stop();
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

class Parce extends Thread{
    Thread parce;
    Parce(){
        parce = new Thread(this);
        parce.start();
    }

    public void run(){
        while(true){
            synchronized (GServer.message) {
                GServer.parsing = true;
                for (int i = 0; i < GServer.players.size(); i++) {
                    Player pl = (Player) GServer.players.get(i);
                    GServer.message += pl.A;
                    GServer.message += ":";
                    GServer.message += pl.B;
                    GServer.message += ";";


                }
                //System.out.println(GServer.message);
                GServer.parsing = false;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GServer.message = "";
        }
    }
}