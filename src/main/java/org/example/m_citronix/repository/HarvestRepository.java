package org.example.m_citronix.repository;

import org.example.m_citronix.model.Field;
import org.example.m_citronix.model.Harvest;
import org.example.m_citronix.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HarvestRepository extends JpaRepository<Harvest, Long> {

    boolean existsByFieldAndSeason(Field field, Season season);

    Optional<Harvest> findByFieldAndSeason(Field field, Season season);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Harvest h " +
            "WHERE h.field = :field AND h.season = :season AND YEAR(h.harvestDate) = :year")
    boolean existsByFieldAndSeasonAndYear(@Param("field") Field field,
                                          @Param("season") Season season,
                                          @Param("year") int year);

}
