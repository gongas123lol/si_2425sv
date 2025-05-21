import jakarta.persistence.*;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    private Integer person;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person")
    private Person personEntity;

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;

    // Getters and Setters
}
