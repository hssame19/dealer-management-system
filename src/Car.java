public class Car {
    String make;
    String model;
    int year;
    String engineAspiration;
    double price;

    public Car(String make, String model, int year, String engineAspiration, double price) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.engineAspiration = engineAspiration;
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getEngineAspiration() {
        return engineAspiration;
    }

    public double getPrice() {
        return price;
    }

}
