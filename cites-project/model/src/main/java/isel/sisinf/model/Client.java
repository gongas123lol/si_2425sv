package isel.sisinf.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @OneToOne
    @JoinColumn(name = "person")
    private Person person;

    @Column(name = "dtregister", nullable = false)
    private LocalDateTime dtRegister;

    public void setPerson(Person person){
        this.person = person;
    }
    public Person getPerson(){
        return this.person;
    }

    public void setDtRegister(LocalDateTime date){
        this.dtRegister = date;
    }

    public LocalDateTime getDtRegister(){
        return this.dtRegister;
    }
}