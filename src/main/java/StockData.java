public class StockData {


    private final String timestamp;
    private final double open;
    private final double close;

    public StockData(String timestamp, double open, double close) {
        this.timestamp = timestamp;
        this.open = open;
        this.close = close;
    }

    public String getTimestamp() {return timestamp;}
    public double getOpen() {return open;}
    public double getClose() {return close;}

    @Override
    public String toString() {
        return String.format("Dates: %s | Open: %.2f | Close: %.2f", timestamp, open, close);
    }

}
