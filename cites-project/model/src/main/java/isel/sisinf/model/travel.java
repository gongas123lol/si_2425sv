import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRAVEL")
@IdClass(TravelId.class)
public class Travel {

    @Id
    private LocalDateTime dinitial;

    @Id
    @ManyToOne
    @JoinColumn(name = "scooter")
    private Scooter scooter;

    private String comment;

    private Integer evaluation;

    private LocalDateTime dfinal;

    @ManyToOne
    @JoinColumn(name = "client", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "stinitial", nullable = false)
    private Station stinitial;

    @ManyToOne
    @JoinColumn(name = "stfinal")
    private Station stfinal;

    // Getters and Setters
}
