package mk.ukim.finki.mk.lab.repository;

import mk.ukim.finki.mk.lab.model.Location;
import mk.ukim.finki.mk.lab.bootstrap.DataHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository {
    public List<Location> findAll() {
        return DataHolder.locations;
    }

    public Optional<Location> findById(Long id) {
        return DataHolder.locations.stream()
                .filter(location -> location.getId().equals(id))
                .findFirst();
    }
}
