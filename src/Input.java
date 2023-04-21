import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Input implements Finals {
  public static User user;

  public static User getUser() {
    return user;
  }

  /**
   * Инициализирует начальное состояние:
   * Создает по одной категории в расходах и доходах, а так же создает одну валюту
   * для того чтобы пользователь мог сразу приступить к созданию записей
   *
   * @param currencyList список доступных валют
   */
  public static void initializeData(List<Currency> currencyList) {
    //инициализация категорий
    TransactionType.INCOMING.getCategoryList().clear();
    TransactionType.OUTGOING.getCategoryList().clear();
    TransactionType.INCOMING.getCategoryList().add(new Category("Доходы"));
    TransactionType.OUTGOING.getCategoryList().add(new Category("Расходы"));

    //инициализация валют
    currencyList.add(new Currency("US dollar", "USD", 0));
  }

  /**
   * Считывает данные для авторизации уже существующего пользователя
   *
   * @param userData хранит в себе пары login:passwordHash
   * @return возвращает true если авторизация прошла успешно
   */
  public static boolean userLoginRead(Map<String, String> userData) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String login;
    do {
      System.out.print("Введите Ваш логин или e-mail: ");
      login = br.readLine();
      if (login.trim().isEmpty()) {
        System.out.println(RED + "Это поле не может быть пустым!" + RESET);
      }
    } while (login.trim().isEmpty());
    if (!userData.containsKey(login)) {
      System.out.println(RED + "Такой пользователь не найден!" + RESET);
      System.out.println();
      System.out.println("Возможно Вам нужен пункт регистрации нового пользователя");
      return false;
    }
    String expectPasswordHash = userData.get(login);
    System.out.print("Введите Ваш пароль: ");
    String password = br.readLine();
    if (!expectPasswordHash.equals(User.makeHash(password, login))) {
      System.out.println(RED + "Введен неправильный пароль!" + RESET);
      System.out.println();
      System.out.println("Возможно Вам нужен пункт регистрации нового пользователя");
      return false;
    }
    user = new User(login, password);
    return true;
  }

  /**
   * Считывает данные для авторизации нового пользователя
   *
   * @param userData хранит в себе пары login:passwordHash
   * @return возвращает true если авторизация прошла успешно
   */
  public static boolean userNewRead(Map<String, String> userData) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String login;
    do {
      System.out.print("Введите Ваш логин или e-mail: ");
      login = br.readLine();
      if (login.trim().isEmpty()) {
        System.out.println(RED + "Это поле не может быть пустым!" + RESET);
      }
    } while (login.trim().isEmpty());
    if (userData.containsKey(login)) {
      System.out.println(RED + "Такой пользователь уже существует!" + RESET);
      System.out.println();
      System.out.println("Возможно Вам нужен пункт для входа в свою учетную запись");
      return false;
    }
    System.out.print("Введите Ваш пароль: ");
    String password = br.readLine();
    user = new User(login, password);
    userData.put(user.getLogin(), user.getPasswordHash());
    Output.saveLoginFile(userData);
    return true;
  }

  /**
   * Считывает зашифрованный файл содержащий данные для авториции пользователей
   *
   * @param userData хранит в себе пары login:passwordHash
   */
  public static void readLoginFile(Map<String, String> userData) throws IOException {
    File file = new File("res/dd2495l.txt");
    BufferedReader fr = new BufferedReader(new FileReader(file));
    for (String line = fr.readLine(); line != null; line = fr.readLine()) {
      line = Encryption.decryptStrCesar(line, 11);
      String[] values = line.split(";", 2);
      userData.put(values[0], values[1]);
    }
  }

  /**
   * считывает зашифрованный файл данных пользователя (в качестве имени файла используется часть хеша пароля пользователя)
   * расшифровывает данные и сохраняет их в соответствующие списки
   *
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   */
  public static void readFromEncryptFile(List<Transaction> transactionList,
                                         List<Currency> currencyList) throws IOException, InterruptedException {
    System.out.print("FILE DECRYPTION |\u2009");
    String filename = user.getPasswordHash().substring(2, 10);
    File file = new File("res/" + filename + ".txt");
    if (!file.exists()) {
      if (file.createNewFile()) System.out.println("... Файл создан ...");
      return;
    }
    BufferedReader fr = new BufferedReader(new FileReader(file));

    //считываем категории по типам операции
    for (int j = 0; j < 2; j++) {
      System.out.print("▌");
      TimeUnit.MILLISECONDS.sleep(10);
      String line = Encryption.decryptStrCesar(fr.readLine(), 17);
      String[] value = line.split(";", -1);
      TransactionType type = TransactionType.valueOf(value[0]);
      int num = Integer.parseInt(value[1]);
      for (int i = 2; i < num + 2; i++) {
        type.getCategoryList().add(new Category(value[i]));
      }
    }

    //считываем валюты
    String line = Encryption.decryptStrCesar(fr.readLine(), 17);
    String[] value = line.split(";", -1);
    int num = Integer.parseInt(value[0]);
    for (int i = 0; i < num * 2; i += 2) {
      System.out.print("▌");
      TimeUnit.MILLISECONDS.sleep(10);
      String title = value[1 + i];
      String acronym = value[2 + i];
      currencyList.add(new Currency(title, acronym, 0));
    }

    //считываем все записи
    num = Integer.parseInt(Encryption.decryptStrCesar(fr.readLine(), 17));
    for (int i = 0; i < num; i++) {
      System.out.print("▌");
      TimeUnit.MILLISECONDS.sleep(5);
      line = Encryption.decryptStrCesar(fr.readLine(), 17);
      value = line.split(";", -1);
      String title = value[0];
      String description = value[1];
      TransactionType type = TransactionType.valueOf(value[2]);

      int categoryIndex = 0;
      for (Category category : type.getCategoryList()) {
        if (category.getTitle().equals(value[3])) {
          break;
        } else {
          ++categoryIndex;
        }
      }

      int currencyIndex = 0;
      for (Currency currency : currencyList) {
        if (currency.getAcronym().equals(value[4])) {
          break;
        } else {
          ++currencyIndex;
        }
      }

      double amount = Double.parseDouble(value[5]);
      Date date = dateFromString(value[6]);
      transactionList.add(new Transaction(title, description, type, type.getCategoryList().get(categoryIndex),
          currencyList.get(currencyIndex), amount, date));
    }
    Collections.sort(transactionList);
  }

  /**
   * служит для выбора типа записи РАСХОД/ДОХОД
   *
   * @return возвращает выбранный тип записи (enum)
   */
  public static TransactionType takeType() throws IOException {
    int lastOfNum = 1;
    System.out.println("Выберите тип категории:");
    for (TransactionType values : TransactionType.values()) {
      System.out.println("  " + values.getId() + ". " + values.getTitle());
      lastOfNum = values.getId();
    }
    System.out.print("Введите номер пункта меню: ");
    int choice = readIntLimited(1, lastOfNum);
    TransactionType priority = null;
    for (TransactionType values : TransactionType.values()) {
      if (values.getId() == choice) {
        priority = values;
      }
    }
    return priority;
  }

  /**
   * создает новую категорию, записыает ее в выбранный тип записи
   * (списки категорий храняться отдельно в enum INCOMING/OUTGOING)
   *
   * @param br BufferedReader, для считывания ввода пользователя
   */
  public static void addCategory(BufferedReader br) throws IOException {
    System.out.println();
    System.out.println(BLUE + "[ ДОБАВЛЕНИЕ НОВОЙ КАТЕГОРИИ ]" + RESET);
    TransactionType transactionType = takeType();
    String title;
    do {
      System.out.print("Введите название категории: ");
      title = br.readLine();
      if (title.trim().isEmpty()) {
        System.out.println(RED + "Поле 'Название' не может быть пустым!" + RESET);
      }
    } while (title.trim().isEmpty());
    transactionType.getCategoryList().add(new Category(title));
  }

  /**
   * служит для выбора категории пользователем
   *
   * @param transactionType выбранный тип записи (enum)
   * @return возвращает ссылку на выбранную категорию
   */
  public static Category takeCategory(TransactionType transactionType) throws IOException {
    List<Category> categoryList = transactionType.getCategoryList();
    System.out.println("Выберите категорию:");
    for (int i = 0; i < categoryList.size(); i++) {
      System.out.println("  " + (i + 1) + ". " + categoryList.get(i).getTitle());
    }
    System.out.print("Введите номер пункта меню: ");
    int choice = readIntLimited(1, categoryList.size()) - 1;
    return categoryList.get(choice);
  }

  /**
   * редактирует выбранную категорию
   *
   * @param br BufferedReader, для считывания ввода пользователя
   */
  public static void editCategory(BufferedReader br) throws IOException {
    System.out.println();
    TransactionType transactionType = takeType();
    Category category = takeCategory(transactionType);
    String title;
    do {
      System.out.print(BLUE + "Введите новое название для категории: " + RESET);
      title = br.readLine();
      if (title.trim().isEmpty()) {
        System.out.println(RED + "Поле 'Название' не может быть пустым!" + RESET);
      }
    } while (title.trim().isEmpty());

    category.setTitle(title);
  }

  /**
   * создает новую валюту и добавляет ее в список доступных валют
   *
   * @param br           BufferedReader, для считывания ввода пользователя
   * @param currencyList список доступных валют
   */
  public static void addCurrency(BufferedReader br, List<Currency> currencyList) throws IOException {
    System.out.println();
    System.out.println(BLUE + "[ ДОБАВЛЕНИЕ НОВОЙ ВАЛЮТЫ ]" + RESET);
    String title;
    do {
      System.out.print("Введите название для валюты: ");
      title = br.readLine();
      if (title.trim().isEmpty()) {
        System.out.println(RED + "Поле 'Название' не может быть пустым!" + RESET);
      }
    } while (title.trim().isEmpty());
    String acronym;
    do {
      System.out.print("Введите акроним (абревиатуру) для этой валюты: ");
      acronym = br.readLine();
      if (acronym.trim().isEmpty()) {
        System.out.println(RED + "Это поле не может быть пустым!" + RESET);
      }
    } while (acronym.trim().isEmpty());
    System.out.print(BLUE + "Введите стартовую сумму для этой валюты: " + RESET);
    double total = readDoubleLimited(0, Double.MAX_VALUE);

    currencyList.add(new Currency(title, acronym, total));
  }

  /**
   * служит для выбора валюты пользователем
   *
   * @param currencyList список доступных валют
   * @return возвращает ссылку на выбранную валюту
   */
  public static Currency takeCurrency(List<Currency> currencyList) throws IOException {
    System.out.println("Выберите валюту:");
    for (int i = 0; i < currencyList.size(); i++) {
      System.out.println("  " + (i + 1) + ". " + currencyList.get(i).getAcronym());
    }
    System.out.print("Введите номер пункта меню: ");
    int choice = readIntLimited(1, currencyList.size()) - 1;
    return currencyList.get(choice);
  }

  /**
   * редактирует выбранную валюту
   *
   * @param br           BufferedReader, для считывания ввода пользователя
   * @param currencyList список доступных валют
   */
  public static void editCurrency(BufferedReader br, List<Currency> currencyList) throws IOException {
    System.out.println();
    Currency currency = takeCurrency(currencyList);
    String title;
    do {
      System.out.print("Введите новое название для валюты: ");
      title = br.readLine();
      if (title.trim().isEmpty()) {
        System.out.println(RED + "Поле 'Название' не может быть пустым!" + RESET);
      }
    } while (title.trim().isEmpty());
    String acronym;
    do {
      System.out.print("Введите акроним (абревиатуру) для этой валюты: ");
      acronym = br.readLine();
      if (acronym.trim().isEmpty()) {
        System.out.println(RED + "Это поле не может быть пустым!" + RESET);
      }
    } while (acronym.trim().isEmpty());
    currency.setTitle(title);
    currency.setAcronym(acronym);
  }

  /**
   * создает новую запись о транзакции и добавляет ее в список созданных записей
   *
   * @param br              BufferedReader, для считывания ввода пользователя
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   */
  public static void addTransaction(BufferedReader br, List<Transaction> transactionList,
                                    List<Currency> currencyList) throws IOException {
    System.out.println();
    System.out.println(BLUE + "[ СОЗДАНИЕ НОВОЙ ЗАПИСИ ]" + RESET);
    String title;
    do {
      System.out.print("Введите название для записи: ");
      title = br.readLine();
      if (title.trim().isEmpty()) {
        System.out.println(RED + "Поле 'Название' не может быть пустым!" + RESET);
      }
    } while (title.trim().isEmpty());
    System.out.println(CYAN + "Поле 'Описание' может быть пустым" + RESET);
    System.out.print("Введите детальное описание записи: ");
    String description = br.readLine().trim();
    TransactionType transactionType = takeType();
    Category category = takeCategory(transactionType);
    Currency currency = takeCurrency(currencyList);
    System.out.print(BLUE + "Введите сумму операции: " + RESET);
    double amount = Math.abs(readDoubleLimited(-Double.MAX_VALUE, Double.MAX_VALUE));
    amount = transactionType == TransactionType.INCOMING ? amount : -amount;
    Date date = new Date();
    transactionList.add(new Transaction(title, description, transactionType, category, currency, amount, date));
    System.out.println(GREEN + "... Запись добавлена ..." + RESET);
  }

  /**
   * редактируем выбранную запись о транзакции
   *
   * @param br              BufferedReader, для считывания ввода пользователя
   * @param transactionList список созданных записей
   * @param currencyList    список доступных валют
   * @param index           индекс записи в transactionList
   */
  public static void editTransaction(BufferedReader br, List<Transaction> transactionList,
                                     List<Currency> currencyList, int index) throws IOException {
    String num;
    if (String.valueOf(index).length() < 3) {
      num = "000" + (transactionList.size() - index);
      num = num.substring(num.length() - 3);
    } else num = String.valueOf(transactionList.size() - index);
    System.out.println();
    System.out.println(BLUE + "[ РЕДАКТИРОВАНИЕ ЗАПИСИ " + num + " ]" + RESET);
    System.out.println();
    String title;
    do {
      System.out.print("Введите название для записи: ");
      title = br.readLine();
      if (title.trim().isEmpty()) {
        System.out.println(RED + "Поле 'Название' не может быть пустым!" + RESET);
      }
    } while (title.trim().isEmpty());
    System.out.println(CYAN + "Поле 'Описание' может быть пустым" + RESET);
    System.out.print("Введите детальное описание записи: ");
    String description = br.readLine().trim();
    TransactionType transactionType = takeType();
    Category category = takeCategory(transactionType);
    Currency currency = takeCurrency(currencyList);
    System.out.print(BLUE + "Введите сумму операции: " + RESET);
    double amount = Math.abs(readDoubleLimited(-Double.MAX_VALUE, Double.MAX_VALUE));
    amount = transactionType == TransactionType.INCOMING ? amount : -amount;
    Transaction transaction = transactionList.get(index);

    transaction.getCurrency().setTotal(transaction.getCurrency().getTotal() - transaction.getAmount());
    currency.setTotal(currency.getTotal() + amount);

    transaction.setTitle(title);
    transaction.setDescription(description);
    transaction.setType(transactionType);
    transaction.setCategory(category);
    transaction.setCurrency(currency);
    transaction.setAmount(amount);

    System.out.println(GREEN + "... Запись отредактирована ..." + RESET);
  }

  /**
   * удаление запись о транзакции по индексу
   *
   * @param transactionList список созданных записей
   * @param index           индекс записи в transactionList
   */
  public static void deleteTransaction(List<Transaction> transactionList, int index) {
    double amount = transactionList.get(index).getAmount();
    double total = transactionList.get(index).getCurrency().getTotal();
    transactionList.get(index).getCurrency().setTotal(total - amount);
    transactionList.remove(index);
  }

  /**
   * переводит переменную из Date в String
   *
   * @param currentDate дата для преобразования
   * @param format      шаблон для отображения даты
   * @return возвращает строку содержащую дату в заданном шаблоне
   */
  public static String dateToString(Date currentDate, String format) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.format(currentDate);
  }

  /**
   * переводит строку в формат Date
   *
   * @param dateString строка с датой
   * @return возвращает дату с типом Date или null если была ошибка
   */
  public static Date dateFromString(String dateString) {
    Date currentDate = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
    try {
      currentDate = dateFormat.parse(dateString);
    } catch (Exception e) {
      System.out.println("Ошибка считывания даты: " + dateString);
    }
    return currentDate;
  }

  /**
   * считывает целое число с клавиатуры в заданных рамках
   *
   * @param min минимальное допустимое значение
   * @param max максимальное допустимое значение
   * @return возвращает целое число в заданых рамках
   */
  public static int readIntLimited(int min, int max) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int num = -1;
    do {
      try {
        num = Integer.parseInt(br.readLine());
      } catch (NumberFormatException e) {
        System.out.println("\u001B[31mВводите только цифры!\u001B[0m");
      }
      if (!(num >= min && num <= max)) {
        System.out.printf("Введите число от %d до %d: ", min, max);
      }
    } while (!(num >= min && num <= max));
    return num;
  }

  /**
   * считывает дробное число с клавиатуры в заданных рамках
   *
   * @param min минимальное допустимое значение
   * @param max максимальное допустимое значение
   * @return возвращает дробное число в заданых рамках
   */
  public static double readDoubleLimited(double min, double max) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    boolean isWrong = true;
    double num = 0;
    do {
      try {
        num = Double.parseDouble(br.readLine());
        isWrong = false;
      } catch (NumberFormatException e) {
        System.out.println("\u001B[31mВводите только цифры!\u001B[0m");
        System.out.print("Попробуйте еще разок: ");
      }
      if (!(num >= min && num <= max)) {
        isWrong = true;
        if (min == 0 && max == Double.MAX_VALUE) {
          System.out.println(RED + "Вводите только положительные значение!" + RESET);
          System.out.print("Попробуйте еще разок: ");
        } else {
          System.out.printf("Введите число от %f до %f: ", min, max);
        }
      }
    } while (isWrong);
    return num;
  }
}
