package isel.sisinf.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "nif", unique = true, nullable = false, length = 9)
    private String nif;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getNIF(){
        return nif;
    }

    public LocalDate getJoinDate(){
        return joinDate;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setJoinDate(LocalDate date){
        this.joinDate = date;
    }

    public void setNIF(String nif){
        this.nif = nif;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }
}

