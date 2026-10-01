package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Kunde;
import com.dbw.spring_boot.model.Mitarbeiter;
import com.dbw.spring_boot.repository.KundeRepository;
import com.dbw.spring_boot.repository.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

  @Autowired
  private MitarbeiterRepository mitarbeiterRepository;

  @Autowired
  private KundeRepository kundeRepository;

  @Autowired
  private KundeController kundeController;

  @PostMapping("/mitarbeiter")
  public ResponseEntity<Mitarbeiter> loginMitarbeiter(@RequestBody Map<String, Object> body) {
    Long id = Long.valueOf(body.get("personalNr").toString());
    String password = (String) body.get("passwort");

    Optional<Mitarbeiter> m = mitarbeiterRepository.findById(id);

    if (m.isPresent() && m.get().getPasswort().equals(password)) {
      m.get().setPasswort(null);
      return ResponseEntity.ok(m.get());
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mitarbeiter());
  }

  @PostMapping("/kunde")
  public ResponseEntity<Kunde> loginKunde(@RequestBody Map<String, Object> body) {
    String email = (String) body.get("email");
    String password = (String) body.get("passwort");

    Optional<Kunde> k = kundeRepository.findByEmail(email);

    if (k.isPresent() && k.get().getPasswort().equals(password)) {
      k.get().setAdressen(kundeController.getAdressenForKunde(k.get().getKundeId()));
      k.get().setPasswort(null);
      return ResponseEntity.ok(k.get());
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Kunde());
  }
}