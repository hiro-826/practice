package Cart;

import java.util.ArrayList;
import java.util.List;

import Product.Stock;

/**
 * カートを管理するクラス
 */
public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void addItem(Stock stock, int quantity) {
        if (stock.getQuantity() <= 0) {
            throw new IllegalStateException("在庫数が0の商品は購入できません。");
        }
        if (quantity > stock.getQuantity()) {
            throw new IllegalArgumentException("在庫数（" + stock.getQuantity() + "個）を超える指定はできません。");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("数量は1以上を指定してください。");
        }
        items.add(new CartItem(stock.getProduct(), quantity));
    }

    public List<CartItem> getItems() { return items; }

    public int calculateTotal() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public boolean isEmpty() { return items.isEmpty(); }
    public void clear() { items.clear(); }
}
