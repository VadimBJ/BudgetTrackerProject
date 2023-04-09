import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Input implements Finals {
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
    transactionType.getCategoryList().add(new Category(title, 0));
  }

  public static void transactionRead(BufferedReader br, List<Transaction> transactionList,
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

  }

  public static String dateToString(Date currentDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
    return dateFormat.format(currentDate);
  }

  public static Date dateFromString(String dateString) {
    Date currentDate = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
    try {
      currentDate = dateFormat.parse(dateString);
    } catch (Exception e) {
      System.out.println("Ошибка считывания даты");
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
