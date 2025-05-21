package isel.sisinf.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "replacementorder")
public class ReplacementOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    // Getters and setters
}
