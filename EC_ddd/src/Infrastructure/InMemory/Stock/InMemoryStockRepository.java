package Infrastructure.InMemory.Stock;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import Domain.Product.ProductId.ProductId;
import Domain.Stock.IStockRepository;
import Domain.Stock.Stock;
import Domain.Stock.StockId.StockId;

public class InMemoryStockRepository implements IStockRepository {
	private final Map<StockId, Stock> store = new ConcurrentHashMap<>();

	@Override
	public Optional<Stock> findById(StockId id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Optional<Stock> findByProductId(ProductId productId) {
		return store.values().stream()
				.filter(stock -> stock.getProduct().getId().equals(productId))
				.findFirst();
	}

	@Override
	public List<Stock> findAll() {
		return List.copyOf(store.values());
	}

	@Override
	public void save(Stock stock) {
		store.put(stock.getId(), stock);
	}
}