import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


public class Menu implements Finals {
  private static boolean isPlaying = true;

  public static void menuMain(BufferedReader br, List<Transaction> transactionList,
                               List<Currency> currencyList) throws IOException {
    while (isPlaying) {
      System.out.println();
      System.out.println(BLUE + "[ ОСНОВНОЕ МЕНЮ ]" + RESET);
      System.out.println("""
          Доступные действия:
            𝟙. Создать запись
            𝟚. Добавить категорию
            𝟛. Печатать все записи
            𝟜. 
            𝟝. 
            𝟞. 
            𝟟. 
            𝟠.
            𝟡.
          """);
      System.out.print("Введите номер пункта меню: ");
      int choice = Input.readIntLimited(1, 9);
      switch (choice) {
        case 1 -> Input.transactionRead(br, transactionList,  currencyList);
        case 2 -> Input.addCategory(br);
        case 3 -> Output.printTransactionAll(currencyList,transactionList);



          case 9 -> isPlaying=false;
        default -> System.out.println("default");

      }

    }
  }
}

