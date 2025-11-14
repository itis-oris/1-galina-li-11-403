package repository.impl;

import model.EventRegistration;
import repository.DbConnection;
import repository.EventRegistrationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventRegistrationRepositoryImpl implements EventRegistrationRepository {

    @Override
    public void registerUserForEvent(Long userId, Long eventId) {
        String sql = "INSERT INTO event_registrations (event_id, user_id) VALUES (?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, eventId);
            statement.setLong(2, userId);

            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error registering user for event: " + e.getMessage(), e);
        }
    }

    @Override
    public void cancelRegistration(Long userId, Long eventId) {
        String sql = "DELETE FROM event_registrations WHERE event_id = ? AND user_id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, eventId);
            statement.setLong(2, userId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Registration not found for user " + userId + " and event " + eventId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error canceling registration: " + e.getMessage(), e);
        }
    }


    @Override
    public boolean isUserRegistered(Long userId, Long eventId) {
        String sql = "SELECT COUNT(*) FROM event_registrations WHERE event_id = ? AND user_id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, eventId);
            statement.setLong(2, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;

        } catch (Exception e) {
            throw new RuntimeException("Error checking user registration: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EventRegistration> findByEventId(Long eventId) {
        List<EventRegistration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM event_registrations WHERE event_id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, eventId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                registrations.add(createRegistrationFromResultSet(resultSet));
            }

            return registrations;
        } catch (Exception e) {
            throw new RuntimeException("Error finding registrations by event id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EventRegistration> findByUserId(Long userId) {
        List<EventRegistration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM event_registrations WHERE user_id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                registrations.add(createRegistrationFromResultSet(resultSet));
            }

            return registrations;

        } catch (Exception e) {
            throw new RuntimeException("Error finding registrations by user id: " + e.getMessage(), e);
        }
    }

    @Override
    public EventRegistration save(EventRegistration registration) {
        String sql = "INSERT INTO event_registrations (event_id, user_id) VALUES (?, ?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setLong(1, registration.getEventId());
                statement.setLong(2, registration.getUserId());

                int affectedRows = statement.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            registration.setId(generatedKeys.getLong(1));
                        }
                    }
                }
                return registration;

        } catch (Exception e) {
            throw new RuntimeException("Error saving event registration: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<EventRegistration> findById(Long id) {
        String sql = "SELECT * FROM event_registrations WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(createRegistrationFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error finding registration by id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EventRegistration> findAll() {
        List<EventRegistration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM event_registrations ORDER BY id";

        try (Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                registrations.add(createRegistrationFromResultSet(resultSet));
            }
            return registrations;
        } catch (Exception e) {
            throw new RuntimeException("Error finding all registrations: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(EventRegistration registration) {
        throw new UnsupportedOperationException("Update operation not supported for event registrations");
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM event_registrations WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting registration: " + e.getMessage(), e);
        }
    }

    private EventRegistration createRegistrationFromResultSet (ResultSet resultSet) throws SQLException {
        EventRegistration registration = new EventRegistration();
        registration.setId(resultSet.getLong("id"));
        registration.setEventId(resultSet.getLong("event_id"));
        registration.setUserId(resultSet.getLong("user_id"));
        return registration;
    }
}
