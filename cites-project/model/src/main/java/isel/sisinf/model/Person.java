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

    @Column(name = "cc_number", unique = true, nullable = false, length = 8)
    private String ccNumber;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "nif", unique = true, nullable = false, length = 9)
    private String nif;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    public Long getId() {
        return id;
    }
    public String getCcNumber(){
        return ccNumber;
    }
    public String getNIF(){
        return nif;
    }
    public LocalDate getBirthDate(){
        return birthDate;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setBirthDate(LocalDate date){
        this.birthDate = date;
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

