package repository;

import model.Event;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByUserId(Long userId); // Мероприятия организатора
    List<Event> findUpcomingEvents(); // Предстоящие мероприятия
    List<Event> findByType(Long eventTypeId); // Фильтрация по типу
    List<Event> findAllWithDetails(); // С информацией об организаторе
}