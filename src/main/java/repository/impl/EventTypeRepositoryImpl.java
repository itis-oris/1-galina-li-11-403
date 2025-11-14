package repository.impl;

import model.EventType;
import repository.DbConnection;
import repository.EventTypeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventTypeRepositoryImpl implements EventTypeRepository {

    @Override
    public List<EventType> findAll(){
        List<EventType> eventTypes = new ArrayList<>();
        String sql = "SELECT * FROM event_types ORDER BY id";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                EventType eventType = new EventType();
                eventType.setId(resultSet.getLong("id"));
                eventType.setName(resultSet.getString("name"));
                eventTypes.add(eventType);
            }
            return eventTypes;

        } catch (Exception e) {
            throw new RuntimeException("Error finding all event types: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(EventType entity) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public EventType save(EventType entity) {
        return null;
    }

    @Override
    public Optional<EventType> findById(Long id) {
        String sql = "SELECT * FROM event_types WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                EventType eventType = new EventType();
                eventType.setId(resultSet.getLong("id"));
                eventType.setName(resultSet.getString("name"));
                return Optional.of(eventType);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error finding event type by id: " + e.getMessage(), e);
        }
    }


}
