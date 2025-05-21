package isel.sisinf.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "start_dock_id")
    private Dock startDock;

    @ManyToOne(optional = false)
    @JoinColumn(name = "end_dock_id")
    private Dock endDock;

    // Getters and setters
}
