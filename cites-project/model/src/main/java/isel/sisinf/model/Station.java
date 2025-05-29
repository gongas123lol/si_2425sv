package isel.sisinf.model;

import jakarta.persistence.*;

@Entity
@Table(name = "station")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    @Column(name = "max_docks", nullable = false)
    private int maxDocks;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxDocks() {
        return maxDocks;
    }

    public void setMaxDocks(int maxDocks) {
        this.maxDocks = maxDocks;
    }
}
