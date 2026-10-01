package com.dbw.spring_boot.repository;

import com.dbw.spring_boot.model.Kunde;
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
public class KundeRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Kunde> rowMapper = (rs, rowNum) -> {
    Kunde k = new Kunde();
    k.setKundeId(rs.getLong("kunde_id"));
    k.setEmail(rs.getString("email"));
    k.setVorname(rs.getString("vorname"));
    k.setNachname(rs.getString("nachname"));
    k.setPasswort(rs.getString("passwort"));
    return k;
  };

  public List<Kunde> findAll() {
    return jdbcTemplate.query("SELECT * FROM kunde", rowMapper);
  }

  public Optional<Kunde> findById(Long id) {
    List<Kunde> res = jdbcTemplate.query("SELECT * FROM kunde WHERE kunde_id = ?", rowMapper, id);
    return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
  }

  public Optional<Kunde> findByEmail(String email) {
    List<Kunde> res = jdbcTemplate.query("SELECT * FROM kunde WHERE email = ?", rowMapper, email);
    return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
  }

  public Kunde save(Kunde k) {
    if (k.getKundeId() == null) {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(con -> {
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO kunde (email, vorname, nachname, passwort) VALUES (?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, k.getEmail());
        ps.setString(2, k.getVorname());
        ps.setString(3, k.getNachname());
        ps.setString(4, k.getPasswort());
        return ps;
      }, keyHolder);
      k.setKundeId(((Number) keyHolder.getKeys().get("kunde_id")).longValue());
    } else {
      jdbcTemplate.update(
          "UPDATE kunde SET email=?, vorname=?, nachname=?, passwort=? WHERE kunde_id=?",
          k.getEmail(), k.getVorname(), k.getNachname(), k.getPasswort(), k.getKundeId());
    }
    return k;
  }
}