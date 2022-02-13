public class Fruit {
    public String name;
    public static int numFruit = 1;
    private boolean canGroew = true;

    //option 1
    public Fruit(){}

    public Fruit(String name){this.name = name;}
    // this 等同于python的self

    public Fruit(String name, int numFruit){
        this.name  = name;
        this.numFruit = numFruit;
    }
}