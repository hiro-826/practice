package Application.Stock.GetStockList;

public record StockDto(String stockId, String productId, String productName, int price, int quantity) {}