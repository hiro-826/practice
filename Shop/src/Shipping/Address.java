package Shipping;

/**
 * 配送先住所を保持するクラス
 */
public class Address {
    private final String value;

    public Address(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("住所を入力してください。");
        }
        this.value = value;
    }

    public String getValue() { return value; }

    @Override
    public String toString() {
        return value;
    }
}