package service;

import model.Event;
import model.EventRegistration;
import model.EventType;
import model.User;
import repository.EventRepository;
import repository.impl.EventRepositoryImpl;
import repository.EventTypeRepository;
import repository.impl.EventTypeRepositoryImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventService {
    private EventRepository eventRepository = new EventRepositoryImpl();
    private EventTypeRepository eventTypeRepository = new EventTypeRepositoryImpl();
    private EventRegistrationService registrationService = new EventRegistrationService(); // Добавляем

    public boolean isRegistered(Long userId, Long eventId) {
        return registrationService.isUserRegistered(userId, eventId);
    }

    public int getRegisteredUsersCount(Long eventId) {
        List<EventRegistration> registrations = registrationService.getEventParticipants(eventId);
        return registrations.size();
    }


    public Event createEvent(Event event, User organizer) {
        event.setUserId(organizer.getId());

        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Дата мероприятия не может быть в прошлом");
        }
        return eventRepository.save(event);
    }

    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    public List<Event> getEventsByType(Long eventTypeId) {
        return eventRepository.findByType(eventTypeId);
    }


    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents();
    }

    public List<Event> getUserEvents(User user) {
        return eventRepository.findByUserId(user.getId());
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public void updateEvent(Event event, User currentUser) {
        if (!event.getUserId().equals(currentUser.getId())) {
            throw new SecurityException("Вы можете редактировать только свои мероприятия");
        }

        eventRepository.update(event);
    }

    public void deleteEvent(Long eventId, User currentUser) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);

        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            if (!event.getUserId().equals(currentUser.getId())) {
                throw new SecurityException("Вы можете удалять только свои мероприятия");
            }

            eventRepository.delete(eventId);
        } else {
            throw new IllegalArgumentException("Мероприятие не найдено");
        }
    }

    public boolean isUserOrganizer(Event event, User user) {
        return event.getUserId().equals(user.getId());
    }

    public EventTypeRepository getEventTypeRepository() {
        return eventTypeRepository;
    }

    public void setEventTypeRepository(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    public List<Event> getUserRegisteredEvents(Long userId) {
        EventRegistrationService registrationService = new EventRegistrationService();
        List<EventRegistration> registrations = registrationService.getUserRegistrations(userId);

        List<Event> events = new ArrayList<>();
        for (EventRegistration registration : registrations) {
            Optional<Event> eventOpt = eventRepository.findById(registration.getEventId());
            if (eventOpt.isPresent()) {
                events.add(eventOpt.get());
            }
        }
        return events;
    }
}