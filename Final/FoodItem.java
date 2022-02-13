public class FoodItem {
    private String name;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addToQuantity(int amount) {
        quantity += amount;
    }
}

class Apple extends FoodItem{
    private int numSeeds;

//    public Apple(int ns){
//        super("Apple", 1);
//        numSeeds=ns;
//    }

    public int getNumSeeds(){
        return numSeeds;
    }

    public void setNumSeeds(int ns){
        numSeeds=ns;
    }
}