package isel.sisinf.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "SCOOTERMODEL")
public class ScooterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;

    @Column(length = 30, nullable = false)
    private String designation;

    @Column(length = 30, nullable = false)
    private String make;

    @Column(nullable = false)
    private Integer stockAutonomy;

    @OneToMany(mappedBy = "model")
    private List<Scooter> scooters;

    // Getters and Setters

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Integer getStockAutonomy() {
        return stockAutonomy;
    }

    public void setStockAutonomy(Integer autonomy) {
        this.stockAutonomy = autonomy;
    }

    public List<Scooter> getScooters() {
        return scooters;
    }

    public void setScooters(List<Scooter> scooters) {
        this.scooters = scooters;
    }
}
