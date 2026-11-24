package Product;

/**
 * 在庫情報を保持・更新するクラス
 */
public class Stock {
    private final Product product;
    private int quantity;

    public Stock(Product product, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("在庫数は0以上である必要があります。");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    // 在庫減少処理
    public void reduceQuantity(int amount) {
        if (amount > this.quantity) {
            throw new IllegalStateException("在庫数を超えて購入することはできません。");
        }
        this.quantity -= amount;
    }

    @Override
    public String toString() {
        return String.format("%s - 在庫: %d個", product.toString(), quantity);
    }
}