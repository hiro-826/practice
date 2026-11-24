package Cart;

import Product.Product;

/**
 * カート内の一点を保持するクラス
 */
public class CartItem {
    private final Product product;
    private final int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public int getSubtotal() {
        return product.getPrice() * quantity;
    }
}