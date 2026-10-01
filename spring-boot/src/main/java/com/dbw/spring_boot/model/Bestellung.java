package com.dbw.spring_boot.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Bestellung {
  private Long bestellungId;
  private Long kundeId;
  private Long personalNr; // Maps to mitarbeiterzuweis
  private LocalDateTime datum;
  private String status;
  private List<Map<String, Object>> positionen;

  public Long getBestellungId() {
    return bestellungId;
  }

  public void setBestellungId(Long bestellungId) {
    this.bestellungId = bestellungId;
  }

  public Long getKundeId() {
    return kundeId;
  }

  public void setKundeId(Long kundeId) {
    this.kundeId = kundeId;
  }

  public Long getPersonalNr() {
    return personalNr;
  }

  public void setPersonalNr(Long personalNr) {
    this.personalNr = personalNr;
  }

  public LocalDateTime getDatum() {
    return datum;
  }

  public void setDatum(LocalDateTime datum) {
    this.datum = datum;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<Map<String, Object>> getPositionen() {
    return positionen;
  }

  public void setPositionen(List<Map<String, Object>> positionen) {
    this.positionen = positionen;
  }
}