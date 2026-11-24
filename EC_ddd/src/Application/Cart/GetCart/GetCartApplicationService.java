package Application.Cart.GetCart;

import Domain.Cart.Cart;
import Domain.Cart.ICartRepository;
import Domain.Cart.CartId.CartId;

public class GetCartApplicationService {
	private final ICartRepository cartRepository;

	public GetCartApplicationService(ICartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}

	public CartDto execute(CartId cartId) {
		Cart cart = cartRepository.findById(cartId).orElseGet(() -> new Cart(cartId));
		var itemDtos = cart.getItems().stream()
			.map(item -> new CartItemDto(
				item.product().getName().value(),
				item.quantity().value(),
				item.getSubtotal().amount()
			))
			.toList();
		return new CartDto(itemDtos, cart.calculateTotal().amount(), cart.isEmpty());
	}
}