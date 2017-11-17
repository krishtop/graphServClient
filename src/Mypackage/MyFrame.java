package Mypackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public abstract class MyFrame  {

    private static JLabel labA= new JLabel("Input parametr A");
    private static JLabel labB=new JLabel("Input parametr B");
    private static JTextField infA = new JTextField();
    private static JTextField infB = new JTextField();
    private static JButton start = new JButton("Paint");
    public static boolean fl=false;
    public static boolean st=false;

    private static boolean created = false; //проверка соднания окна
    private static JFrame window;   //окно

    private static JPanel pane;
    //private static Polygon polygon;


    private static int[] bufferData;

    private static int clearColor;


    public static boolean testBut(){
        if (fl) {
            return true;
        }
        else
            return false;
    }
    public static int A;
    public static int B;
    protected boolean trash=false;
    public static void setAB(int parA, int parB)
    {
        A=parA;
        B=parB;
    }
    public static double f(double x, int A, int B) {
        return A*Math.cos(x)+ B*Math.sin(x);
    }


    public static void create(String title)  //создание окна
    {
        if(created)
            return;

        window = new JFrame(title); //создаем окно
        //window.setLayout(new BorderLayout());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//вырубать прогу после закрытия окна\
        //window.setSize(heigth, width);



        //создаем лист и задаем его параметры

        pane=new DrawSine();
        pane.setLayout(null);

        labA.setBounds(10,30,160,20);
        labB.setBounds(10,90,160,20);
        infA.setBounds(10,60,80,20);
        infB.setBounds(10,120,80,20);
        pane.add(labA);
        pane.add(labB);
        pane.add(infA);
        pane.add(infB);
        window.setBackground(Color.WHITE);
       window.add(pane, BorderLayout.CENTER);
        //mf.add(start);
        window.add(start, BorderLayout.SOUTH);
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                fl = true;
                //String textA=infA.getText();
                //System.out.println(textA);
                //String textB=infB.getText();
                //MyFrame.setAB(Integer.parseInt(textA),Integer.parseInt(textB));
               // try {
                //    GClient.MyGraph.update();
                //} catch (IOException e) {
                 //   e.printStackTrace();
                //} catch (InterruptedException e) {
                 //   e.printStackTrace();
                //}

            }
        });
  //      Dimension size = new Dimension(width, heigth);
//        content.setPreferredSize(size);

       // window.setResizable(false);     //запрет изменения параметров окна пользователем
        //window.getContentPane().add(content);        //возврат только внутренней части окна без обрезания scroollам и кнопками
        //window.pack();      //подгон окна под контент
        window.setSize(1920, 1000);
        window.setLocationRelativeTo(null);  //окно по середине
        window.setVisible(true);   //разрешить увидеть окно



        //window.add(pane);
        created = true;

    }


    public static void refresh(Vector polygon ){
        pane=new DrawSine(polygon);
        pane.setLayout(null);
        labA.setBounds(10,30,160,20);
        labB.setBounds(10,90,160,20);
        infA.setBounds(10,60,80,20);
        infB.setBounds(10,120,80,20);
        pane.add(labA);
        pane.add(labB);
        pane.add(infA);
        pane.add(infB);
        window.setBackground(Color.WHITE);
        window.add(pane, BorderLayout.CENTER);
        //mf.add(start);
        window.add(start, BorderLayout.SOUTH);

        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                fl=true;
              //  try {
               //     GClient.MyGraph.update();
               // } catch (IOException e) {
                //    e.printStackTrace();
                //} catch (InterruptedException e) {
                 //   e.printStackTrace();
                //}
            }
        });
        window.add(pane);
    }

    public static void clear(){
        //window.clear();   //заполение массива значениями клиркалоp

    }



    public static int getA(){
        return Integer.parseInt(infA.getText());
    }

    public static int getB(){
        return Integer.parseInt(infB.getText());
    }





    public static void destroy(){
        if(!created)
            return;
        window.dispose();
    }

    public static void setTitle(String title){
        window.setTitle(title);
    }



}

class DrawSine extends JPanel {

    public static Vector mass;
    public static int A;
    public static int B;
    protected boolean trash=false;
    public DrawSine(Vector pol){
        mass=pol;
        setLayout(null);
        //System.out.println(mass.length);
    }

    public DrawSine(){
        setLayout(null);
    }
    public static void setAB(int parA, int parB)
    {
        A=parA;
        B=parB;
    }



    protected void paintComponent(Graphics g/*, Polygon[] pol*/) {
        super.paintComponent(g);
//оси
        g.drawLine(10, 500, 1510, 500);
        g.drawLine(750, 10, 750, 1010);
//стрелки
        g.drawLine(1510, 500, 1500, 490);
        g.drawLine(1510, 500, 1500, 510);
        g.drawLine(750, 10, 740, 20);
        g.drawLine(750, 10, 760, 20);
        g.drawString("0", 740, 520);
        g.drawString("X", 1510, 520);
        g.drawString("Y", 730, 30);
        for (int i = 1; i < 9; i++) {
            g.drawLine((int) (750 + 50 * i), 495, (int) (750 + 50 * i), 505);
            g.drawLine((int) (750 - 50 * i), 495, (int) (750 - 50 * i), 505);
            g.drawString(Integer.toString(i) + "\u03c0", (int) (745 + 50 * i), 520);
            g.drawString("-" + Integer.toString(i) + "\u03c0", (int) (745 - 50 * i), 520);


        }
        for (int i = 1; i < 6; i++) {
            g.drawLine(745, (int) (500 - i * 50), 755, (int) (500 - i * 50));
            g.drawLine(745, (int) (500 + i * 50), 755, (int) (500 + i * 50));
            g.drawString(Integer.toString(i), 730, (int) (505 - i * 50));
            g.drawString("-" + Integer.toString(i), 730, (int) (505 + i * 50));
        }
       if (mass != null) {
            for (int i = 0; i < mass.size(); i++) {
                if ((i == 0)&(((Polygon) mass.get(i)).npoints!=0)) {
                    g.setColor(Color.red);
                    System.out.println(i);
                }
                if ((i == 1)&(((Polygon) mass.get(i)).npoints!=0)) {
                    g.setColor(Color.blue);
                    System.out.println(i);
                }
                if ((i == 2)&(((Polygon) mass.get(i)).npoints!=0)) {
                    g.setColor(Color.green);
                    System.out.println(i);
                }
                if ((i == 3)&(((Polygon) mass.get(i)).npoints!=0)) {
                    g.setColor(Color.magenta);
                    System.out.println(i);
                }
                g.drawPolyline(((Polygon) mass.get(i)).xpoints,((Polygon) mass.get(i)).ypoints,((Polygon) mass.get(i)).npoints);
            }

        }
    }
}

