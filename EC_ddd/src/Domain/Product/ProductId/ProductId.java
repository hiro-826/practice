package Domain.Product.ProductId;

import java.util.Objects;

/**
 * 商品識別子 (Value Object)
 */
public record ProductId(String value) {
    public ProductId {
        Objects.requireNonNull(value, "ProductIdは必須です。");
        if (value.isBlank()) {
            throw new IllegalArgumentException("ProductIdを空文字にすることはできません。");
        }
    }
}