package Domain.Shipping;

import java.util.List;
import java.util.Objects;

import Domain.Cart.CartItem.CartItem;
import Domain.Shipping.Address.Address;

/**
 * 発送手配・伝票出力 (Value Object)
 */
public class Shipment {
    private final Address address;
    private final List<CartItem> items;

    public Shipment(Address address, List<CartItem> items) {
        this.address = Objects.requireNonNull(address, "Addressは必須です。");
        this.items = List.copyOf(items);
    }

    /**
     * CLIへ発送伝票を出力
     */
    public void printShippingSlip() {
        System.out.println("========================================");
        System.out.println(" 発送伝票");
        System.out.println(" お届け先: " + address.value());
        System.out.println("----------------------------------------");
        System.out.println(" 品目一覧:");
        for (CartItem item : items) {
            System.out.printf("  - %s x %d個 (小計: %d円)%n",
                    item.product().getName().value(),
                    item.quantity().value(),
                    item.getSubtotal().amount());
        }
        System.out.println("========================================");
    }

    public Address getAddress() {
        return address;
    }

    public List<CartItem> getItems() {
        return items;
    }
}