package isel.sisinf.repo;

import isel.sisinf.model.Client;
import isel.sisinf.model.Rider;

import java.util.List;
import java.util.Optional;

public interface IRiderRepository {
    Optional<Rider> findById(Long id);
    List<Rider> findAll();
    void save(Rider rider);
    void deleteById(Long id);
}