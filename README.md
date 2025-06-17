# 📈 Stock Trend Analyzer

A simple Java program that fetches historical stock data and calculates trend indicators like **Exponential Moving Average (EMA)** based on daily **log returns** or **percent changes**.

---

## 🚀 Features

- Fetches daily stock prices using the **Alpha Vantage API**
- Computes **close-to-close percent returns** or **log returns**
- Calculates the **latest EMA** over a user-defined period
- Supports toggling between percent and log return analysis
- Clean object-oriented structure for further extension

---

## 📦 Technologies Used

- Java 11+
- `HttpClient` for API calls
- `Gson` for JSON parsing
- Alpha Vantage API (free tier)
