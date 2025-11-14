package repository.impl;

import model.Event;
import repository.DbConnection;
import repository.EventRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventRepositoryImpl implements EventRepository {

    @Override
    public Event save(Event event) {
        String sql = "INSERT INTO events (user_id, title, description, event_date, location, event_type_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, event.getUserId());
            statement.setString(2, event.getTitle());
            statement.setString(3, event.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(event.getEventDate()));
            statement.setString(5, event.getLocation());
            statement.setLong(6, event.getEventTypeId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        event.setId(generatedKeys.getLong(1));
                    }
                }
            }

            return event;

        } catch (Exception e) {
            throw new RuntimeException("Error saving event: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Event> findById(Long id) {
        String sql = "SELECT * FROM events WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(createEventFromResultSet(resultSet));
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new RuntimeException("Error finding event by id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE event_date >= CURRENT_TIMESTAMP ORDER BY event_date ASC";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Event event = createEventFromResultSet(resultSet);
                events.add(event);
            }

            return events;

        } catch (Exception e) {
            throw new RuntimeException("Error finding all events: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Event event) {
        String sql = "UPDATE events SET title = ?, description = ?, event_date = ?, location = ?, event_type_id = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(event.getEventDate()));
            statement.setString(4, event.getLocation());
            statement.setLong(5, event.getEventTypeId());
            statement.setLong(6, event.getId());

            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error updating event: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM events WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error deleting event: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Event> findByUserId(Long userId) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE user_id = ? ORDER BY event_date DESC";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                events.add(createEventFromResultSet(resultSet));
            }

            return events;

        } catch (Exception e) {
            throw new RuntimeException("Error finding events by user id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Event> findUpcomingEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE event_date >= CURRENT_TIMESTAMP ORDER BY event_date ASC";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                events.add(createEventFromResultSet(resultSet));
            }

            return events;

        } catch (Exception e) {
            throw new RuntimeException("Error finding upcoming events: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Event> findByType(Long eventTypeId) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE event_type_id = ? AND event_date >= CURRENT_TIMESTAMP ORDER BY event_date ASC";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, eventTypeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                events.add(createEventFromResultSet(resultSet));
            }

            return events;

        } catch (Exception e) {
            throw new RuntimeException("Error finding events by type: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Event> findAllWithDetails() {
        return findAll();
    }

    private Event createEventFromResultSet(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getLong("id"));
        event.setUserId(resultSet.getLong("user_id"));
        event.setTitle(resultSet.getString("title"));
        event.setDescription(resultSet.getString("description"));

        Timestamp timestamp = resultSet.getTimestamp("event_date");
        if (timestamp != null) {
            event.setEventDate(timestamp.toLocalDateTime());
        }

        event.setLocation(resultSet.getString("location"));
        event.setEventTypeId(resultSet.getLong("event_type_id"));


        return event;
    }
}