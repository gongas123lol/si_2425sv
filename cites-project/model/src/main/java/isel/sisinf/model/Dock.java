package isel.sisinf.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dock")
public class Dock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "has_scooter", nullable = false)
    private boolean hasScooter;

    // Getters and setters
}
