package com.mobine.vnews.module;

import java.security.Timestamp;

/**
 * Created by xuantang on 11/27/17.
 */

public class Notice {
    /**
     *  ID        INT AUTO_INCREMENT
     * PRIMARY KEY,
     * newsID    INT                                 NULL,
     * fromID    VARCHAR(20)                         NULL,
     * toID      VARCHAR(20)                         NULL,
     * content   TEXT                                NOT NULL,
     * timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
     */
    private int ID;
    private int newsID;
    private String fromID;
    private String toID;
    private String content;
    private Timestamp timestamp;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
