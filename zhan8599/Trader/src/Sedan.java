public class Sedan implements Vehicle, Tradable{
    private double humanPrice;
    private int speed;

    public Sedan(double humanPrice) {
        this.humanPrice = humanPrice;
        this.speed = 0;
    }

    @Override
    public double getHumanPrice() {
        return this.humanPrice;
    }

    @Override
    public double getAlienPrice() {
        return 50 * this.humanPrice;
    }

    @Override
    public void speedUp() {
        this.speed += 2;
    }

    @Override
    public void speedDown() {
        this.speed -= 2;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }


}
