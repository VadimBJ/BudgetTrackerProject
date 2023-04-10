import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Input implements Finals {

  public static void readFromFile(List<Transaction> transactionList,
                                  List<Currency> currencyList) throws IOException {
    File file = new File("res/Test.csv");
    BufferedReader fr = new BufferedReader(new FileReader(file));

    //считываем категории по типам операции
    for (int j = 0; j < 2; j++) {
      String line = fr.readLine();
      String[] value = line.split(";", -1);
      TransactionType type = TransactionType.valueOf(value[0]);
      int num = Integer.parseInt(value[1]);
      for (int i = 2; i < num + 2; i++) {
        type.getCategoryList().add(new Category(value[i]));
      }
    }

    //считываем валюты
    String line = fr.readLine();
    String[] value = line.split(";", -1);
    int num = Integer.parseInt(value[0]);
    for (int i = 0; i < num * 2; i += 2) {
      String title = value[1 + i];
      String acronym = value[2 + i];
//      double total = Double.parseDouble(value[3 + i]);
      currencyList.add(new Currency(title, acronym, 0));
    }

    //считываем все записи
    num = Integer.parseInt(fr.readLine());
    for (int i = 0; i < num; i++) {
      line = fr.readLine();
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
  }

  public static void addCategory(BufferedReader br) throws IOException {
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

  public static void addTransaction(BufferedReader br, List<Transaction> transactionList,
                                    List<Currency> currencyList) throws IOException {

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
    System.out.print("Введите детальное описание задачи: ");
    String description = br.readLine().trim();

    TransactionType transactionType = takeType();

    Category category = takeCategory(transactionType);

    Currency currency = takeCurrency(currencyList);

    System.out.print("Введите сумму операции: ");
    double amount = Math.abs(readDoubleLimited(-Double.MAX_VALUE, Double.MAX_VALUE));
    amount = transactionType == TransactionType.INCOMING ? amount : -amount;


    Date date = new Date();

    transactionList.add(new Transaction(title, description, transactionType, category, currency, amount, date));
    System.out.println(GREEN+"... Запись добавлена ..."+RESET);
    Collections.sort(transactionList);

  }

  public static String dateToString(Date currentDate, String format) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.format(currentDate);
  }

  public static Date dateFromString(String dateString) {
    Date currentDate = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
    try {
      currentDate = dateFormat.parse(dateString);
    } catch (Exception e) {
      System.out.println("Ошибка считывания даты: "+dateString);
    }
    return currentDate;
  }

  public static TransactionType takeType() throws IOException {
    int lastOfNum = 1;
    System.out.println("Выберите тип операции:");
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

  public static Currency takeCurrency(List<Currency> currencyList) throws IOException {
    System.out.println("Выберите валюту:");
    for (int i = 0; i < currencyList.size(); i++) {
      System.out.println("  " + (i + 1) + ". " + currencyList.get(i).getAcronym());
    }
    System.out.print("Введите номер пункта меню: ");
    int choice = readIntLimited(1, currencyList.size()) - 1;
    return currencyList.get(choice);
  }

  public static int readIntLimited(int min, int max) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int num = 0;
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

  public static double readDoubleLimited(double min, double max) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    double num = 0;
    do {
      try {
        num = Double.parseDouble(br.readLine());
      } catch (NumberFormatException e) {
        System.out.println("\u001B[31mВводите только цифры!\u001B[0m");
      }
      if (!(num >= min && num <= max)) {
        System.out.printf("Введите число от %f до %f: ", min, max);
      }
    } while (!(num >= min && num <= max));
    return num;
  }
}
