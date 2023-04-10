import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BudgetTrackerRunner implements Finals {
  public static void main(String[] args) throws IOException, InterruptedException, AWTException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    List<Currency> currencyList = new ArrayList<>();
    List<Transaction> transactionList = new ArrayList<>();
/*
    //инициализация категорий
    TransactionType.INCOMING.getCategoryList().add(new Category("Зарплата"));
    TransactionType.INCOMING.getCategoryList().add(new Category("Премии/бонусы"));
    TransactionType.INCOMING.getCategoryList().add(new Category("Дивиденды"));
    TransactionType.INCOMING.getCategoryList().add(new Category("Инвестиции"));
    TransactionType.INCOMING.getCategoryList().add(new Category("Аренда недвижимости"));
    TransactionType.INCOMING.getCategoryList().add(new Category("Продажа"));


    TransactionType.OUTGOING.getCategoryList().add(new Category("Жилье и коммунальные услуги"));
    TransactionType.OUTGOING.getCategoryList().add(new Category("Транспорт"));
    TransactionType.OUTGOING.getCategoryList().add(new Category("Продукты"));
    TransactionType.OUTGOING.getCategoryList().add(new Category("Медицинские услуги и лекарства"));
    TransactionType.OUTGOING.getCategoryList().add(new Category("Одежда и обувь"));
    TransactionType.OUTGOING.getCategoryList().add(new Category("Покупка"));


    //инициализация валют
    currencyList.add(new Currency("US dollar", "USD", 0));
    currencyList.add(new Currency("EURO", "EUR", 0));
    currencyList.add(new Currency("Гривнi", "UAH", 0));

    //создание трансакций
    transactionList.add(new Transaction(
        "Оплата комуналки",
        "Комуналка за март",
        TransactionType.OUTGOING,
        TransactionType.OUTGOING.getCategoryList().get(0),
        currencyList.get(2),
        -800, new Date()));
    transactionList.add(new Transaction(
        "Зарплата",
        "Зарплата за март",
        TransactionType.INCOMING,
        TransactionType.INCOMING.getCategoryList().get(0),
        currencyList.get(2),
        18000, new Date()));
    transactionList.add(new Transaction(
        "Ремонт машины",
        "Замена опорного подшипника, замена масла и фильтров, ремонт подвески и тормозной системы",
        TransactionType.OUTGOING,
        TransactionType.OUTGOING.getCategoryList().get(1),
        currencyList.get(1),
        -250, new Date()));
    transactionList.add(new Transaction(
        "Продажа машины",
        "",
        TransactionType.INCOMING,
        TransactionType.INCOMING.getCategoryList().get(5),
        currencyList.get(1),
        15000, new Date()));


 */

//    System.out.printf(LOGO, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW,
//        BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, RESET);

    Input.readFromFile(transactionList, currencyList);
    Collections.sort(transactionList);
    Menu.menuMain(br, transactionList, currencyList);


    //печатаем список трансакций
//    Output.printList(transactionList);
//    printList(categoryList);
//    Output.printList(currencyList);


  }


}