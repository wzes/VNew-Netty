package com.mobile.vnews.module;

import java.security.Timestamp;

/**
 * Created by xuantang on 11/27/17.
 */

public class Message {
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    private int floor;
    /**
     *  add after
     */
    private String title;
    private String fromImage;
    private String fromUsername;

//    /**
//     * Relation user for submitting
//     */
//    private String relationID;
//
//    public String getRelationID() {
//        return relationID;
//    }
//
//    public void setRelationID(String relationID) {
//        this.relationID = relationID;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFromImage() {
        return fromImage;
    }

    public void setFromImage(String fromImage) {
        this.fromImage = fromImage;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    /* --------------------------------------------- */

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
