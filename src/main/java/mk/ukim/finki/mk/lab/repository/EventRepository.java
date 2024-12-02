package mk.ukim.finki.mk.lab.repository;

import mk.ukim.finki.mk.lab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByLocation_Id(Long locationId);

    Optional<Event> findByName(String name);
}
