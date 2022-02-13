public class Vehicle {

    int numWheels = 1;

    public void ride(Vehicle v) {
        System.out.println(v.getNumWheels());
    }

    public int getNumWheels() {
        return numWheels;
    }

    public static void main(String[] args) {
        Vehicle v1 = new Bicycle();
        Bicycle v2 = new Bicycle();
        v1.ride(v2);

    }
}


class Bicycle extends Vehicle {

    int numWheels = 2;

    @Override
    public int getNumWheels() {
        return numWheels;
    }

    void ride(String s) {
        System.out.println(s);
    }

    public void ride(Vehicle v) {
        System.out.println(this.getNumWheels());
    }
}