package com.dbw.spring_boot.model;

import java.math.BigDecimal;

public class Produkt {
  private String sku;
  private String name;
  private BigDecimal preis;
  private Integer lagerbestand;
  private Long angelegtVon;

  public Produkt() {
  }

  public Produkt(String sku, String name, BigDecimal preis, Integer lagerbestand, Long angelegtVon) {
    this.sku = sku;
    this.name = name;
    this.preis = preis;
    this.lagerbestand = lagerbestand;
    this.angelegtVon = angelegtVon;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPreis() {
    return preis;
  }

  public void setPreis(BigDecimal preis) {
    this.preis = preis;
  }

  public Integer getLagerbestand() {
    return lagerbestand;
  }

  public void setLagerbestand(Integer lagerbestand) {
    this.lagerbestand = lagerbestand;
  }

  public Long getAngelegtVon() {
    return angelegtVon;
  }

  public void setAngelegtVon(Long angelegtVon) {
    this.angelegtVon = angelegtVon;
  }
}