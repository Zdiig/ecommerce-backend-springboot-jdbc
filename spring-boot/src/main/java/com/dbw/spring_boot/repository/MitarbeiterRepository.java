package com.dbw.spring_boot.repository;

import com.dbw.spring_boot.model.Mitarbeiter;
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
public class MitarbeiterRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Mitarbeiter> rowMapper = (rs, rowNum) -> {
    Mitarbeiter m = new Mitarbeiter();
    m.setPersonalNr(rs.getLong("personal_nr"));
    m.setVorname(rs.getString("vorname"));
    m.setNachname(rs.getString("nachname"));
    m.setEmail(rs.getString("email"));
    m.setPasswort(rs.getString("passwort"));
    return m;
  };

  public List<Mitarbeiter> findAll() {
    return jdbcTemplate.query("SELECT * FROM mitarbeiter", rowMapper);
  }

  public Optional<Mitarbeiter> findById(Long id) {
    List<Mitarbeiter> res = jdbcTemplate.query("SELECT * FROM mitarbeiter WHERE personal_nr = ?", rowMapper, id);
    return res.isEmpty() ? Optional.empty() : Optional.of(res.get(0));
  }

  public Mitarbeiter save(Mitarbeiter m) {
    if (m.getPersonalNr() == null) {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(con -> {
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO mitarbeiter (vorname, nachname, email, passwort) VALUES (?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, m.getVorname());
        ps.setString(2, m.getNachname());
        ps.setString(3, m.getEmail());
        ps.setString(4, m.getPasswort());
        return ps;
      }, keyHolder);
      m.setPersonalNr(((Number) keyHolder.getKeys().get("personal_nr")).longValue());
    }
    return m;
  }

  public void deleteById(Long id) {
    jdbcTemplate.update("DELETE FROM mitarbeiter WHERE personal_nr = ?", id);
  }
}