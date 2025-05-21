package isel.sisinf.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Person person;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    // Getters and setters
}
