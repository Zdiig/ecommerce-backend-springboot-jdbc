package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Adresse;
import com.dbw.spring_boot.model.Kunde;
import com.dbw.spring_boot.model.KundeAdresseDTO;
import com.dbw.spring_boot.repository.KundeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kunden")
public class KundeController {

  @Autowired
  private KundeRepository kundeRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<KundeAdresseDTO> getAdressenForKunde(Long kundeId) {
    String sql = "SELECT a.*, kha.typ FROM adresse a " +
        "JOIN kunde_hat_adressen kha ON a.adresse_id = kha.adresse_id " +
        "WHERE kha.kunde_id = ?";

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, kundeId);
    List<KundeAdresseDTO> result = new ArrayList<>();

    for (Map<String, Object> row : rows) {
      Adresse a = new Adresse(
          (Boolean) row.get("aktiv"),
          (String) row.get("strasse"),
          (String) row.get("hausnummer"),
          ((Number) row.get("plz")).longValue(),
          (String) row.get("ort"),
          (String) row.get("land"));
      a.setAdresseId(((Number) row.get("adresse_id")).longValue());
      result.add(new KundeAdresseDTO(a, (String) row.get("typ")));
    }
    return result;
  }

  @GetMapping
  public Object getKunden(@RequestParam(required = false) Long id, @RequestParam(required = false) String email) {
    if (id != null) {
      Kunde k = kundeRepository.findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      k.setAdressen(getAdressenForKunde(k.getKundeId()));
      return k;
    }
    if (email != null) {
      Kunde k = kundeRepository.findByEmail(email)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      k.setAdressen(getAdressenForKunde(k.getKundeId()));
      return k;
    }

    Iterable<Kunde> all = kundeRepository.findAll();
    all.forEach(k -> k.setAdressen(getAdressenForKunde(k.getKundeId())));
    return all;
  }

  @PostMapping
  public Kunde createKunde(@RequestBody Kunde k) {
    return kundeRepository.save(k);
  }

  @PutMapping(params = "id")
  public Kunde updateKunde(@RequestParam Long id, @RequestBody Kunde details) {
    Kunde k = kundeRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    k.setEmail(details.getEmail());
    k.setVorname(details.getVorname());
    k.setNachname(details.getNachname());
    k.setPasswort(details.getPasswort());
    return kundeRepository.save(k);
  }
}