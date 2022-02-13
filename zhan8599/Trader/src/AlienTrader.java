import java.util.List;
public class AlienTrader extends Trader {
    public AlienTrader(double money, List<Tradable> inventory, boolean bilingual) {
        super(inventory, money, bilingual);
    }

    @Override
    public boolean isHuman() {
        return false;
    }
}