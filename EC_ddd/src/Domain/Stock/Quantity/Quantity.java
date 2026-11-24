package Domain.Stock.Quantity;

/**
 * 数量 (Value Object)
 */
public record Quantity(int value) {
    public Quantity {
        if (value < 0) {
            throw new IllegalArgumentException("数量は0以上である必要があります。入力値: " + value);
        }
    }

    public Quantity add(Quantity amount) {
        return new Quantity(this.value + amount.value());
    }

    public Quantity subtract(Quantity amount) {
        if (this.value < amount.value()) {
            throw new IllegalStateException("在庫数量を超えて減算することはできません。現在の数量: " + this.value + ", 減算量: " + amount.value());
        }
        return new Quantity(this.value - amount.value());
    }
}
