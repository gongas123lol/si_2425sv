package isel.sisinf.jpa;

import isel.sisinf.model.Dock;
import isel.sisinf.model.Person;

import java.util.List;
import java.util.Optional;

public interface dockRepository {
    Optional<Dock> findById(Long id);
    List<Dock> findAll();
    void save(Dock dock);
    void deleteById(Long id);
}
