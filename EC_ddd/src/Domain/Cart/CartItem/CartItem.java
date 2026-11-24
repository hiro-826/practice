package Domain.Cart.CartItem;

import java.util.Objects;

import Domain.Product.Product;
import Domain.Product.Price.Price;
import Domain.Stock.Quantity.Quantity;

/**
 * カート明細 (Value Object)
 */
public record CartItem(Product product, Quantity quantity) {
    public CartItem {
        Objects.requireNonNull(product, "商品は必須です。");
        Objects.requireNonNull(quantity, "数量は必須です。");
        if (quantity.value() <= 0) {
            throw new IllegalArgumentException("カートに追加する数量は1以上である必要があります。");
        }
    }

    public Price getSubtotal() {
        return product.getPrice().multiply(quantity);
    }
}