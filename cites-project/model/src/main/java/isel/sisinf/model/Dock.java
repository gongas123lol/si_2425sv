package isel.sisinf.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dock")
public class Dock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number") // Matches "number serial primary key"
    private Integer number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "station") // Matches station INTEGER REFERENCES station(id)
    private Station station;

    @Column(name = "state", nullable = false)
    private String state;

    @ManyToOne
    @JoinColumn(name = "scooter") // Nullable, optional FK to SCOOTER
    private Scooter scooter;

    @Column(name = "version")
    private LocalDateTime version;

    // Getters and setters

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public void setScooter(Scooter scooter) {
        this.scooter = scooter;
    }

    public LocalDateTime getVersion() {
        return version;
    }

    public void setVersion(LocalDateTime version) {
        this.version = version;
    }
}
