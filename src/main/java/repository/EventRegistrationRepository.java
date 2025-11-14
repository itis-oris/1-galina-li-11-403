package repository;

import model.EventRegistration;
import java.util.List;

public interface EventRegistrationRepository extends CrudRepository<EventRegistration, Long> {
    void registerUserForEvent(Long userId, Long eventId);
    void cancelRegistration(Long userId, Long eventId);
    boolean isUserRegistered(Long userId, Long eventId);
    List<EventRegistration> findByEventId(Long eventId);
    List<EventRegistration> findByUserId(Long userId);
}