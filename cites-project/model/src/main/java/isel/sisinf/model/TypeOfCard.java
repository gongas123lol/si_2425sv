package isel.sisinf.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TYPEOFCARD")
public class TypeOfCard {

    @Id
    @Column(length = 10)
    private String reference;

    @Column(nullable = false)
    private Integer nodays;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal price;

    // Getters and Setters
}
