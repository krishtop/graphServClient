package Mypackage;

/**
 * Created by Dmitry on 04.12.2016.
 */
import com.sun.security.ntlm.Server;

import java.awt.*;
import java.applet.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class GClient  extends Thread {
    protected static int count_pl=0;
    protected static boolean fr=false;
    protected static boolean frn=false;
    protected static int count_player=0;
    //protected static Polygon[] mas;
    public static Vector players = new Vector();
    protected static Vector mas = new Vector();
    public static int number;
    protected static String line;
    protected static DataInputStream inp;
    protected static DataOutputStream outp;
    public static ObjectInputStream ois;
    public static ObjectOutputStream oos;
    protected static int size1;


    protected static String server;//хост, на котором работает сервер

    static final int ServerPort = 8087; //порт сервера для обмена текстом

    protected static Socket withServer; //сокеты для работы с серверами

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        server="127.0.0.1";
        withServer = new Socket(server, ServerPort);
        System.out.println("соединение с сервером: " + withServer.getInetAddress() + " установлено");
        inp = new DataInputStream(withServer.getInputStream());
        outp = new DataOutputStream(withServer.getOutputStream());
        oos = new ObjectOutputStream(withServer.getOutputStream());
        ois = new ObjectInputStream(withServer.getInputStream());

        line = inp.readUTF();


        if (line.startsWith("Hello packet")) {
            number = Integer.parseInt(String.valueOf(line.charAt(14)));
        }
        for (int i = 0; i <= 4; i++) {
            Player play = new Player(number, i);
            players.addElement(play);

        }

        new MyGraph();
        while (true) {
            String message = ois.readUTF();
            if (message.length()>size1){
                fr=true;
            }
           // System.out.println(message);
            if (!message.equals("")) {
                parceStr(message);
            }
            Thread.sleep(5);
        }
    }

    public static void parceStr(String str) {
        if (str!="") {
            String[] masStr;
            masStr = str.split(";");
            String[] masStrCifri;

            for (int i = 0; i < masStr.length; i++) {
                masStrCifri = masStr[i].split(":");
                Player pl = new Player(i, i);
                pl.A = Integer.parseInt(masStrCifri[0]);
                pl.B = Integer.parseInt(masStrCifri[1]);
                //System.out.println(pl.A+"   "+pl.B);
                players.setElementAt(pl, i);
                //parsing=true;
                if ((pl.A!=0)&&(pl.B!=0)){
                    frn=true;
                }
                else
                    frn=false;

            }

            size1=str.length();
           // System.out.println("end");
        }
    }

   public static class MyGraph extends Thread{
        //public static final int CENTER_X = 1280/2-50;
        //public static final int CENTER_Y = 720/2-50;


        //public static final float   UPDATE_RATE     = 50.0f; //сколько раз в секунду считать физику
        //public static final float   UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE; // интервал между updatами
        //public long          IDLE_TIME       = 1;    //остановка потока

        Graphics2D graphics;
        //Input                           input;

        private boolean              running;
        private Thread               gameThread;

        Thread thread;
        //ActionEvent ES;

        public MyGraph() throws IOException  {
            MyFrame.create("GRAPH");

            thread = new Thread(this);
            thread.start();

        }

       public void run() {


            while(true){


                if (MyFrame.testBut()) {

                    try {
                        update();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (fr&frn){

                    render();
                    fr=false;
                    frn=false;
                }

                if (MyFrame.testBut()){///45;20
                    //try {
                      //  thread.sleep(80);
                    //} catch (InterruptedException e) {
                     //   e.printStackTrace();
                   // }
                    render();
                    MyFrame.fl=false;
                }
                try {
                    thread.sleep(185);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }

        public static void update() throws IOException, InterruptedException {              //рассчет параметров

            if(true){

                MessageFromClient msg = new MessageFromClient(number,MyFrame.getA(),MyFrame.getB() );
                oos.writeObject(msg);


                Thread.sleep(5);
                oos.flush();
            }


        }

       public static void render()  {              //прорисовка следующего события

          MyFrame.clear();


               if (((Player) players.get(count_player)).A!=0) {
                   Polygon p = new Polygon();
                   //System.out.println(((Player) players.get(i)).A);
                   for (int x = -500; x <= 500; x++) {
                       p.addPoint(x + 750, 500 - (int) (50 * MyFrame.f(((x / 100.0) * 2 * Math.PI), ((Player) players.get(count_player)).A, ((Player) players.get(count_player)).B)));
                       ///if((int) ((50*f((x / 100.0) * 2* Math.PI)==)
                   }
                   // System.out.println(p.npoints);

                   mas.add(count_player, p);
                   count_player++;
                   //System.out.println(mas[i].npoints);
               }



         MyFrame.refresh(mas);
       }

     public void start(){
            if(running)
                return;
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }
   //protected void finalize() throws IOException {
     //  MessageFromClient msg = new MessageFromClient(number, 1, 2);
       //oos.writeObject(msg);
      //oos.flush();
    ///}
}


