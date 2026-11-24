import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Cart.Cart;
import Cart.CartItem;
import Product.Product;
import Product.Stock;
import Shipping.Address;
import Shipping.Shipment;

public class Main {
    
    private final Scanner scan = new Scanner(System.in);
    private final List<Stock> stocks = new ArrayList<>();
    private final Cart cart = new Cart();
    private Address shippingAddress;

    // コンストラクタで商品と初期在庫を用意
    public Main() {
        stocks.add(new Stock(new Product(1, "Java入門書", 3200), 10));
        stocks.add(new Stock(new Product(2, "ワイヤレスマウス", 2500), 5));
        stocks.add(new Stock(new Product(3, "USBメモリ 64GB", 1200), 0)); // 在庫0の商品
    }

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        System.out.println("==========================================");
        System.out.println("            ECサイトへようこそ            ");
        System.out.println("==========================================");
        
        // 配送先の入力
        inputShippingAddress();
        
        boolean isLoop = true;
        
        while(isLoop) {
            showMenu();
            System.out.print("番号を選択してください > ");
            
            try {
                int input = Integer.parseInt(scan.next());
                
                switch(input) {
                    case 1:
                        selectProduct(stocks.get(0));
                        continue;
                    case 2:
                        selectProduct(stocks.get(1));
                        continue;
                    case 3:
                        selectProduct(stocks.get(2));
                        continue;
                    case 4:
                        cartItem();
                        break;
                    case 0:
                        System.out.println("利用を終了します");
                        return;
                    default:
                        System.out.println("無効な入力です");
                        continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("【エラー】数字を入力してください。");
            }
        }
    }
    
    private void inputShippingAddress() {
        System.out.print("住所を入力してください (例：大阪府吹田市1-1) > ");
        String inputAddress = scan.next();
        
        // 入力文字列から住所オブジェクトを生成して保持
        this.shippingAddress = new Address(inputAddress);
        System.out.println("配送先を設定しました: " + shippingAddress);
    }
    
    private void showMenu() {
        System.out.println("\n-----商品一覧----");
        System.out.println("1: " + stocks.get(0));
        System.out.println("2: " + stocks.get(1));
        System.out.println("3: " + stocks.get(2));
        System.out.println("-----------------");
        System.out.println("4: カートの確認と購入手続き");
        System.out.println("0: 終了");
    }


    // カート追加の共通処理（商品・在庫オブジェクトとの連携）
    private void selectProduct(Stock stock) {
        if (stock.getQuantity() <= 0) {
            System.out.println("【エラー】「" + stock.getProduct().getName() + "」は在庫切れ（0個）のため購入できません。");
            return;
        }

        System.out.print("「" + stock.getProduct().getName() + "」を何個購入しますか？ > ");
        try {
            int count = Integer.parseInt(scan.next());
            
            // カートオブジェクトに追加（在庫オーバーや負の数のチェックは Cart クラス内で行われます）
            cart.addItem(stock, count);
            System.out.println("カートに「" + stock.getProduct().getName() + "」を " + count + " 個追加しました。");
        } catch (Exception e) {
            System.out.println("【エラー】" + e.getMessage());
        }
    }

    // カートの確認と購入手続き
    private void cartItem() {
        if (cart.isEmpty()) {
            System.out.println("\nカートは空です。");
            return;
        }

        System.out.println("\n----- カートの内容 -----");
        for (CartItem item : cart.getItems()) {
            System.out.println("・" + item.getProduct().getName() + " x " + item.getQuantity() + "個 = 小計 " + item.getSubtotal() + "円");
        }
        System.out.println("------------------------");
        System.out.println("合計金額: " + cart.calculateTotal() + " 円");
        System.out.println("配送先: " + shippingAddress);

        System.out.print("\n購入を確定しますか？ (1: はい / 0: いいえ) > ");
        try {
            int choice = Integer.parseInt(scan.next());
            if (choice == 1) {
                // 在庫の減少処理
                for (CartItem item : cart.getItems()) {
                    for (Stock stock : stocks) {
                        if (stock.getProduct().getId() == item.getProduct().getId()) {
                            stock.reduceQuantity(item.getQuantity());
                        }
                    }
                }

                // 発送伝票オブジェクトを生成してアウトプット
                Shipment shipment = new Shipment(shippingAddress, cart.getItems());
                shipment.printShippingSlip();

                cart.clear();
                System.out.println("購入処理が完了しました。");
            } else {
                System.out.println("購入手続きをキャンセルしました。");
            }
        } catch (Exception e) {
            System.out.println("【エラー】数字で選択してください。");
        }
    }
}