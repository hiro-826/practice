package Domain.Product.ProductName;

import java.util.Objects;

/**
 * 商品名 (Value Object)
 */
public record ProductName(String value) {
    public ProductName {
        Objects.requireNonNull(value, "商品名は必須です。");
        if (value.isBlank()) {
            throw new IllegalArgumentException("商品名を空文字にすることはできません。");
        }
    }
}