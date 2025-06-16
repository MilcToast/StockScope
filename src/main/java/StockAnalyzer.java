import java.util.ArrayList;
import java.util.List;
public class StockAnalyzer {
    private final List<StockData> entries;

    public StockAnalyzer(List<StockData> entries) {
        this.entries = entries;
    }

    // Calculate the Exponential Moving Average (EMA)
    public List<Double> calculateEMA(int period) {
        List<Double> ema = new ArrayList<>();
        if (entries.size() < period) return ema;

        double alpha = 2.0/(period + 1);

        // Calculate Simple Average
        for (int i = 0; i < entries.size(); i++) {

        }

        return ema;
    }
}
