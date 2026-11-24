package Domain.Stock;

import java.util.Objects;

import Domain.Product.Product;
import Domain.Stock.Quantity.Quantity;
import Domain.Stock.StockId.StockId;

/**
 * 在庫集約ルート (Aggregate Root / Entity)
 */
public class Stock {
    private final StockId id;
    private final Product product;
    private Quantity quantity;

    public Stock(StockId id, Product product, Quantity initialQuantity) {
        this.id = Objects.requireNonNull(id, "StockIdは必須です。");
        this.product = Objects.requireNonNull(product, "Productは必須です。");
        this.quantity = Objects.requireNonNull(initialQuantity, "初期在庫数は必須です。");
    }

    /**
     * 在庫数を引き当てる（減算する）ドメインメソッド
     */
    public void reduceQuantity(Quantity amount) {
        Objects.requireNonNull(amount, "減算する数量は必須です。");
        this.quantity = this.quantity.subtract(amount);
    }

    public StockId getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Stock[id=" + id.value() + ", product=" + product.getName().value() + ", quantity=" + quantity.value() + "]";
    }
}