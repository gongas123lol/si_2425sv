package isel.sisinf.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Changed to Integer to match serial

    private String name;

    @Column(name = "taxnumber", unique = true)
    private Integer taxNumber;  // Changed to match database column

    @Column(name = "email", unique = true)
    private String email;

    // Remove joinDate as it doesn't exist in the database

    // Update getters and setters
    public Integer getId() {
        return id;
    }

    public Integer getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(Integer taxNumber) {
        this.taxNumber = taxNumber;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        return this.name;
    }
}