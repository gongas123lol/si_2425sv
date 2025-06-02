package isel.sisinf.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rider")
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Changed to Integer to match serial

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "taxnumber", unique = true)
    private Integer taxNumber;  // Changed to match database column

    private String name;

    @Column(name = "dtregister", nullable = false)
    private LocalDateTime dtRegister;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardid")
    private Long cardId;

    @Column
    private Float credit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CardType type;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public Integer getTaxNumber() { return taxNumber; }

    public void setTaxNumber(Integer newTaxNumber) {
        this.taxNumber = newTaxNumber;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public String getName(){
        return this.name;
    }

    public void setDtRegister(LocalDateTime newDate){
        this.dtRegister = newDate;
    }

    public LocalDateTime getDtRegister(){
        return this.dtRegister;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long newCardId) {
        this.cardId = newCardId;
    }

    public Float getCredit() { return credit; }

    public void setCredit(Float newCredit) { this.credit = newCredit; }

    public CardType getType() {
        return type;
    }

    public void setType(CardType newType) {
        this.type = newType;
    }
}