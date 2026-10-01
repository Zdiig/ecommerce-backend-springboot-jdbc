package com.dbw.spring_boot.model;

public class Mitarbeiter {
  private Long personalNr;
  private String vorname;
  private String nachname;
  private String email;
  private String passwort;

  public Long getPersonalNr() {
    return personalNr;
  }

  public void setPersonalNr(Long personalNr) {
    this.personalNr = personalNr;
  }

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswort() {
    return passwort;
  }

  public void setPasswort(String passwort) {
    this.passwort = passwort;
  }
}