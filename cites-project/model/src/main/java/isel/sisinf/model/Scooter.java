package isel.sisinf.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "SCOOTER")
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal weight;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal maxvelocity;

    @Column(nullable = false)
    private Integer Autonomy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "model", referencedColumnName = "number")
    private ScooterModel model;
    // The manufacture's name is in the Model table

    @Column(nullable = false)
    private Double price;

    private LocalDateTime version;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getMaxvelocity() {
        return maxvelocity;
    }

    public void setMaxvelocity(BigDecimal maxvelocity) {
        this.maxvelocity = maxvelocity;
    }

    public Integer getAutonomy() {
        return Autonomy;
    }

    public void setAutonomy(Integer Autonomy) {
        this.Autonomy = Autonomy;
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
