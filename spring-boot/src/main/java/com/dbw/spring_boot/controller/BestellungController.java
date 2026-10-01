package com.dbw.spring_boot.controller;

import com.dbw.spring_boot.model.Bestellposition;
import com.dbw.spring_boot.model.Bestellung;
import com.dbw.spring_boot.model.Produkt;
import com.dbw.spring_boot.repository.BestellpositionRepository;
import com.dbw.spring_boot.repository.BestellungRepository;
import com.dbw.spring_boot.repository.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bestellungen")
public class BestellungController {

  @Autowired
  private BestellungRepository bestellungRepository;
  @Autowired
  private BestellpositionRepository bestellpositionRepository;
  @Autowired
  private ProduktRepository produktRepository;

  private void attachPositions(Bestellung b) {
    List<Bestellposition> positions = bestellpositionRepository.findByBestellungId(b.getBestellungId());
    List<Map<String, Object>> posList = new ArrayList<>();

    for (Bestellposition bp : positions) {
      Produkt p = produktRepository.findById(bp.getProduktSku()).orElse(new Produkt());
      Map<String, Object> posMap = new HashMap<>();

      BigDecimal total = p.getPreis() != null ? p.getPreis().multiply(BigDecimal.valueOf(bp.getMenge()))
          : BigDecimal.ZERO;

      Map<String, Object> productDetails = new HashMap<>();
      productDetails.put("sku", p.getSku());
      productDetails.put("name", p.getName());
      productDetails.put("preis", p.getPreis());
      productDetails.put("lagerbestand", p.getLagerbestand());
      productDetails.put("angelegtVon", p.getAngelegtVon());

      posMap.put("produkt", productDetails);
      posMap.put("menge", bp.getMenge());
      posMap.put("positionsId", bp.getPositionsId());
      posMap.put("gesamtpreis", total);

      posList.add(posMap);
    }
    b.setPositionen(posList);
  }

  @GetMapping
  public Object getBestellungen(@RequestParam(required = false) Long id) {
    if (id != null) {
      Bestellung b = bestellungRepository.findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      attachPositions(b);
      return b;
    }
    Iterable<Bestellung> all = bestellungRepository.findAll();
    all.forEach(this::attachPositions);
    return all;
  }

  @PostMapping
  public Bestellung createBestellung(@RequestBody Bestellung b) {
    return bestellungRepository.save(b);
  }

  @DeleteMapping(params = "id")
  public void deleteBestellung(@RequestParam Long id) {
    bestellungRepository.deleteById(id);
  }
}