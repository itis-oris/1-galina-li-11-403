package service;

import model.Event;
import model.EventRegistration;
import repository.EventRegistrationRepository;
import repository.impl.EventRegistrationRepositoryImpl;
import repository.EventRepository;
import repository.impl.EventRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class EventRegistrationService {
    private EventRegistrationRepository registrationRepository = new EventRegistrationRepositoryImpl();
    private  EventRepository eventRepository = new EventRepositoryImpl();

    public void registerForEvent(Long userId, Long eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);

        if (!eventOpt.isPresent()) {
            throw new IllegalArgumentException("Мероприятие не найдено");
        }
        Event event = eventOpt.get();

        if (event.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Организатор не может записываться на свое мероприятие");
        }

        if (registrationRepository.isUserRegistered(userId, eventId)) {
            throw new IllegalArgumentException("Вы уже зарегистрированы на это мероприятие");
        }

        registrationRepository.registerUserForEvent(userId, eventId);
    }

    public void cancelRegistration (Long userId, Long eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (!eventOpt.isPresent()) {
            throw new IllegalArgumentException("Мероприятие не найдено");
        }

        if(!registrationRepository.isUserRegistered(userId, eventId)) {
            throw new IllegalArgumentException("Вы не зарегистрированы на это мероприятие");
        }
        registrationRepository.cancelRegistration(userId, eventId);
    }

    public boolean isUserRegistered(Long userId, Long eventId) {
        return registrationRepository.isUserRegistered(userId, eventId);
    }

    public List<EventRegistration> getUserRegistrations (Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    public List<EventRegistration> getEventParticipants (Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    public Optional<EventRegistration> findById(Long id) {
        return registrationRepository.findById(id);
    }

    public List<EventRegistration> findAll() {
        return registrationRepository.findAll();
    }
}