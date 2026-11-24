package Domain.Cart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import Domain.Cart.CartId.CartId;
import Domain.Cart.CartItem.CartItem;
import Domain.Product.Product;
import Domain.Product.Price.Price;
import Domain.Stock.Stock;
import Domain.Stock.Quantity.Quantity;

/**
 * カート集約ルート (Aggregate Root / Entity)
 */
public class Cart {
    private final CartId id;
    private final List<CartItem> items;

    public Cart(CartId id) {
        this.id = Objects.requireNonNull(id, "CartIdは必須です。");
        this.items = new ArrayList<>();
    }

    /**
     * 在庫を確認した上でカートへ商品を追加し、在庫を減算するドメインロジック
     */
    public void addItem(Stock stock, Quantity quantity) {
        Objects.requireNonNull(stock, "Stockは必須です。");
        Objects.requireNonNull(quantity, "数量は必須です。");

        // 在庫の減算
        stock.reduceQuantity(quantity);

        Product product = stock.getProduct();
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (item.product().getId().equals(product.getId())) {
                Quantity newQuantity = item.quantity().add(quantity);
                items.set(i, new CartItem(product, newQuantity));
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    /**
     * カート内商品の合計金額を計算
     */
    public Price calculateTotal() {
        Price total = Price.zero();
        for (CartItem item : items) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    /**
     * カートを空にする
     */
    public void clear() {
        this.items.clear();
    }

    public CartId getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}