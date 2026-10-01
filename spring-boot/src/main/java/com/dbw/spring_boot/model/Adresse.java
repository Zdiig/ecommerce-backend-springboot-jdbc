package com.dbw.spring_boot.model;

public class Adresse {
  private Long adresseId;
  private Boolean aktiv;
  private String strasse;
  private String hausnummer;
  private Long plz;
  private String ort;
  private String land;

  public Adresse() {
  }

  public Adresse(Boolean aktiv, String strasse, String hausnummer, Long plz, String ort, String land) {
    this.aktiv = aktiv;
    this.strasse = strasse;
    this.hausnummer = hausnummer;
    this.plz = plz;
    this.ort = ort;
    this.land = land;
  }

  public Long getAdresseId() {
    return adresseId;
  }

  public void setAdresseId(Long adresseId) {
    this.adresseId = adresseId;
  }

  public Boolean getAktiv() {
    return aktiv;
  }

  public void setAktiv(Boolean aktiv) {
    this.aktiv = aktiv;
  }

  public String getStrasse() {
    return strasse;
  }

  public void setStrasse(String strasse) {
    this.strasse = strasse;
  }

  public String getHausnummer() {
    return hausnummer;
  }

  public void setHausnummer(String hausnummer) {
    this.hausnummer = hausnummer;
  }

  public Long getPlz() {
    return plz;
  }

  public void setPlz(Long plz) {
    this.plz = plz;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public String getLand() {
    return land;
  }

  public void setLand(String land) {
    this.land = land;
  }
}