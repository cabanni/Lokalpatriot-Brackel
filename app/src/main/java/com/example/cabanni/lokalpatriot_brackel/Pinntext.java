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
    private String Kategorie;
    private String text;
    private String mysqlDate;
    private String formatedDate;
    private String user;
    private String mail;
    private Integer points;


    /**
     *
     * @param text
     * @param user
     * @param mail
     * @param points
     * @param mysqlDate
     */
    public Pinntext(String text, String user, String mail, Integer points, String mysqlDate) {
        this.text = text;
        this.user = user;
        this.mail = mail;
        this.points = points;
        try {
            Date date = dateformater.parse(mysqlDate);
            SimpleDateFormat simpleD = new SimpleDateFormat();
            simpleD.applyPattern("dd.MM.yyyy");
            this.mysqlDate = simpleD.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public String getKategorie() {
        return Kategorie;
    }

    public void setKategorie(String kategorie) {
        Kategorie = kategorie;
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
