import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Menu {
  public static void menuMain(BufferedReader br, List<Transaction> transactionList,
                              List<Category> categoryList,List<Currency> currencyList) throws IOException {
    System.out.println("""
        Доступные действия:
          1. Добавить транзакцию
        """);
    System.out.print("Введите номер пункта меню: ");
    int choice = Input.readIntLimited(1, 2);
    switch (choice) {
      case 1 -> Input.transactionRead(br,transactionList,categoryList,currencyList);
      default -> System.out.println("default");
    }

  }
}
