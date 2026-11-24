package Domain.Shipping.Address;

import java.util.Objects;

/**
 * 配送先住所 (Value Object)
 */
public record Address(String value) {
    public Address {
        Objects.requireNonNull(value, "住所を入力してください。");
        if (value.isBlank()) {
            throw new IllegalArgumentException("住所を空文字にすることはできません。");
        }
    }
}