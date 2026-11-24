package Infrastructure.InMemory.Cart;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import Domain.Cart.Cart;
import Domain.Cart.ICartRepository;
import Domain.Cart.CartId.CartId;

public class InMemoryCartRepository implements ICartRepository {
    private final Map<CartId, Cart> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Cart> findById(CartId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void save(Cart cart) {
        store.put(cart.getId(), cart);
    }
}