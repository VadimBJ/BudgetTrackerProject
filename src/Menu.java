import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class Menu implements Finals {

  public static void menuLogin(List<Transaction> transactionList,
                               List<Currency> currencyList) throws IOException {
    System.out.println("""
           Добро пожаловать в бюджет-трекер!
        Для начала работы Вам необходимо авторизоваться.
        Если у вас уже есть учетная запись, введите свой логин и пароль.
        Если Вы новый пользователь, пожалуйста, зарегистрируйтесь.
        Мы гарантируем безопасность ваших данных!
               
          𝟙. Войти под уже существующей учётной записью
          𝟚. Зарегистрировать нового пользователя""");
    System.out.print("Введите номер пункта меню: ");
    int choice = Input.readIntLimited(1, 2);
    switch (choice) {
      case 1 -> Input.readFromEncryptFile(transactionList, currencyList);
      case 2 -> Input.userRead(transactionList, currencyList);
    }
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
            
            9 - тест
            
            𝟘. Выход
          """);
      System.out.print("Введите номер пункта меню: ");
      int choice = Input.readIntLimited(0, 9);
      switch (choice) {
        case 1 -> Input.addTransaction(br, transactionList, currencyList);
        case 2 -> Output.printTransactionAll(br, transactionList, currencyList);
        case 3 -> Output.printTransactionBy10(transactionList, currencyList);
        //todo Посмотреть записи за указанный период времени
        //todo Отфильтровать записи по выбранному критерию

        case 6 -> Input.addCategory(br);

        //todo Добавить новую валюту

        case 8 -> Output.writeToEncryptFile2(transactionList, currencyList);
        case 9 -> Input.testEncrypt();
        case 0 -> System.exit(0); //todo меню для выхода с сохранением данных
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
        System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
        Output.showTransactionById(br, transactionList, currencyList, Input.readIntLimited(1, transactionList.size()));
      }
      case 2 -> {
        System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
        transactionList.remove(Input.readIntLimited(1, transactionList.size()) - 1);
      }
      case 3 -> System.out.println(" ");
      case 4 -> Menu.menuMain(br, transactionList, currencyList);
    }
  }

  public static void menuAfterTransactionListEnds(BufferedReader br, List<Transaction> transactionList,
                                                  List<Currency> currencyList) throws IOException, InterruptedException, AWTException {
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
        Output.showTransactionById(br, transactionList, currencyList, Input.readIntLimited(1, transactionList.size()));
      }
      case 2 -> {
        System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
        transactionList.remove(Input.readIntLimited(1, transactionList.size()) - 1);
      }
      case 3 -> Menu.menuMain(br, transactionList, currencyList);
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

