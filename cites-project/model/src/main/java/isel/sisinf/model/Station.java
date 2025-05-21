package isel.sisinf.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "STATION")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 6, scale = 4, nullable = false)
    private BigDecimal latitude;

    @Column(precision = 6, scale = 4, nullable = false)
    private BigDecimal longitude;

    // Getters and Setters
}
