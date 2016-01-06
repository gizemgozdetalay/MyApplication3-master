package com.example.gizem.myapplication;

/**
 * Created by Gizem on 03.01.2016.
 */
public class Contact {

    String name;
    String number;
    String email;
    String location;
    String incoming;
    String outgoing;
    int missed;
    int sendMess;
    int comeMes;



    public  Contact()
    {

    }

    public Contact(String name,String number,String email, String location, String incoming, String outgoing, int missed, int sendMess, int comeMes)
    {
        this.name=name;
        this.number=number;
        this.email=email;
        this.location=location;
        this.incoming=incoming;
        this.outgoing=outgoing;
        this.missed=missed;
        this.sendMess=sendMess;
        this.comeMes=comeMes;
    }

    public String getName()
    {
        return name;
    }
    public String getNumber()
    {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getIncoming() {
        return incoming;
    }


    public String getOutgoing() {
        return outgoing;
    }

    public int getMissed() {
        return missed;
    }

    public int getSendMess() {
        return sendMess;
    }

    public int getComeMes() {
        return comeMes;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public void setNumber(String number)
    {
        this.number=number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setIncoming(String incoming) {
        this.incoming = incoming;
    }

    public void setOutgoing(String outgoing) {
        this.outgoing = outgoing;
    }

    public void setMissed(int missed) {
        this.missed = missed;
    }

    public void setSendMess(int sendMess) {
        this.sendMess = sendMess;
    }

    public void setComeMes(int comeMes) {
        this.comeMes = comeMes;
    }
}
