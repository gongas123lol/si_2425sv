package isel.sisinf.model;

import jakarta.persistence.*;

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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public boolean isHasScooter() {
        return hasScooter;
    }

    public void setHasScooter(boolean hasScooter) {
        this.hasScooter = hasScooter;
    }

}
