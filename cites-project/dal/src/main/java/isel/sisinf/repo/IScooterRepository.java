package isel.sisinf.repo;

import isel.sisinf.model.Scooter;

import java.util.List;
import java.util.Optional;

public interface IScooterRepository {
    Optional<Scooter> findById(Long id);
    List<Scooter> findAll();
    void save(Scooter scooter);
    void deleteById(Long id);
}
