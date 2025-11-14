package repository;

import model.EventType;

import java.util.List;
import java.util.Optional;

public interface EventTypeRepository extends CrudRepository<EventType, Long> {
    List<EventType> findAll();
    Optional<EventType> findById(Long id);
}
