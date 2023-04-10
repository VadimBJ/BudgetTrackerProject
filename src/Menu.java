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
      Output.clearScreen();
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
            𝟡.
            
            𝟘. Выход
          """);
      System.out.print("Введите номер пункта меню: ");
      int choice = Input.readIntLimited(0, 9);
      switch (choice) {
        case 1 -> Input.addTransaction(br, transactionList, currencyList);
        case 2 -> Output.printTransactionAll(transactionList, currencyList);
        case 3 -> Output.printTransactionBy10(transactionList, currencyList);

        case 6 -> Input.addCategory(br);

        case 8 -> Output.writeToFile(transactionList, currencyList);
        case 9 -> isPlaying = false;
        default -> System.out.println("default");
      }
    }
  }

  public static void menuAnderTransactionList(BufferedReader br, List<Transaction> transactionList,
                                              List<Currency> currencyList) throws IOException, InterruptedException, AWTException {
    System.out.println("""
        Доступные действия:
          𝟙. Просмотреть запись по Id
          𝟚. Удалить запись по Id
          𝟛. Продолжить просмотр станиц
          𝟜. Вернуться в главное меню""");
    System.out.print("Введите номер пункта меню: ");
    int choice = Input.readIntLimited(1, 4);
    switch (choice) {
      case 1 -> {
        System.out.print(BLUE+"Введите Id нужной записи: "+RESET);
        Output.showTransactionById(br, transactionList, currencyList, Input.readIntLimited(1, transactionList.size()));
      }

      case 3 -> System.out.println(" ");
      case 4 -> Menu.menuMain(br, transactionList, currencyList);
      default -> System.out.println(" 2");
    }
  }

  public static void menuAnderTransactionView(BufferedReader br, List<Transaction> transactionList,
                                              List<Currency> currencyList, int index) throws IOException, InterruptedException, AWTException {
    System.out.println("""
        Доступные действия:
          𝟙. Удалить эту запись
          𝟚. Продолжить просмотр станиц
          𝟛. Вернуться в главное меню""");
    System.out.print("Введите номер пункта меню: ");
    int choice = Input.readIntLimited(1, 3);
    switch (choice) {
      case 1 -> transactionList.remove(index);
      case 2 -> System.out.println(" ");
      case 3 -> Menu.menuMain(br, transactionList, currencyList);
      default -> System.out.println(" 2");
    }
  }

  }

