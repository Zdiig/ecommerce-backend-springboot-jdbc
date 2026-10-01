package com.dbw.spring_boot.model;

import java.math.BigDecimal;

public class Bestellposition {
  private Long positionsId;
  private Long bestellungId;
  private String produktSku; // Maps to sku
  private Integer menge;
  private BigDecimal gesamtpreis;

  public Long getPositionsId() {
    return positionsId;
  }

  public void setPositionsId(Long positionsId) {
    this.positionsId = positionsId;
  }

  public Long getBestellungId() {
    return bestellungId;
  }

  public void setBestellungId(Long bestellungId) {
    this.bestellungId = bestellungId;
  }

  public String getProduktSku() {
    return produktSku;
  }

  public void setProduktSku(String produktSku) {
    this.produktSku = produktSku;
  }

  public Integer getMenge() {
    return menge;
  }

  public void setMenge(Integer menge) {
    this.menge = menge;
  }

  public BigDecimal getgesamtpreis() {
    return gesamtpreis;
  }

  public void setGesamtpreis(BigDecimal gesamtpreis) {
    this.gesamtpreis = gesamtpreis;
  }
}