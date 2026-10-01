package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Produkt;
import com.dbw.spring_boot.repository.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/produkte")
public class ProduktController {

  @Autowired
  private ProduktRepository produktRepository;

  @GetMapping
  public Object getProdukte(@RequestParam(required = false) String sku) {
    if (sku != null) {
      return produktRepository.findById(sku)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produkt nicht gefunden"));
    }
    return produktRepository.findAll();
  }

  @PostMapping
  public Produkt createProdukt(@RequestBody Produkt produkt) {
    return produktRepository.save(produkt);
  }

  @DeleteMapping(params = "sku")
  public void deleteProdukt(@RequestParam String sku) {
    produktRepository.deleteById(sku);
  }

  @PutMapping(params = "sku")
  public Produkt updateLagerbestand(@RequestParam String sku, @RequestBody Produkt details) {
    Produkt p = produktRepository.findById(sku)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produkt nicht gefunden"));

    p.setLagerbestand(details.getLagerbestand());
    return produktRepository.save(p);
  }
}