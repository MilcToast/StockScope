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
}
