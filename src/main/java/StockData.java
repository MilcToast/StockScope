public class StockData {


    private final String timestamp;
    private final double close;

    public StockData(String timestamp, double close) {
        this.timestamp = timestamp;
        this.close = close;
    }

    public String getTimestamp() {return timestamp;}
    public double getClose() {return close;}

    @Override
    public String toString() {
        return String.format("Dates: %s | Close: %.2f", timestamp, close);
    }

}
