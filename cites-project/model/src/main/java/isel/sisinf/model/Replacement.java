package isel.sisinf.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "replacement")
public class Replacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "replacement_order_id")
    private ReplacementOrder replacementOrder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dock_id")
    private Dock dock;

    // Getters and setters
}
