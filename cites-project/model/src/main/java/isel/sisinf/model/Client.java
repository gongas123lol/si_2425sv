package isel.sisinf.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "client")
public class Client {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Person person;

    // Getters and setters
}
