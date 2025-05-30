package isel.sisinf.services;

import isel.sisinf.jpa.PersonRepository;
import isel.sisinf.model.Client;
import isel.sisinf.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

public class ClientServices {

    private final PersonRepository personRepo;

    @PersistenceContext
    private EntityManager em;        // injected by container or supplied manually

    /* Constructor used inside managed environments (EntityManager injected). */
    public ClientServices(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    /* Convenience constructor for plain Java / CLI apps. */
    public ClientServices(PersonRepository personRepo, EntityManager em) {
        this.personRepo = personRepo;
        this.em = em;
    }

    /* Setter so we can still inject manually if desired. */
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }


    /**
     * Creates a new Person and an associated Client in one transaction.
     *
     * @param name      person’s full name
     * @param taxNumber fiscal number (unique)
     * @return the newly created Client entity (managed)
     */
    @Transactional
    public Client createPersonAndClient(String name, String email, Integer taxNumber) {

        /* 1. Create & persist the Person */
        Person person = new Person();
        person.setName(name);
        person.setTaxNumber(taxNumber);
        person.setEmail(email);

        personRepo.save(person); // PK generated here

        /* 2. Create & persist the linked Client */
        Client client = new Client();
        client.setPerson(person);                 // FK / PK
        client.setDtRegister(LocalDateTime.now()); // registration timestamp

        em.persist(client);

        return client;                            // managed instance
    }
}