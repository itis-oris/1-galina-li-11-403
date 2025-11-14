package repository;

import model.Car;
import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findByUserId(Long userId);
}