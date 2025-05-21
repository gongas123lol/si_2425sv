package isel.sisinf.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "cc_number", unique = true, nullable = false, length = 8)
    private String ccNumber;

    @Column(name = "nif", unique = true, nullable = false, length = 9)
    private String nif;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    // Getters and setters
}

