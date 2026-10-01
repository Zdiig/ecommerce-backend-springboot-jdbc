package com.dbw.spring_boot.repository;

import com.dbw.spring_boot.model.Bestellung;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class BestellungRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Bestellung> rowMapper = (rs, rowNum) -> {
    Bestellung b = new Bestellung();
    b.setBestellungId(rs.getLong("bestellung_id"));
    b.setKundeId(rs.getLong("kunde_id"));
    b.setPersonalNr(rs.getLong("mitarbeiterzuweis"));
    b.setDatum(rs.getTimestamp("datum").toLocalDateTime());
    b.setStatus(rs.getString("status"));
    return b;
  };

  public List<Bestellung> findAll() {
    return jdbcTemplate.query("SELECT * FROM bestellung", rowMapper);
  }

  public Optional<Bestellung> findById(Long id) {
    List<Bestellung> res = jdbcTemplate.query("SELECT * FROM bestellung WHERE bestellung_id = ?", rowMapper, id);
    return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
  }

  public Bestellung save(Bestellung b) {
    if (b.getBestellungId() == null) {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(con -> {
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO bestellung (kunde_id, mitarbeiterzuweis, datum, status) VALUES (?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, b.getKundeId());
        ps.setObject(2, b.getPersonalNr());
        ps.setTimestamp(3, Timestamp.valueOf(b.getDatum()));
        ps.setString(4, b.getStatus());
        return ps;
      }, keyHolder);
      b.setBestellungId(((Number) keyHolder.getKeys().get("bestellung_id")).longValue());
    }
    return b;
  }

  public void deleteById(Long id) {
    jdbcTemplate.update("DELETE FROM bestellung WHERE bestellung_id = ?", id);
  }
}