package Domain.Product;

import java.util.Objects;

import Domain.Product.Price.Price;
import Domain.Product.ProductId.ProductId;
import Domain.Product.ProductName.ProductName;

/**
 * 商品エンティティ
 */
public class Product {
    private final ProductId id;
    private final ProductName name;
    private final Price price;

    public Product(ProductId id, ProductName name, Price price) {
        this.id = Objects.requireNonNull(id, "ProductIdは必須です。");
        this.name = Objects.requireNonNull(name, "ProductNameは必須です。");
        this.price = Objects.requireNonNull(price, "Priceは必須です。");
    }

    public ProductId getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product[id=" + id.value() + ", name=" + name.value() + ", price=" + price.amount() + "円]";
    }
}