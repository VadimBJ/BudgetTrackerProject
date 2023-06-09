import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Menu implements Finals {
  private static boolean isRepeat;

  /**
   * стартовое меню для авторизации пользователя
   *
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   * @param userData        данные пользователей для авторизации
   */
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

  /**
   * главное меню программы
   *
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   */
  public static void menuMain(List<Transaction> transactionList,
                              List<Currency> currencyList) throws IOException, AWTException, InterruptedException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    isRepeat = true;
    while (isRepeat) {
      Output.clearScreen();
      int size = transactionList.size();
      String transactionCount = "Создано записей: "+size;
      String logo2 = String.format("""
              
      %s   ┌─                ─┐
      %s═══╡│%s Budget Tracker%s │╞════════━━━━━┅┅┅┅┅┅┄┄%s┄┄┄┈┈┈┈
      %s   └─                ─┘%s%27s%s
        """, YELLOW, CYAN, YELLOW, CYAN, YELLOW, CYAN, YELLOW, transactionCount, RESET);

      System.out.printf(logo2);
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
            𝟠. Просмотреть или редактировать категории/валюты
            𝟡. Сохранить все данные в файл
            
            𝟘. Выход
          """);
      System.out.print("Введите номер пункта меню: ");
      int choice = Input.readIntLimited(0, 9);
      switch (choice) {
        case 1 -> Input.addTransaction(br, transactionList, currencyList);
        case 2 -> Output.printTransactionAll(br, transactionList, currencyList);
        case 3 -> Output.printTransactionBy10(transactionList, currencyList);
        case 4 -> menuPrintTransactionByDate(br, transactionList, currencyList);
        case 5 -> menuFilterTransaction(br, transactionList, currencyList);
        case 6 -> Input.addCategory(br);
        case 7 -> Input.addCurrency(br, currencyList);
        case 8 -> menuEditCategoryCurrency(br, currencyList);
        case 9 -> Output.writeToEncryptFile(transactionList, currencyList);
        case 0 -> menuExit(transactionList, currencyList);
      }
    }
  }

  /**
   * меню для выхода из программы
   *
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   */
  public static void menuExit(List<Transaction> transactionList, List<Currency> currencyList) throws IOException,
      InterruptedException, AWTException {
    System.out.println();
    System.out.println(BLUE + "[ ВЫХОД ИЗ ПРОГРАММЫ ]" + RESET);
    System.out.println("""
        Доступные действия:
          𝟙. Сменить учетную запись
          𝟚. Выйти из программы""");
    System.out.print("Введите номер пункта меню: ");
    int choice = Input.readIntLimited(1, 2);
    switch (choice) {
      case 1 -> {
        isRepeat = false;
        Output.writeToEncryptFile(transactionList, currencyList);
        Output.clearScreen();
        BudgetTrackerRunner.main(new String[]{});
      }
      case 2 -> {
        isRepeat = false;
        Output.writeToEncryptFile(transactionList, currencyList);
        System.out.println();
        System.out.println(BLUE + "До скорой встречи!" + RESET);
        System.out.println();
        System.exit(0);
      }
    }
  }

  /**
   * меню для выбора способа фильтрации записей
   *
   * @param br              BufferedReader, для считывания ввода пользователя
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   */
  public static void menuFilterTransaction(BufferedReader br, List<Transaction> transactionList,
                                           List<Currency> currencyList) throws IOException, InterruptedException, AWTException {
    System.out.println();
    System.out.println(BLUE + "[ НАСТРОЙКИ ФИЛЬТРА ]" + RESET);
    System.out.println("""
        Выберите критерий для отбора записей:
          𝟙. Отфильтровать по типу операции (доход/расход)
          𝟚. Отфильтровать по категории операции
          𝟛. Отфильтровать по валюте операции
          𝟜. Вернуться в главное меню""");
    System.out.print("Введите номер пункта меню: ");
    int choice = Input.readIntLimited(1, 4);
    switch (choice) {
      case 1 -> Output.printTransactionFilteredByType(br, transactionList, currencyList);
      case 2 -> Output.printTransactionFilteredByCategory(br, transactionList, currencyList);
      case 3 -> Output.printTransactionFilteredByCurrency(br, transactionList, currencyList);
      case 4 -> System.out.println();
    }

  }

  /**
   * меню для выбора объекта редактирования или просмотра наявных валют и категорий
   *
   * @param br           BufferedReader, для считывания ввода пользователя
   * @param currencyList список доступных валют
   */
  public static void menuEditCategoryCurrency(BufferedReader br, List<Currency> currencyList) throws IOException {
    System.out.println();
    System.out.println(BLUE + "[ ВЫБОР ОБЪЕКТА ДЛЯ РЕДАКТИРОВАНИЯ ]" + RESET);
    System.out.println("""
        Что Вы хотите отредактировать:
          𝟙. Редактировать категорию
          𝟚. Редактировать валюту
          𝟛. Просмотреть наявные категории и валюты
          𝟜. Вернуться в главное меню""");
    System.out.print("Введите номер пункта меню: ");
    int choice = Input.readIntLimited(1, 4);
    switch (choice) {
      case 1 -> Input.editCategory(br);
      case 2 -> Input.editCurrency(br, currencyList);
      case 3 -> Output.showAllCategoryCurrency(br, currencyList);
      default -> System.out.println();
    }
  }

  /**
   * меню выбора начальной и конечной даты для отображения записей за выбранный период
   *
   * @param br              BufferedReader, для считывания ввода пользователя
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   */
  public static void menuPrintTransactionByDate(BufferedReader br, List<Transaction> transactionList,
                                                List<Currency> currencyList)
      throws IOException, InterruptedException, AWTException {
    System.out.println();
    System.out.println(BLUE + "[ ВЫБОР ПЕРИОДА ДЛЯ ОТОБРАЖЕНИЯ ]" + RESET);
    System.out.println();
    Date firstDate;
    Date lastDate;
    do {
      do {
        System.out.print("Введите начальную дату в формате [дд.ММ.гггг]: ");
        firstDate = Input.dateFromString(br.readLine() + "  00:00");
      } while (firstDate == null);
      do {
        System.out.print("Введите конечную дату в формате [дд.ММ.гггг]: ");
        lastDate = Input.dateFromString(br.readLine() + "  23:59");
      } while (lastDate == null);
      if (firstDate.after(lastDate)) {
        System.out.println(RED + "Начальная дата не может быть позже конечной!" + RESET);
      }
    } while (firstDate.after(lastDate));
    Output.printTransactionByDate(br, transactionList, currencyList, firstDate, lastDate);
  }

  /**
   * меню под списком всех записей
   *
   * @param br              BufferedReader, для считывания ввода пользователя
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   * @param isList10        служит для отслеживания места откуда было вызвано меню
   *                        и проброса этого параметра в showTransactionById
   */
  public static void menuAnderTransactionList(BufferedReader br, List<Transaction> transactionList,
                                              List<Currency> currencyList, boolean isList10)
      throws IOException, InterruptedException, AWTException {
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
      case 3 -> Menu.menuMain(transactionList, currencyList);
      case 4 -> System.out.println(" ");

    }
  }

  /**
   * меню под списком всех задач по окончанию вывода их по 10
   *
   * @param br              BufferedReader, для считывания ввода пользователя
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   * @param isList10        служит для отслеживания места откуда было вызвано меню
   *                        и проброса этого параметра в showTransactionById
   */
  public static void menuAfterTransactionListEnds(BufferedReader br, List<Transaction> transactionList,
                                                  List<Currency> currencyList, boolean isList10)
      throws IOException, InterruptedException, AWTException {
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
      case 3 -> Menu.menuMain(transactionList, currencyList);
    }
  }

  /**
   * меню под просмотром транзакции по Id
   *
   * @param br              BufferedReader, для считывания ввода пользователя
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   * @param index           индекс транзакции в transactionList
   * @param isList10        служит для отслеживания места откуда было вызвано меню
   */
  public static void menuAnderTransactionView(BufferedReader br, List<Transaction> transactionList,
                                              List<Currency> currencyList, int index, boolean isList10)
      throws IOException, InterruptedException, AWTException {
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
      case 1 -> Input.deleteTransaction(transactionList, index);
      case 2 -> Input.editTransaction(br, transactionList, currencyList, index);
      case 3 -> Menu.menuMain(transactionList, currencyList);
      case 4 -> System.out.print(" ");
    }
  }
}
