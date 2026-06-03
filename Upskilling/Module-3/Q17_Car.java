// Q17 - Class and Object Creation
public class Q17_Car {
    String make;
    String model;
    int year;

    Q17_Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    void displayDetails() {
        System.out.println(year + " " + make + " " + model);
    }

    public static void main(String[] args) {
        Q17_Car car1 = new Q17_Car("Toyota", "Camry", 2022);
        Q17_Car car2 = new Q17_Car("Honda", "Civic", 2021);

        car1.displayDetails();
        car2.displayDetails();
    }
}
