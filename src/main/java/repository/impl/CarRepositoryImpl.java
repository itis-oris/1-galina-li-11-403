package repository.impl;

import model.Car;
import repository.CarRepository;
import repository.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRepositoryImpl implements CarRepository {
    @Override
    public List<Car> findByUserId(Long userId) {
        List<Car> cars = new ArrayList<>();

        String sql = "SELECT * FROM cars WHERE user_id = ? ORDER BY brand, model";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cars.add(createCarFromResultSet(resultSet));
            }
            return cars;

        } catch (Exception e) {
            throw new RuntimeException("Error finding cars by user id: " + e.getMessage(), e);
        }
    }

    @Override
    public Car save(Car car) {
        String sql = "INSERT INTO cars (user_id, brand, model, year) VALUES (?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, car.getUserId());
            statement.setString(2, car.getBrand());
            statement.setString(3, car.getModel());
            statement.setInt(4, car.getYear());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        car.setId(generatedKeys.getLong(1));
                    }
                }
            }
            return car;
    } catch (Exception e) {
            throw new RuntimeException("Error saving car: " + e.getMessage(), e);
        }
    }


    @Override
    public Optional<Car> findById(Long id) {
        String sql = "SELECT * FROM cars WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(createCarFromResultSet(resultSet));
            }
            return Optional.empty();

        } catch (Exception e) {
            throw new RuntimeException("Error finding car by id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars ORDER BY brand, model";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                cars.add(createCarFromResultSet(resultSet));
            }
            return cars;

        } catch (Exception e) {
            throw new RuntimeException("Error finding all cars: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Car car) {
        String sql = "UPDATE cars SET brand = ?, model = ?, year = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setLong(4, car.getId());

            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error updating car: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM cars WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error deleting car: " + e.getMessage(), e);
        }
    }

    private Car createCarFromResultSet(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getLong("id"));
        car.setUserId(resultSet.getLong("user_id"));
        car.setBrand(resultSet.getString("brand"));
        car.setModel(resultSet.getString("model"));
        car.setYear(resultSet.getInt("year"));
        return car;
    }
}
