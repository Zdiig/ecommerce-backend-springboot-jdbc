package com.dbw.spring_boot.repository;

import com.dbw.spring_boot.model.Adresse;
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
public class AdresseRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Adresse> rowMapper = (rs, rowNum) -> new Adresse(
      rs.getBoolean("aktiv"),
      rs.getString("strasse"),
      rs.getString("hausnummer"),
      rs.getLong("plz"),
      rs.getString("ort"),
      rs.getString("land")) {
    {
      setAdresseId(rs.getLong("adresse_id"));
    }
  };

  public List<Adresse> findAll() {
    return jdbcTemplate.query("SELECT * FROM adresse", rowMapper);
  }

  public Optional<Adresse> findById(Long id) {
    List<Adresse> result = jdbcTemplate.query("SELECT * FROM adresse WHERE adresse_id = ?", rowMapper, id);
    return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
  }

  public Adresse save(Adresse a) {
    if (a.getAdresseId() == null) {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(con -> {
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO adresse (aktiv, strasse, hausnummer, plz, ort, land) VALUES (?, ?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        ps.setBoolean(1, a.getAktiv());
        ps.setString(2, a.getStrasse());
        ps.setString(3, a.getHausnummer());
        ps.setLong(4, a.getPlz());
        ps.setString(5, a.getOrt());
        ps.setString(6, a.getLand());
        return ps;
      }, keyHolder);
      a.setAdresseId(((Number) keyHolder.getKeys().get("adresse_id")).longValue());
    } else {
      jdbcTemplate.update(
          "UPDATE adresse SET aktiv=?, strasse=?, hausnummer=?, plz=?, ort=?, land=? WHERE adresse_id=?",
          a.getAktiv(), a.getStrasse(), a.getHausnummer(), a.getPlz(), a.getOrt(), a.getLand(), a.getAdresseId());
    }
    return a;
  }
}