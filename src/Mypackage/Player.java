package Mypackage;

import java.awt.*;

/**
 * Created by Dmitry on 04.12.2016.
 */
public class Player {
    protected int number;
    protected int A;
    protected int B;
    protected Color color;
    public Player(int number, int color){
        this.number=number;
        if (color==0){
            this.color=Color.red;
        }
        if (color==1){
            this.color=Color.blue;
        }
        if (color==2){
            this.color=Color.green;
        }
        if (color==3){
            this.color=Color.magenta;
        }
    }
}
