package com.dbw.spring_boot.repository;

import com.dbw.spring_boot.model.Bestellposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class BestellpositionRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Bestellposition> rowMapper = (rs, rowNum) -> {
    Bestellposition bp = new Bestellposition();
    bp.setPositionsId(rs.getLong("position_id"));
    bp.setBestellungId(rs.getLong("bestellung_id"));
    bp.setProduktSku(rs.getString("sku"));
    bp.setMenge(rs.getInt("menge"));
    return bp;
  };

  public List<Bestellposition> findAll() {
    return jdbcTemplate.query("SELECT * FROM bestellposition", rowMapper);
  }

  public Optional<Bestellposition> findById(Long id) {
    List<Bestellposition> res = jdbcTemplate.query("SELECT * FROM bestellposition WHERE position_id = ?", rowMapper,
        id);
    return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
  }

  public List<Bestellposition> findByBestellungId(Long id) {
    return jdbcTemplate.query("SELECT * FROM bestellposition WHERE bestellung_id = ?", rowMapper, id);
  }

  public Bestellposition save(Bestellposition bp) {
    if (bp.getPositionsId() == null) {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(con -> {
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO bestellposition (bestellung_id, sku, menge) VALUES (?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, bp.getBestellungId());
        ps.setString(2, bp.getProduktSku());
        ps.setInt(3, bp.getMenge());
        return ps;
      }, keyHolder);
      bp.setPositionsId(((Number) keyHolder.getKeys().get("position_id")).longValue());
    }
    return bp;
  }

  public void deleteById(Long id) {
    jdbcTemplate.update("DELETE FROM bestellposition WHERE position_id = ?", id);
  }
}