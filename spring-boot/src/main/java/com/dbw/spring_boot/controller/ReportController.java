package com.dbw.spring_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @GetMapping("/kunde/summe-anzahl-bestellungen")
  public List<Map<String, Object>> getKundenSumme() {
    return jdbcTemplate.queryForList("SELECT * FROM v_kunde_summe_anzahl_bestellungen");
  }

  @GetMapping("/produkt/verkaufszahlen")
  public List<Map<String, Object>> getProduktVerkaufszahlen() {
    return jdbcTemplate.queryForList("SELECT * FROM v_produkt_verkaufszahlen");
  }

  @GetMapping("/mitarbeiter/uebersicht")
  public List<Map<String, Object>> getMitarbeiterUebersicht() {
    return jdbcTemplate.queryForList("SELECT * FROM v_mitarbeiter_uebersicht");
  }

  @GetMapping("/mitarbeiter/bestellstatus-uebersicht")
  public List<Map<String, Object>> getMitarbeiterBestellstatus() {
    return jdbcTemplate.queryForList("SELECT * FROM v_mitarbeiter_bestellstatus_uebersicht");
  }
}