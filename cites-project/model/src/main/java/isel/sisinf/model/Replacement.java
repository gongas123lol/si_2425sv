package isel.sisinf.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPLACEMENT")
public class Replacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;

    @Column(nullable = false)
    private LocalDateTime dreplacement;

    @Column(length = 8, nullable = false)
    private String action;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "reporder", referencedColumnName = "dorder"),
            @JoinColumn(name = "repstation", referencedColumnName = "station")
    })
    private ReplacementOrder replacementOrder;

    @ManyToOne
    @JoinColumn(name = "employee", nullable = false)
    private Employee employee;

    // Getters and Setters
}
