import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Input {

  public static void transactionRead(BufferedReader br, List<Transaction> transactionList,
                                     List<Category> categoryList, List<Currency> currencyList) throws IOException {
    System.out.println(takeCategory(categoryList));
    System.out.println(takeCurrency(currencyList));
    System.out.println(takeType());



  }

  public static TransactionType takeType() throws IOException {
    int lastOfNum = 1;
    System.out.println("Выберите тип задачи:");
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

  public static Category takeCategory(List<Category> categoryList) throws IOException {
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
}
