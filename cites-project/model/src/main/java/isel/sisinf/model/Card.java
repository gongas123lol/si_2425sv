package isel.sisinf.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CardType type;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    // Getters and setters
}
