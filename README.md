# 📈 Stock Trend Analyzer

A **Java-based program** that fetches and analyzes **historical stock data** using the **Alpha Vantage API**. Supports **multiple stock tickers**, calculates key **technical indicators**, and lays the groundwork for **portfolio-level analysis**.

---

## 🚀 Features

- Fetches daily stock data using the **Alpha Vantage API**
- Computes:
    - Exponential Moving Average (EMA)
    - Simple Moving Average (SMA)
    - Percent & Log Returns
    - Rolling Standard Deviation (volatility)
- Supports **multi-stock input** in a single run
- Clean object-oriented structure for further extension like:
    - Weighted portfolio returns
    - Covariance/Correlation analysis
    - Sharpe ratio

---

## 📦 Technologies Used

- Java 17+
- `HttpClient` for API calls
- `Gson` for JSON parsing
- **Alpha Vantage API** for stock market data
