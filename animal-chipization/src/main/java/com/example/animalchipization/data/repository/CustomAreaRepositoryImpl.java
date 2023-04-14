package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.Area;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;

@Repository
public class CustomAreaRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomAreaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Modifying
    public void save(Area area) {
        String sql = "INSERT INTO area (name, area_points) VALUES (?, ?);";
        PGpoint p1 = new PGpoint(0, 0);
        PGpoint p2 = new PGpoint(1, 1);
        PGpoint p3 = new PGpoint(1, 0);
        PGpoint p4 = new PGpoint(0, 0);
        PGpolygon pg = new PGpolygon(new PGpoint[] {p1, p2, p3, p4});
        var query = jdbcTemplate.update(sql, area.getName(), pg);


        //TODO попробовать JDBC
        /*String sql = "INSERT INTO area (name, area_points) VALUES (?, ?);";
        Query query = entityManager.createNativeQuery(sql);

        PGpoint p1 = new PGpoint(0, 0);
        PGpoint p2 = new PGpoint(1, 1);
        PGpoint p3 = new PGpoint(1, 0);
        PGpoint p4 = new PGpoint(0, 0);
        PGpolygon pg = new PGpolygon(new PGpoint[] {p1, p2, p3, p4});

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, area.getName());
            ps.setObject(2, pg);
            ps.executeUpdate();
        } catch (Exception exception) {
        }
        // https://jdbc.postgresql.org/documentation/server-prepare/*/
    }

}
