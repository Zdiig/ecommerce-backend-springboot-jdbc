package com.dbw.spring_boot.repository;

import com.dbw.spring_boot.model.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProduktRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Produkt> rowMapper = (rs, rowNum) -> new Produkt(
      rs.getString("sku"),
      rs.getString("name"),
      rs.getBigDecimal("preis"),
      rs.getInt("lagerbestand"),
      rs.getLong("angelegt_von"));

  public List<Produkt> findAll() {
    return jdbcTemplate.query("SELECT * FROM produkt", rowMapper);
  }

  public Optional<Produkt> findById(String sku) {
    List<Produkt> res = jdbcTemplate.query("SELECT * FROM produkt WHERE sku = ?", rowMapper, sku);
    return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
  }

  public Produkt save(Produkt p) {
    // Upsert logic for products since ID is string
    int updated = jdbcTemplate.update(
        "UPDATE produkt SET name=?, preis=?, lagerbestand=?, angelegt_von=? WHERE sku=?",
        p.getName(), p.getPreis(), p.getLagerbestand(), p.getAngelegtVon(), p.getSku());

    if (updated == 0) {
      jdbcTemplate.update(
          "INSERT INTO produkt (sku, name, preis, lagerbestand, angelegt_von) VALUES (?, ?, ?, ?, ?)",
          p.getSku(), p.getName(), p.getPreis(), p.getLagerbestand(), p.getAngelegtVon());
    }
    return p;
  }

  public void deleteById(String sku) {
    jdbcTemplate.update("DELETE FROM produkt WHERE sku = ?", sku);
  }
}