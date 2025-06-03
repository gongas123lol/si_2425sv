package isel.sisinf.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id")
    private Card card;

     */
    @Column(name = "dinitial", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "dfinal", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stinitial")
    private Dock startDock;

    @ManyToOne(optional = false)
    @JoinColumn(name = "scooter", nullable = false)
    private Scooter scooter;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client", nullable = false)
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stfinal")
    private Dock endDock;

    @Column(name = "comment")
    private String comment;

    @Column(name = "evaluation")
    private Integer rate;


    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Dock getStartDock() {
        return startDock;
    }

    public void setStartDock(Dock startDock) {
        this.startDock = startDock;
    }

    public Dock getEndDock() {
        return endDock;
    }

    public void setEndDock(Dock endDock) {
        this.endDock = endDock;
    }

    public Scooter getScooter() {return scooter;}

    public void setScooter(Scooter scooter) {this.scooter = scooter;}
}
