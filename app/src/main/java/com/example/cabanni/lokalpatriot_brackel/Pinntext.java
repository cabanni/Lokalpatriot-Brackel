package com.example.cabanni.lokalpatriot_brackel;

/**
 * Created by cabanni on 25.12.16.
 */

public class Pinntext {
    private String Kategorie;
    private String text;
    private String mysqlDate;
    private String formatedDate;
    private String user;
    private String mail;
    private Integer points;

    /**
     * @param text
     * @param user
     * @param mail
     * @param points
     * @param formatedDate
     */
    public Pinntext(String text, String user, String mail, Integer points, String formatedDate) {
        this.text = text;
        this.user = user;
        this.mail = mail;
        this.points = points;
        this.formatedDate = formatedDate;
    }


    /**
     * @param kategorie
     * @param text
     * @param formatedDate
     * @param user
     * @param mail
     * @param points
     */
    public Pinntext(String kategorie, String text, String formatedDate, String user, String mail, Integer points) {
        Kategorie = kategorie;
        this.text = text;
        this.formatedDate = formatedDate;
        this.user = user;
        this.mail = mail;
        this.points = points;
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
