package org.example.m_citronix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trees")
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate plantingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;

    public int getAge() {
        return (int) ChronoUnit.YEARS.between(plantingDate, LocalDate.now());
    }

    public double getAnnualProductivity() {
        int age = getAge();
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12;
        } else if (age <= 20) {
            return 20;
        } else {
            return 0;
        }
    }

    public boolean isPlantingSeason() {
        int month = plantingDate.getMonthValue();
        return month >= 3 && month <= 5;
    }
}
