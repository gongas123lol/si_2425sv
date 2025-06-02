package isel.sisinf.repo;

import isel.sisinf.model.Dock;

import java.util.List;
import java.util.Optional;

public interface IDockRepository {
    Optional<Dock> findById(Long id);
    List<Dock> findAll();
    void save(Dock dock);
    void deleteById(Long id);
}
