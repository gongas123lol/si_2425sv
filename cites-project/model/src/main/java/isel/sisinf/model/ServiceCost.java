package isel.sisinf.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SERVICECOST")
public class ServiceCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Not present in SQL, added for JPA

    @Column(precision = 3, scale = 2)
    private BigDecimal unlock = BigDecimal.valueOf(1.00);

    @Column(precision = 3, scale = 2)
    private BigDecimal usable = BigDecimal.valueOf(0.15);

    // Getters and Setters
}
