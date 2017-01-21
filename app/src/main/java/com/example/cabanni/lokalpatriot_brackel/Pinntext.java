package com.example.cabanni.lokalpatriot_brackel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cabanni on 25.12.16.
 */

public class Pinntext {
    private static DateFormat dateformater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private String kategorie;
    private String text;
    private String mysqlDate;
    private String formatedDate;
    private String user;
    private String mail;
    private Integer points;
    private String ueberschrift;
    private Integer id;


    /**
     *
     * @param text
     * @param user
     * @param mail

     * @param mysqlDate
     */
    public Pinntext(String ueberschrift, String text, String user, String mail, String mysqlDate, Integer id, String kategorie) {
        this.text = text;
        this.user = user;
        this.mail = mail;
        this.id = id;
        this.kategorie = kategorie;


        this.ueberschrift = ueberschrift;
        try {
            Date date = dateformater.parse(mysqlDate);
            SimpleDateFormat simpleD = new SimpleDateFormat();
            simpleD.applyPattern("dd.MM.yyyy");
            this.mysqlDate = simpleD.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public String getUeberschrift() {
        return ueberschrift;
    }

    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMysqlDate() {
        return mysqlDate;
    }

    public void setMysqlDate(String mysqlDate) {
        this.mysqlDate = mysqlDate;
    }

    public String getFormatedDate() {
        return formatedDate;
    }

    public void setFormatedDate(String formatedDate) {
        this.formatedDate = formatedDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }




}
