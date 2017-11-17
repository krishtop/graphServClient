package Mypackage;

import java.io.Serializable;

/**
 * Created by Dmitry on 04.12.2016.
 */
public class MessageFromClient implements Serializable {
    protected int A;
    protected int B;
    protected int number;
    MessageFromClient(int number, int A, int B){
        this.number=number;
        this.A=A;
        this.B=B;
    }
}
