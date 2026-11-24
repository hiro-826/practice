package Presentation;

import java.util.List;
import java.util.Scanner;

import Application.Cart.AddToCartApplicationService.AddToCartApplicationService;
import Application.Cart.GetCart.CartDto;
import Application.Cart.GetCart.GetCartApplicationService;
import Application.Order.ConfirmOrderApplicationService.ConfirmOrderApplicationService;
import Application.Stock.GetStockList.GetStockListApplicationService;
import Application.Stock.GetStockList.StockDto;
import Domain.Cart.ICartRepository;
import Domain.Cart.CartId.CartId;
import Domain.Product.Product;
import Domain.Product.Price.Price;
import Domain.Product.ProductId.ProductId;
import Domain.Product.ProductName.ProductName;
import Domain.Shipping.Address.Address;
import Domain.Stock.IStockRepository;
import Domain.Stock.Stock;
import Domain.Stock.Quantity.Quantity;
import Domain.Stock.StockId.StockId;
import Infrastructure.InMemory.Cart.InMemoryCartRepository;
import Infrastructure.InMemory.Stock.InMemoryStockRepository;

public class Program {
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

	private final AddToCartApplicationService addToCartService;
	private final ConfirmOrderApplicationService confirmOrderService;
	private final GetStockListApplicationService getStockListService;
	private final GetCartApplicationService getCartService;

	public Program(
		AddToCartApplicationService addToCartService,
		ConfirmOrderApplicationService confirmOrderService,
		GetStockListApplicationService getStockListService,
		GetCartApplicationService getCartService
	) {
		this.addToCartService = addToCartService;
		this.confirmOrderService = confirmOrderService;
		this.getStockListService = getStockListService;
		this.getCartService = getCartService;
	}

	public void run() {
		CartId cartId = new CartId("C001");

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("==========================================");
			System.out.println("            ECサイトへようこそ            ");
			System.out.println("==========================================");

			Address shippingAddress = promptForAddress(scanner);

			boolean isLoop = true;
			while (isLoop) {
				List<StockDto> stocks = getStockListService.execute();
				showMenu(stocks);
				System.out.print("番号を選択してください > ");

				try {
					int input = Integer.parseInt(scanner.nextLine());
					if (input >= 1 && input <= stocks.size()) {
						selectProduct(scanner, cartId, stocks.get(input - 1));
					} else if (input == 4) {
						checkout(scanner, cartId, shippingAddress);
					} else if (input == 0) {
						System.out.println("利用を終了します");
						isLoop = false;
					} else {
						System.out.println("無効な入力です");
					}
				} catch (NumberFormatException e) {
					System.out.println("【エラー】数字を入力してください。");
				}
			}
		}
	}

	private Address promptForAddress(Scanner scanner) {
		while (true) {
			try {
				System.out.print("住所を入力してください (例：大阪府吹田市1-1) > ");
				String input = scanner.nextLine();
				return new Address(input);
			} catch (IllegalArgumentException | NullPointerException e) {
				System.out.println("入力された住所が無効です。再度入力してください。");
			}
		}
	}

	private void showMenu(List<StockDto> stocks) {
		System.out.println("\n-----商品一覧----");
		for (int i = 0; i < stocks.size(); i++) {
			StockDto stock = stocks.get(i);
			System.out.println((i + 1) + ": [" + (i + 1) + "] " + stock.productName() + " (" + stock.price() + "円) - 在庫: " + stock.quantity() + "個");
		}
		System.out.println("-----------------");
		System.out.println("4: カートの確認と購入手続き");
		System.out.println("0: 終了");
	}

	private void selectProduct(Scanner scanner, CartId cartId, StockDto stock) {
		if (stock.quantity() <= 0) {
			System.out.println("【エラー】「" + stock.productName() + "」は在庫切れ（0個）のため購入できません。");
			return;
		}

		System.out.print("「" + stock.productName() + "」を何個購入しますか？ > ");
		try {
			int count = Integer.parseInt(scanner.nextLine());
			addToCartService.execute(cartId, new StockId(stock.stockId()), count);
			System.out.println("カートに「" + stock.productName() + "」を " + count + " 個追加しました。");
		} catch (Exception e) {
			System.out.println("【エラー】" + e.getMessage());
		}
	}

	private void checkout(Scanner scanner, CartId cartId, Address shippingAddress) {
		CartDto cart = getCartService.execute(cartId);
		if (cart.isEmpty()) {
			System.out.println("\nカートは空です。");
			return;
		}

		System.out.println("\n----- カートの内容 -----");
		cart.items().forEach(item ->
			System.out.println("・" + item.productName() + " x " + item.quantity() + "個 = 小計 " + item.subtotal() + "円")
		);
		System.out.println("------------------------");
		System.out.println("合計金額: " + cart.totalAmount() + " 円");
		System.out.println("配送先: " + shippingAddress.value());

		System.out.print("\n購入を確定しますか？ (1: はい / 0: いいえ) > ");
		try {
			int choice = Integer.parseInt(scanner.nextLine());
			if (choice == 1) {
				confirmOrderService.execute(cartId, shippingAddress);
				System.out.println("購入処理が完了しました。");
			} else {
				System.out.println("購入手続きをキャンセルしました。");
			}
		} catch (Exception e) {
			System.out.println("【エラー】数字で選択してください。");
		}
	}
}