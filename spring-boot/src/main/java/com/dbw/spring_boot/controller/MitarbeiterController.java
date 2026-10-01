package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Mitarbeiter;
import com.dbw.spring_boot.repository.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/mitarbeiter")
public class MitarbeiterController {

  @Autowired
  private MitarbeiterRepository mitarbeiterRepository;

  @GetMapping
  public Object getMitarbeiter(@RequestParam(required = false) Long id) {
    if (id != null) {
      return mitarbeiterRepository.findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    return mitarbeiterRepository.findAll();
  }

  @PostMapping
  public Mitarbeiter createMitarbeiter(@RequestBody Mitarbeiter m) {
    m.setPersonalNr(null);
    return mitarbeiterRepository.save(m);
  }

  @DeleteMapping(params = "id")
  public void deleteMitarbeiter(@RequestParam Long id) {
    mitarbeiterRepository.deleteById(id);
  }
}