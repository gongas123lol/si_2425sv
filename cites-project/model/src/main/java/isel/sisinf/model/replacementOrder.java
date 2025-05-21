import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPLACEMENTORDER")
@IdClass(ReplacementOrderId.class)
public class ReplacementOrder {

    @Id
    private LocalDateTime dorder;

    @Id
    @ManyToOne
    @JoinColumn(name = "station")
    private Station station;

    private LocalDateTime dreplacement;

    @Column(nullable = false)
    private Integer roccupation;

    // Getters and Setters
}
