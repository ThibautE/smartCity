package com.example.thibaut.smartcity.Model;

public class Close {
    private String time;

    private String day;

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getDay ()
    {
        return day;
    }

    public void setDay (String day)
    {
        this.day = day;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [time = "+time+", day = "+day+"]";
    }
}
