package Domain.Cart.CartId;

import java.util.Objects;

/**
 * カート識別子 (Value Object)
 */
public record CartId(String value) {
    public CartId {
        Objects.requireNonNull(value, "CartIdは必須です。");
        if (value.isBlank()) {
            throw new IllegalArgumentException("CartIdを空文字にすることはできません。");
        }
    }
}