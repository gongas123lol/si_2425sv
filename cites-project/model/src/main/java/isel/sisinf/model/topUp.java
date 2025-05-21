import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TOPUP")
@IdClass(TopUpId.class)
public class TopUp {

    @Id
    private LocalDateTime dttopup;

    @Id
    @ManyToOne
    @JoinColumn(name = "card")
    private Card card;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal value;

    // Getters and Setters
}
