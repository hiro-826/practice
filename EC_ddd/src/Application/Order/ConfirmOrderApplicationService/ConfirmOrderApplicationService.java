package Application.Order.ConfirmOrderApplicationService;

import Domain.Cart.Cart;
import Domain.Cart.ICartRepository;
import Domain.Cart.CartId.CartId;
import Domain.Shipping.Shipment;
import Domain.Shipping.Address.Address;

public class ConfirmOrderApplicationService {
    private final ICartRepository cartRepository;

    public ConfirmOrderApplicationService(ICartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Shipment execute(CartId cartId, Address address) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("カートが見つかりません。 ID: " + cartId.value()));

        if (cart.isEmpty()) {
            throw new IllegalStateException("カートが空のため注文を確定できません。");
        }

        Shipment shipment = new Shipment(address, cart.getItems());
        shipment.printShippingSlip();

        cart.clear();
        cartRepository.save(cart);

        return shipment;
    }
}