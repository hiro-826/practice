import Application.Cart.AddToCartApplicationService.AddToCartApplicationService;
import Application.Cart.GetCart.GetCartApplicationService;
import Application.Order.ConfirmOrderApplicationService.ConfirmOrderApplicationService;
import Application.Stock.GetStockList.GetStockListApplicationService;
import Domain.Cart.ICartRepository;
import Domain.Product.Product;
import Domain.Product.Price.Price;
import Domain.Product.ProductId.ProductId;
import Domain.Product.ProductName.ProductName;
import Domain.Stock.IStockRepository;
import Domain.Stock.Stock;
import Domain.Stock.Quantity.Quantity;
import Domain.Stock.StockId.StockId;
import Infrastructure.InMemory.Cart.InMemoryCartRepository;
import Infrastructure.InMemory.Stock.InMemoryStockRepository;
import Presentation.Program;

public class Main {
	public static void main(String[] args) {
		IStockRepository stockRepository = new InMemoryStockRepository();
		ICartRepository cartRepository = new InMemoryCartRepository();

		stockRepository.save(new Stock(new StockId("S001"), new Product(new ProductId("P001"), new ProductName("Java入門書"), new Price(3200)), new Quantity(10)));
		stockRepository.save(new Stock(new StockId("S002"), new Product(new ProductId("P002"), new ProductName("ワイヤレスマウス"), new Price(2500)), new Quantity(5)));
		stockRepository.save(new Stock(new StockId("S003"), new Product(new ProductId("P003"), new ProductName("USBメモリ 64GB"), new Price(1200)), new Quantity(0)));

		AddToCartApplicationService addToCartService = new AddToCartApplicationService(cartRepository, stockRepository);
		ConfirmOrderApplicationService confirmOrderService = new ConfirmOrderApplicationService(cartRepository);
		GetStockListApplicationService getStockListService = new GetStockListApplicationService(stockRepository);
		GetCartApplicationService getCartService = new GetCartApplicationService(cartRepository);

		Program program = new Program(addToCartService, confirmOrderService, getStockListService, getCartService);
		program.run();
	}
}