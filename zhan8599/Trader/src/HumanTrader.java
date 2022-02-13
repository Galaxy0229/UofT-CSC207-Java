import java.util.List;

public class HumanTrader extends Trader {
    public HumanTrader(double money, List<Tradable> inventory, boolean bilingual) {
        super(inventory, money, bilingual);
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}