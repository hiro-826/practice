package Shipping;

import java.util.List;

import Cart.CartItem;

/**
 * 発送先と購入品をまとめて出力するクラス
 */
public class Shipment {
    private final Address address;
    private final List<CartItem> items;

    public Shipment(Address address, List<CartItem> items) {
        this.address = address;
        this.items = List.copyOf(items);
    }

    public void printShippingSlip() {
        System.out.println("\n==========================================");
        System.out.println("            【商品発送伝票】              ");
        System.out.println("==========================================");
        System.out.println("■ お届け先住所 : " + address);
        System.out.println("------------------------------------------");
        System.out.println("■ 発送商品一覧:");
        for (CartItem item : items) {
            System.out.printf("  ・ %-15s x %d個 (小計: %d円)\n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getSubtotal());
        }
        System.out.println("==========================================\n");
    }
}
