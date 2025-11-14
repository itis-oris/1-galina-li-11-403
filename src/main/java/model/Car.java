package model;

public class Car {
    private Long id;
    private Long userId;
    private String brand;
    private String model;
    private Integer year;

    public Car(Long id, Long userId, String brand, String model, Integer year) {
        this.id = id;
        this.userId = userId;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public Car() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
