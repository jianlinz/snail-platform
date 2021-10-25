package indi.jl.sp.jwt.pojo;


import indi.jl.sp.jwt.enums.JwtType;

import java.io.Serializable;

/**
 * JWT规范 第一段头部结构
 */
public class JwtHeader implements Serializable {

    /**
     * 加密形式
     */
    private String alg;

    /**
     * JWT类型
     */
    private JwtType typ;

    public JwtHeader() {
    }


    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public JwtType getTyp() {
        return typ;
    }

    public void setTyp(JwtType typ) {
        this.typ = typ;
    }
}
