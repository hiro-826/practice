package Domain.Stock.StockId;

import java.util.Objects;

/**
 * 在庫識別子 (Value Object)
 */
public record StockId(String value) {
    public StockId {
        Objects.requireNonNull(value, "StockIdは必須です。");
        if (value.isBlank()) {
            throw new IllegalArgumentException("StockIdを空文字にすることはできません。");
        }
    }
}