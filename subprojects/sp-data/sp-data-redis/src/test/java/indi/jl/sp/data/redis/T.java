package indi.jl.sp.data.redis;

import java.io.Serializable;

public class T implements Serializable {
    public T(){

    }
    private static final long serialVersionUID = 6468926052770326495L;
    private String a;
    private String b;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
