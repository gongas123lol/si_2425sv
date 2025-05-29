package isel.sisinf.model;

import jakarta.persistence.*;

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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReplacementOrder getReplacementOrder() {
        return replacementOrder;
    }

    public void setReplacementOrder(ReplacementOrder replacementOrder) {
        this.replacementOrder = replacementOrder;
    }

    public Dock getDock() {
        return dock;
    }

    public void setDock(Dock dock) {
        this.dock = dock;
    }
}
