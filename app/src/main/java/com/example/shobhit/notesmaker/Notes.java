package com.example.shobhit.notesmaker;

/**
 * Created by shobhit on 17/6/17.
 */

public class Notes {

    public String uname;
    public String title;
    public String content;

    Notes(String uname, String title, String content)
    {
        this.uname=uname;
        this.title=title;
        this.content=content;
    }

    Notes(){}

    //setters
    void setUname(String uname)
    {
        this.uname=uname;
    }

    void setTitle(String title)
    {
        this.title=title;
    }

    void setContent(String content)
    {
        this.content=content;
    }

    //getters
    String getUname()
    {
        return this.uname;
    }

    String getTitle()
    {
        return this.title;
    }

    String getContent()
    {
        return this.content;
    }




}
