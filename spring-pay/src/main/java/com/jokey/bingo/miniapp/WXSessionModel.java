package com.jokey.bingo.miniapp;

import java.io.Serializable;

/**
 * @author JokeyFeng
 * date:2018/12/17
 * project:spring-boot
 * package:com.jokey.bingo.miniapp
 * comment:
 */
public class WXSessionModel implements Serializable {
    private String session_key;
    private String openid;

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
