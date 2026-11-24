package Application.Cart.AddToCartApplicationService;

import Domain.Cart.Cart;
import Domain.Cart.ICartRepository;
import Domain.Cart.CartId.CartId;
import Domain.Stock.IStockRepository;
import Domain.Stock.Stock;
import Domain.Stock.Quantity.Quantity;
import Domain.Stock.StockId.StockId;

public class AddToCartApplicationService {
    private final ICartRepository cartRepository;
    private final IStockRepository stockRepository;

    public AddToCartApplicationService(ICartRepository cartRepository, IStockRepository stockRepository) {
        this.cartRepository = cartRepository;
        this.stockRepository = stockRepository;
    }

    public void execute(CartId cartId, StockId stockId, int quantityValue) {
        Cart cart = cartRepository.findById(cartId)
                .orElseGet(() -> new Cart(cartId));
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("指定された在庫が見つかりません。 ID: " + stockId.value()));

        cart.addItem(stock, new Quantity(quantityValue));

        stockRepository.save(stock);
        cartRepository.save(cart);
    }
}