package Application.Stock.GetStockList;

import java.util.List;

import Domain.Stock.IStockRepository;

public class GetStockListApplicationService {
	private final IStockRepository stockRepository;

	public GetStockListApplicationService(IStockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public List<StockDto> execute() {
		return stockRepository.findAll().stream()
			.map(stock -> new StockDto(
				stock.getId().value(),
				stock.getProduct().getId().value(),
				stock.getProduct().getName().value(),
				stock.getProduct().getPrice().amount(),
				stock.getQuantity().value()
			))
			.toList();
	}
}