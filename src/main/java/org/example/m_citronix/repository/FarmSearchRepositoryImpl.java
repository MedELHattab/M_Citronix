package org.example.m_citronix.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.m_citronix.model.Farm;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class FarmSearchRepositoryImpl  implements FarmSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Farm> searchFarms(String name, String location, Double minArea, Double maxArea) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> query = cb.createQuery(Farm.class);
        Root<Farm> farm = query.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();

        // Add predicates based on non-null criteria
        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(farm.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (location != null && !location.isEmpty()) {
            predicates.add(cb.like(cb.lower(farm.get("location")), "%" + location.toLowerCase() + "%"));
        }
        if (minArea != null) {
            predicates.add(cb.greaterThanOrEqualTo(farm.get("area"), minArea));
        }
        if (maxArea != null) {
            predicates.add(cb.lessThanOrEqualTo(farm.get("area"), maxArea));
        }

        // Combine predicates with AND
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}
