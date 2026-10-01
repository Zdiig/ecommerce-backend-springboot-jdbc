package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Bestellposition;
import com.dbw.spring_boot.model.Produkt;
import com.dbw.spring_boot.repository.BestellpositionRepository;
import com.dbw.spring_boot.repository.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("/bestellpositionen")
public class BestellpositionController {

  @Autowired
  private BestellpositionRepository bestellpositionRepository;

  @Autowired
  private ProduktRepository produktRepository;

  private void calculatePrice(Bestellposition bp) {
    Produkt p = produktRepository.findById(bp.getProduktSku()).orElse(null);
    if (p != null && p.getPreis() != null && bp.getMenge() != null) {
      bp.setGesamtpreis(p.getPreis().multiply(BigDecimal.valueOf(bp.getMenge())));
    }
  }

  @GetMapping
  public Object getBestellpositionen(@RequestParam(required = false) Long id) {
    if (id != null) {
      Bestellposition bp = bestellpositionRepository.findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      calculatePrice(bp);
      return bp;
    }
    Iterable<Bestellposition> all = bestellpositionRepository.findAll();
    all.forEach(this::calculatePrice);
    return all;
  }

  @PostMapping
  public Bestellposition createBestellposition(@RequestBody Bestellposition bp) {
    return bestellpositionRepository.save(bp);
  }

  @DeleteMapping(params = "id")
  public void deleteBestellposition(@RequestParam Long id) {
    bestellpositionRepository.deleteById(id);
  }
}