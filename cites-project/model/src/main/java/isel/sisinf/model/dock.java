import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DOCK")
public class Dock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "station", nullable = false)
    private Station station;

    @Column(length = 30, nullable = false)
    private String state;

    @ManyToOne
    @JoinColumn(name = "scooter")
    private Scooter scooter;

    private LocalDateTime version;

    // Getters and Setters
}
