import jakarta.persistence.*;

@Entity
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, unique = true)
    private String email;

    @Column(unique = true)
    private Integer taxnumber;

    @Column(length = 50, nullable = false)
    private String name;

    // Getters and Setters
}
