package org.example.m_citronix.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate saleDate;

    @Column(nullable = false)
    private double unitPrice;

    @Column(nullable = false)
    private double quantity; // Will be set automatically from Harvest's totalQuantity

    @Column(nullable = false)
    private double revenue;

    @Column(nullable = false)
    private String clientName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;

    @PrePersist
    private void calculateRevenue() {
        this.revenue = this.quantity * this.unitPrice;
    }
}
