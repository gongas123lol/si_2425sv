package isel.sisinf.jpa;

import isel.sisinf.model.*;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Optional<Person> findById(Long id);
    List<Person> findAll();
    void save(Person person);
    void deleteById(Long id);
}
