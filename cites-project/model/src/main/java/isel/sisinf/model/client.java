import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    private Integer person;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person")
    private Person personEntity;

    @Column(nullable = false)
    private LocalDateTime dtregister;

    // Getters and Setters
}
