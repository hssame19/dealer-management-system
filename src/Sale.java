import java.util.Date;

public class Sale {
    String buyersName;
    String buyersCpf;

    Car car;

    Date date;

    public Sale(String buyersName, String buyersCpf, Car car, Date date) {
        this.buyersName = buyersName;
        this.buyersCpf = buyersCpf;
        this.car = car;
        this.date = date;
    }
}
