package Domain.Product.Price;

import java.util.Objects;

import Domain.Stock.Quantity.Quantity;

/**
 * 価格・金額 (Value Object)
 */
public record Price(int amount) {
    public Price {
        if (amount < 0) {
            throw new IllegalArgumentException("価格は0以上である必要があります。入力値: " + amount);
        }
    }

    public Price multiply(Quantity quantity) {
        return new Price(this.amount * quantity.value());
    }

    public Price add(Price other) {
        Objects.requireNonNull(other, "加算対象のPriceがnullです。");
        return new Price(this.amount + other.amount());
    }

    public static Price zero() {
        return new Price(0);
    }
}