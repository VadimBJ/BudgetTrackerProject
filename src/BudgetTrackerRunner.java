import java.util.ArrayList;
import java.util.List;

public class BudgetTrackerRunner {
  public static void main(String[] args) {
    //инициализация категорий
    List<Category> categoryList = new ArrayList<>();
    categoryList.add(new Category("Коммунальные платежи", 0));
    categoryList.add(new Category("Машина", 0));
    categoryList.add(new Category("Зарплата", 0));

    //инициализация валют
    List<Currency> currencyList = new ArrayList<>();
    currencyList.add(new Currency("US dollar's", "USD", 0));
    currencyList.add(new Currency("EURO's", "EUR", 0));
    currencyList.add(new Currency("Гривнi", "UAH", 0));

    //создание трансакций
    List<Transaction> transactionList = new ArrayList<>();
    transactionList.add(new Transaction(
        "Оплата комуналки",
        "Комуналка за март",
        categoryList.get(0),
        TransactionType.OUTGOING,
        currencyList.get(2),
        -800));
    transactionList.add(new Transaction(
        "Зарплата",
        "Зарплата за март",
        categoryList.get(2),
        TransactionType.INCOMING,
        currencyList.get(2),
        18000));
    transactionList.add(new Transaction(
        "Ремонт машины",
        "Замена опорного подшипника",
        categoryList.get(1),
        TransactionType.OUTGOING,
        currencyList.get(1),
        -250));
    transactionList.add(new Transaction(
        "Продажа машины",
        "",
        categoryList.get(1),
        TransactionType.INCOMING,
        currencyList.get(1),
        15000));

    //печатаем список трансакций
    printList(transactionList);
    printList(categoryList);
    printList(currencyList);

  }

  public static void printList(List<?> listToPrint) {
    for (Object obj : listToPrint) {
      System.out.println(obj);
    }
  }


}