import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
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
            𝟜. Запись файла
            𝟝. считать файл
            𝟞. сортировка
            𝟟. 
            𝟠.
            𝟡.
          """);
      System.out.print("Введите номер пункта меню: ");
      int choice = Input.readIntLimited(1, 9);
      switch (choice) {
        case 1 -> Input.addTransaction(br, transactionList, currencyList);
        case 2 -> Input.addCategory(br);
        case 3 -> Output.printTransactionAll(transactionList, currencyList,"Показано: 10 из 100     Период: с 01.03.2022 по 20.04.2023");
        case 4 -> Output.writeToFile(transactionList, currencyList);
        case 5 -> Input.readFromFile(transactionList, currencyList);
        case 6 -> Collections.sort(transactionList);



        case 9 -> isPlaying = false;
        default -> System.out.println("default");
      }


//      isPlaying = false;
    }
  }
}

