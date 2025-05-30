package isel.sisinf.jpa;

import isel.sisinf.model.Client;
import isel.sisinf.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {

    private final EntityManager em;

    public ClientRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Client> findById(Long id) {
        if (id == null) return Optional.empty();

        // Person.id is Integer → convert Long safely
        int personPk = Math.toIntExact(id);

        Person person = em.find(Person.class, personPk);
        return person == null
                ? Optional.empty()
                : Optional.ofNullable(em.find(Client.class, person));
    }

    @Override
    public List<Client> findAll() {
        return em.createQuery("select c from Client c", Client.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(Client client) {
        if (client == null) return;

        boolean exists = client.getPerson() != null &&
                em.find(Client.class, client.getPerson()) != null;

        if (exists) {
            em.merge(client);   // UPDATE
        } else {
            em.persist(client); // INSERT
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) return;

        int personPk = Math.toIntExact(id);

        Person person = em.find(Person.class, personPk);
        if (person == null) return;

        Client client = em.find(Client.class, person);
        if (client != null) {
            em.remove(client);
        }
    }
}