package com.example.admin.firebase_homework;

import java.io.Serializable;

/**
 * Created by Admin on 10/14/2017.
 */

public class JournalEntry implements Serializable {

    private String journalId;
    private String title;
    private String content;
    private long dateCreated;
    private long dateModified;

    public JournalEntry(){};
    public JournalEntry(String journalId, String title, String content, long dateCreated, long dateModified) {
        this.journalId = journalId;
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }
    @Override
    public String toString() {
        return this.getJournalId()+"-"+this.title;
    }
}
