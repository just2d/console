package com.nuoshi.console.domain.agent;

import java.sql.Timestamp;

/**
 * Author:
 * CHEN Liang <alinous@gmail.com>
 * Date: 2010-1-16
 * Time: 13:26:16
 */
public class AgentStats {
    private int id;
    private int preference;
    private int total;
    private int urgence;

    public int getNewhouse() {
        return newhouse;
    }

    public void setNewhouse(int newhouse) {
        this.newhouse = newhouse;
    }

    private int newhouse;
    private int fresh;
    private Timestamp uts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Timestamp getUts() {
        return uts;
    }

    public void setUts(Timestamp uts) {
        this.uts = uts;
    }

    public int incPreference() {
        return this.incPreference(1);
    }

    public int incPreference(int cnt) {
        if (cnt > 0) {
            return (this.preference += cnt);
        }
        else {
            return this.decPreference(-cnt);
        }
    }

    public int decPreference() {
        return decPreference(1);
    }

    public int decPreference(int n) {
        return (this.preference > n) ? (this.preference -=n) : (this.preference = 0);
    }

    public int incTotal() {
        return ++ this.total;
    }

    public int incTotal(int n) {
        if (n  > 0) {
            return (this.total += n);
        }
        else {
            return this.decTotal( -n );
        }
    }

    public int decTotal() {
        return this.decTotal(1);
    }

    public int decTotal(int cnt) {
        return (this.total > cnt) ? (this.total -= cnt) : (this.total = 0);
    }

    public int incUrgence() {
        return this.incUrgence(1);
    }

    public int incUrgence(int cnt) {
        if (cnt > 0) {
            return (this.urgence += cnt);
        }
        else {
            return this.decUrgence(-cnt);
        }
    }

    public int decUrgence() {
        return decUrgence(1);
    }

    public int decUrgence(int cnt) {
        return (this.urgence >  cnt) ? (this.urgence -= cnt) : (this.urgence = 0);
    }

    public int incNewhouse() {
        return this.incNewhouse(1);
    }

    public int incNewhouse(int cnt) {
        if (cnt > 0) {
            return (this.newhouse += cnt);
        }
        else {
            return this.decUrgence(-cnt);
        }
    }

    public int decNewhouse() {
        return decNewhouse(1);
    }

    public int decNewhouse(int cnt) {
        return (this.newhouse >  cnt) ? (this.newhouse -= cnt) : (this.newhouse = 0);
    }

    public int incFresh() {
        return incFresh(1);
    }

    public int incFresh(int cnt) {
        if (cnt > 0) {
            return (this.fresh += cnt);
        }
        else {
            return this.decFresh(-cnt);
        }

    }

    public int decFresh() {
        return this.decFresh(1);
    }

    public int decFresh(int cnt) {
        return (this.fresh > cnt) ? (this.fresh -= cnt) : (this.fresh =0);
    }

}
