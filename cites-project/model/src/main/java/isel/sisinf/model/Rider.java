package isel.sisinf.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um cliente “Rider”.
 */
@Entity
@Table(name = "rider")
public class Rider {

    /* --------------------  Campos persistidos -------------------- */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "taxnumber", nullable = false)
    private Integer taxNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dtregister", nullable = false)
    private LocalDateTime dtRegister;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardid")
    private Card card;

    @Column(name = "credit", nullable = false)
    private BigDecimal credit;

    /* coluna real na BD (“resident” / “tourist”) */
    @Column(name = "typeofcard", nullable = false)
    private String typeOfCardDb;

    /* --------------------  Campo de conveniência -------------------- */

    @Transient
    private CardType typeOfCard;

    /* --------------------  Getters e Setters -------------------- */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(Integer taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDtRegister() {
        return dtRegister;
    }

    public void setDtRegister(LocalDateTime dtRegister) {
        this.dtRegister = dtRegister;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    /**
     * Getter preferido pela aplicação (enum).
     */
    public CardType getTypeOfCard() {
        return typeOfCard;
    }

    public void setTypeOfCard(CardType typeOfCard) {
        this.typeOfCard = typeOfCard;
    }

    /* Getter opcional para depuração – devolve o valor cru gravado na BD. */
    protected String getTypeOfCardDb() {
        return typeOfCardDb;
    }

    /* Setter protegido: a camada de persistência pode precisar. */
    public void setTypeOfCardDb(String typeOfCardDb) {
        this.typeOfCardDb = typeOfCardDb;
        setTypeOfCard(CardType.valueOf(typeOfCardDb));
    }
}