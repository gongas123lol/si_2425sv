package isel.sisinf.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "topup")
public class TopUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "topup_date", nullable = false)
    private LocalDate topupDate;

    @Column(nullable = false)
    private BigDecimal value;

    // Getters and setters
}
