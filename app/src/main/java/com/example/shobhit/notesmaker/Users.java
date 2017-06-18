package com.example.shobhit.notesmaker;

/**
 * Created by shobhit on 17/6/17.
 */

public class Users {

    private String uname;

    private String password;

    Users(String uname,String password){
        this.uname = uname;
        this.password =password;
    }

    Users(){}

    //setters
    void setUname(String uname){
        this.uname=uname;
    }

    void setPassword(String password)
    {
        this.password=password;
    }

    //getters
    String getUname()
    {
        return this.uname;
    }

    String getPassword()
    {
        return this.password;
    }


}
