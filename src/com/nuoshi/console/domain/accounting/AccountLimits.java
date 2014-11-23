package com.nuoshi.console.domain.accounting;

/**
 * Author:
 * CHEN Liang <alinous@gmail.com>
 * Date: 2010-1-13
 * Time: 15:46:47
 */
public class AccountLimits {
    
    public final static String FIELD_PREFERENCE="preference";
    public final static String FIELD_TOTAL  = "total";
    public final static String FIELD_URGENCE = "urgence";
    public final static String FIELD_FRESH = "fresh";
    public final static String FIELD_MONTH_PRICE = "monthprice";
    public final static String FIELD_SEASON_PRICE = "seasonprice";
    public final static String FIELD_MONTHPAY = "monthpay";
    public final static String FIELD_MONTHCARD = "monthcard";
    public final static String FIELD_SEASONCARD = "seasoncard";
    public final static String FIELD_NEWHOUSE = "newhouse";
    public final static String FIELD_SENDMONTH = "sendmonth";
    public final static String FIELD_SENDDAY = "sendday";

    private int type;
    private int preference;
    private int total;
    private int urgence;
    private int fresh;


    private boolean monthpay;
    private int monthprice;
    private int seasonprice;
    private int monthcard;
    private int seasoncard;
    private int sendmonth;
    private int sendday;

    public int getSendmonth() {
        return sendmonth;
    }

    public void setSendmonth(int sendmonth) {
        this.sendmonth = sendmonth;
    }

    public int getSendday() {
        return sendday;
    }

    public void setSendday(int sendday) {
        this.sendday = sendday;
    }


    public int getMonthprice() {
        return monthprice;
    }

    public void setMonthprice(int monthprice) {
        this.monthprice = monthprice;
    }

    public int getSeasonprice() {
        return seasonprice;
    }

    public void setSeasonprice(int seasonprice) {
        this.seasonprice = seasonprice;
    }

    public boolean isMonthpay() {
        return monthpay;
    }

    public void setMonthpay(boolean monthpay) {
        this.monthpay = monthpay;
    }


    public int getMonthcard() {
        return monthcard;
    }

    public void setMonthcard(int monthcard) {
        this.monthcard = monthcard;
    }

    public int getSeasoncard() {
        return seasoncard;
    }

    public void setSeasoncard(int seasoncard) {
        this.seasoncard = seasoncard;
    }


    public int getNewhouse() {
        return newhouse;
    }

    public void setNewhouse(int newhouse) {
        this.newhouse = newhouse;
    }

    private int newhouse;

   
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUrgence() {
        return urgence;
    }

    public void setUrgence(int urgence) {
        this.urgence = urgence;
    }

    public int getFresh() {
        return fresh;
    }

    public void setFresh(int fresh) {
        this.fresh = fresh;
    }
}
