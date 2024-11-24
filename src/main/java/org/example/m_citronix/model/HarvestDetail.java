package org.example.m_citronix.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
    public class HarvestDetail {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "harvest_id", nullable = false)
        private Harvest harvest;

        @ManyToOne
        @JoinColumn(name = "tree_id", nullable = false)
        private Tree tree;

        @Column(nullable = false)
        private double annualProductivity;
}
