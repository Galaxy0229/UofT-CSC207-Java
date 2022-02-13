public class MyHashing {
    private int seed = 100;

    public MyHashing(){

    }

    public MyHashing(int seed){
        this.seed = seed;
    }

    public int hash(String str){
        int sum = 0;
        for (int i = 0; i < str.length(); i++){
            sum += (int) (str.charAt(i));
        }
        return sum;
    }

    public int hash(int n){
        int c = this.seed;
        this.seed = n;
        return c;
    }

}