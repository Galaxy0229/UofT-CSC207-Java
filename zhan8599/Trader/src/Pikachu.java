public class Pikachu implements AlienAnimal, Tradable{
    private double humanPrice;
    private double alinePrice;

    public Pikachu(double humanPrice, double alinePrice) {
        this.humanPrice = humanPrice;
        this.alinePrice = alinePrice;
    }


    @Override
    public String sound() {
        return "pika pika";
    }

    @Override
    public double getHumanPrice() {
        return this.humanPrice;
    }

    @Override
    public double getAlienPrice() {
        return this.alinePrice;
    }
}
