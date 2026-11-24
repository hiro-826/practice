package Domain.Cart;

import java.util.Optional;

import Domain.Cart.CartId.CartId;

/**
 * カートリポジトリ・インタフェース
 */
public interface ICartRepository {
    Optional<Cart> findById(CartId id);
    void save(Cart cart);
}