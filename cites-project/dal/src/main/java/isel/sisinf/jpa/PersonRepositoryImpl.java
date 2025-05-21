package isel.sisinf.jpa;

import isel.sisinf.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public class PersonRepositoryImpl implements PersonRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(em.find(Person.class, id));
    }

    @Override
    public List<Person> findAll() {
        return em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Override
    public void save(Person person) {
        if (person.getId() == null) {
            em.persist(person);
        } else {
            em.merge(person);
        }
    }

    @Override
    public void deleteById(Long id) {
        Person person = em.find(Person.class, id);
        if (person != null) {
            em.remove(person);
        }
    }
}
