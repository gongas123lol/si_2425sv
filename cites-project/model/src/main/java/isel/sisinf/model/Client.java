package isel.sisinf.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;



@NamedStoredProcedureQuery(
        name = "startTrip",
        procedureName = "startTrip"
        /*
        parameters = {
                @StoredProcedureParameter(name = "p_client_id",  mode = ParameterMode.IN,  type = Integer.class),
                @StoredProcedureParameter(name = "p_scooter_id", mode = ParameterMode.IN,  type = Integer.class),
                @StoredProcedureParameter(name = "p_result",     mode = ParameterMode.OUT, type = Integer.class)
        }

         */

        ,
        parameters = {
                @StoredProcedureParameter(  mode = ParameterMode.IN,  type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN,  type = Integer.class),
                //@StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class)
        }

)

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