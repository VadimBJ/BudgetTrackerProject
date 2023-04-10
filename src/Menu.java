import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Menu implements Finals {
  private static boolean isPlaying = true;

  public static void menuMain(BufferedReader br, List<Transaction> transactionList,
                              List<Currency> currencyList) throws IOException, AWTException, InterruptedException {
    while (isPlaying) {
      System.out.println(LOGO2);
      System.out.println();
      System.out.println(BLUE + "[ ОСНОВНОЕ МЕНЮ ]" + RESET);
      System.out.println("""
          Доступные действия:
            𝟙. Создать новую запись
            𝟚. Посмотреть все записи одним списком
            𝟛. Посмотреть все записи списками по 10
            𝟜. Посмотреть записи за указанный период времени
            𝟝. Отфильтровать записи по выбранному критерию
            𝟞. Добавить новую категорию
            𝟟. Добавить новую валюту
            𝟠. Сохранить все данные в файл
            𝟡. Выход
          """);
      System.out.print("Введите номер пункта меню: ");
      int choice = Input.readIntLimited(1, 9);
      switch (choice) {
        case 1 -> Input.addTransaction(br, transactionList, currencyList);
        case 2 -> Output.printTransactionAll(transactionList, currencyList);
        case 3 -> Output.printTransactionBy10(br,transactionList, currencyList);

        case 6 -> Input.addCategory(br);

        case 8 -> Output.writeToFile(transactionList, currencyList);
        case 9 -> isPlaying = false;
        default -> System.out.println("default");
      }


//      isPlaying = false;
    }
  }
}

