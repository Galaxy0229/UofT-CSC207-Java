import java.util.ArrayList;
import java.util.List;

public abstract class Trader {
    private List<Tradable> inventory;
    private List<Tradable> wishlist;
    private double money;
    private boolean bilingual;

    // Your code goes here.

    public Trader(List<Tradable> inventory, double money, boolean bilingual) {
        this.inventory = inventory;
        this.money = money;
        this.bilingual = bilingual;
        this.wishlist = new ArrayList<Tradable>();
    }

    public abstract boolean isHuman();

    public void addToWishlist (Tradable t) {
        this.wishlist.add(t);
    }

    public boolean sellTo (Trader r) {
        if(!this.bilingual && !r.isBilingual() && this.isHuman() != r.isHuman()) {
            return false;
        }
        ArrayList<Tradable> wishListTemp = new ArrayList<Tradable>();
        for(Tradable wishItem:r.getWishlist()) {
            double availablePrice = r.isHuman() ? wishItem.getHumanPrice() : wishItem.getAlienPrice();
            if (this.inventory.contains(wishItem) && availablePrice <= r.getMoney()) {
                this.inventory.remove(wishItem);
                r.getInventory().add(wishItem);
                wishListTemp.add(wishItem);
                r.setMoney(r.getMoney() - availablePrice);
                this.money += this.isHuman() ? wishItem.getHumanPrice() : wishItem.getAlienPrice();
            }
        }
        r.getWishlist().removeAll(wishListTemp);
        return true;
    }

    public boolean buyFrom (Trader r) {
        return r.sellTo(this);
    }

    public List<Tradable> getInventory() {
        return inventory;
    }

    public List<Tradable> getWishlist() {
        return wishlist;
    }

    public double getMoney() {
        return money;
    }

    public boolean isBilingual() {
        return bilingual;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}