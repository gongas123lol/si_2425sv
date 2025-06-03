package isel.sisinf.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "scooter")
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal weight;

    @Column(name = "maxvelocity", nullable = false, precision = 4, scale = 2)
    private BigDecimal maxVelocity;

    @Column(nullable = false)
    private Integer battery;

    @ManyToOne(optional = false)
    @JoinColumn(name = "model") // References SCOOTERMODEL(number)
    private ScooterModel model;

    @Version
    @Column(name = "version")
    private LocalDateTime version;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(BigDecimal maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public ScooterModel getModel() {
        return model;
    }

    public void setModel(ScooterModel model) {
        this.model = model;
    }

    public LocalDateTime getVersion() {
        return version;
    }

    public void setVersion(LocalDateTime version) {
        this.version = version;
    }
}
