package isel.sisinf.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "station")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    @Column(name = "max_docks", nullable = false)
    private int maxDocks;

    // Getters and setters
}
