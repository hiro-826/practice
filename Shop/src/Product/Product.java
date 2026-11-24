package Product;

/**
 * 商品情報を保持するクラス
 */
public class Product {
    private final int id;
    private final String name;
    private final int price;

    public Product(int id, String name, int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("金額は正の整数である必要があります。");
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%d円)", id, name, price);
    }
}