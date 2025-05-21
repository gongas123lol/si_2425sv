package isel.sisinf.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "servicecost")
public class ServiceCost {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CardType type;

    @Column(nullable = false)
    private BigDecimal cost;

    // Getters and setters
}
