package service;

import model.Car;
import repository.CarRepository;
import repository.impl.CarRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class CarService {
    private CarRepository carRepository = new CarRepositoryImpl();

    public Car addCar(Car car, Long userId) {
        car.setUserId(userId);
        return carRepository.save(car);
    }

    public List<Car> getUserCars(Long userId) {
        return carRepository.findByUserId(userId);
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public void updateCar(Car car, Long userId) {
        if (!car.getUserId().equals(userId)) {
            throw new SecurityException("Вы можете редактировать только свои автомобили");
        }
        carRepository.update(car);
    }

    public void deleteCar(Long carId, Long userId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isPresent()) {
            Car car = carOpt.get();
            if (!car.getUserId().equals(userId)) {
                throw new SecurityException("Вы можете удалять только свои автомобили");
            }
            carRepository.delete(carId);
        } else {
            throw new IllegalArgumentException("Автомобиль не найден");
        }
    }
}
