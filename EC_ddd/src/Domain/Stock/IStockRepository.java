package Domain.Stock;

import java.util.List;
import java.util.Optional;

import Domain.Product.ProductId.ProductId;
import Domain.Stock.StockId.StockId;

public interface IStockRepository {
	Optional<Stock> findById(StockId id);
	Optional<Stock> findByProductId(ProductId productId);
	List<Stock> findAll();
	void save(Stock stock);
}