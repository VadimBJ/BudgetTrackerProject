import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Menu implements Finals {

  public static void menuLogin(List<Transaction> transactionList,
                               List<Currency> currencyList, Map<String, String> userData) throws IOException, InterruptedException {
    boolean isLogin = false;
    do {
      System.out.print("""
            
            𝟙. Войти под уже существующей учётной записью
            𝟚. Зарегистрировать нового пользователя
          Введите номер пункта меню:\u202F""");
      int choice = Input.readIntLimited(1, 2);
      switch (choice) {
        case 1 -> {
          isLogin = Input.userLoginRead(userData);
          if (isLogin) {
            Input.readFromEncryptFile(transactionList, currencyList);
          }
        }
        case 2 -> {
          isLogin = Input.userNewRead(userData);
          if (isLogin) {
            Input.initializeData(currencyList);
          }
        }
      }
    } while (!isLogin);
  }

  public static void menuMain(BufferedReader br, List<Transaction> transactionList,
                              List<Currency> currencyList) throws IOException, AWTException, InterruptedException {
    while (true) {
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
            
            𝟘. Выход
          """);
      System.out.print("Введите номер пункта меню: ");
      int choice = Input.readIntLimited(0, 8);
      switch (choice) {
        case 1 -> Input.addTransaction(br, transactionList, currencyList);
        case 2 -> Output.printTransactionAll(br, transactionList, currencyList);
        case 3 -> Output.printTransactionBy10(transactionList, currencyList);
        //todo Посмотреть записи за указанный период времени
        //todo Отфильтровать записи по выбранному критерию

        case 6 -> Input.addCategory(br);

        //todo Добавить новую валюту

        case 8 -> Output.writeToEncryptFile(transactionList, currencyList);
        case 0 -> System.exit(0); //todo меню для выхода с сохранением данных
      }
    }
  }

  public static void menuAnderTransactionList(BufferedReader br, List<Transaction> transactionList,
                                              List<Currency> currencyList, boolean isList10) throws IOException, InterruptedException, AWTException {
    System.out.println("""
        Доступные действия:
          𝟙. Просмотреть запись по Id
          𝟚. Удалить запись по Id
          𝟛. Вернуться в главное меню
          𝟜. Продолжить просмотр станиц""");
    System.out.print("Введите номер пункта меню: ");
    int choice = Input.readIntLimited(1, 4);
    switch (choice) {
      case 1 -> {
        System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
        Output.showTransactionById(br, transactionList, currencyList, Input.readIntLimited(1, transactionList.size()), isList10);
      }
      case 2 -> {
        System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
        Input.deleteTransaction(transactionList, transactionList.size() - Input.readIntLimited(1, transactionList.size()));
      }
      case 3 -> Menu.menuMain(br, transactionList, currencyList);
      case 4 -> System.out.println(" ");

    }
  }

  public static void menuAfterTransactionListEnds(BufferedReader br, List<Transaction> transactionList,
                                                  List<Currency> currencyList, boolean isList10) throws IOException, InterruptedException, AWTException {
    System.out.println("""
        Доступные действия:
          𝟙. Просмотреть запись по Id
          𝟚. Удалить запись по Id
          𝟛. Вернуться в главное меню""");
    System.out.print("Введите номер пункта меню: ");
    int choice = Input.readIntLimited(1, 3);
    switch (choice) {
      case 1 -> {
        System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
        Output.showTransactionById(br, transactionList, currencyList, Input.readIntLimited(1, transactionList.size()), isList10);
      }
      case 2 -> {
        System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
        Input.deleteTransaction(transactionList, transactionList.size() - Input.readIntLimited(1, transactionList.size()));
      }
      case 3 -> Menu.menuMain(br, transactionList, currencyList);
    }
  }

  public static void menuAnderTransactionView(BufferedReader br, List<Transaction> transactionList,
                                              List<Currency> currencyList, int index, boolean isList10) throws IOException, InterruptedException, AWTException {
    System.out.println("""
        Доступные действия:
          𝟙. Удалить эту запись
          𝟚. Редактировать эту запись
          𝟛. Вернуться в главное меню""");
    int choice;
    if (isList10) {
      System.out.println("  𝟜. Продолжить просмотр станиц");
      System.out.print("Введите номер пункта меню: ");
      choice = Input.readIntLimited(1, 4);
    } else {
      System.out.print("Введите номер пункта меню: ");
      choice = Input.readIntLimited(1, 3);
    }
    switch (choice) {
      case 1 -> Input.deleteTransaction(transactionList, index); //todo учитывать в общей сумме
      case 2 -> System.out.println(" ");//todo 2. редактировать запись
      case 3 -> Menu.menuMain(br, transactionList, currencyList);
      case 4 -> System.out.print(" ");
    }
  }

}

