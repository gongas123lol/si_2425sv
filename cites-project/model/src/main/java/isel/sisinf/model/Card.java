package isel.sisinf.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CARD")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 4, scale = 2)
    private BigDecimal credit;

    @ManyToOne
    @JoinColumn(name = "typeofcard")
    private TypeOfCard typeOfCard;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    // Getters and Setters
}
