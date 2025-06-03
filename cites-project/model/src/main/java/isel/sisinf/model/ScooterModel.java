package isel.sisinf.model;

import jakarta.persistence.*;

@Entity
@Table(name = "scootermodel")
public class ScooterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    @Column(nullable = false, length = 30)
    private String designation;

    @Column(nullable = false)
    private Integer autonomy;

    // Getters and setters
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(Integer autonomy) {
        this.autonomy = autonomy;
    }
}
