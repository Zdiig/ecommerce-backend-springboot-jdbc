package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Adresse;
import com.dbw.spring_boot.repository.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/adressen")
public class AdresseController {

  @Autowired
  private AdresseRepository adresseRepository;

  @GetMapping
  public Object getAdressen(@RequestParam(required = false) Long id) {
    if (id != null) {
      return adresseRepository.findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    return adresseRepository.findAll();
  }

  @PostMapping
  public Adresse createAdresse(@RequestBody Adresse adresse) {
    return adresseRepository.save(adresse);
  }

  @PutMapping(params = "id")
  public Adresse updateAdresse(@RequestParam Long id, @RequestBody Adresse details) {
    Adresse a = adresseRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    a.setAktiv(details.getAktiv());
    a.setStrasse(details.getStrasse());
    a.setHausnummer(details.getHausnummer());
    a.setPlz(details.getPlz());
    a.setOrt(details.getOrt());
    a.setLand(details.getLand());

    return adresseRepository.save(a);
  }
}