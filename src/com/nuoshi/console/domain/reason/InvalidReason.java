package com.nuoshi.console.domain.reason;

/**
 * Created by IntelliJ IDEA.
 * User: pekky
 * Date: 2010-4-9
 * Time: 21:23:45
 */
public class InvalidReason {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    private int id;
    private String reason;
}
