package com.nuoshi.console.domain.message;

import java.sql.Timestamp;

/**
 * Author: CHEN Liang <alinous@gmail.com>
 * Date: 2009-9-3
 * Time: 11:06:26
 */
public class Message {

    public final static String INBOX = "in";
    public final static String OUTBOX = "out";
    public final static String SYSMSG = "sys";
    public final static String SYS_SENDER= "淘房网";


    public final static short TYPE_NORMAL = 0;
    public final static short TYPE_SYS  = 1;
    
    public int getSender() {

        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getSendernick() {
        if (type == TYPE_SYS) {
            return SYS_SENDER;
        } else {
            return sendernick;
        }
    }

    public void setSendernick(String sendernick) {
        this.sendernick = sendernick;
    }

    public String getReceivernick() {
        return receivernick;
    }

    public void setReceivernick(String receivernick) {
        this.receivernick = receivernick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getReadflag() {
        return readflag;
    }

    public void setReadflag(byte readflag) {
        this.readflag = readflag;
    }

    public Timestamp getCts() {
        return cts;
    }

    public void setCts(Timestamp cts) {
        this.cts = cts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndexid() {
        return indexid;
    }

    public void setIndexid(int indexid) {
        this.indexid = indexid;
    }


    public String getSenderhead() {
        return senderhead;
    }

    public void setSenderhead(String senderhead) {
        this.senderhead = senderhead;
    }

    public String getReceiverhead() {
        return receiverhead;
    }

    public void setReceiverhead(String receiverhead) {
        this.receiverhead = receiverhead;
    }


    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }


    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getRefcount() {
        return refcount;
    }

    public void setRefcount(int refcount) {
        this.refcount = refcount;
    }

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    protected int id;
    protected int sender;
    protected int receiver;
    protected String    sendernick;
    protected String    receivernick;
    protected String    senderhead;
    protected String    receiverhead;
    protected String  title;
    protected byte    readflag;
    protected Timestamp cts;
    protected int indexid;
    protected int messageid;
    protected String    content;
    protected String    folder;
    protected int refcount;
    protected short type = 0;

    protected int root=0;
    protected int parent=0;
}
