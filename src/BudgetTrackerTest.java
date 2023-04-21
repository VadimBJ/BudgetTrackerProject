import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class BudgetTrackerTest {
  List<Currency> currencyList = new ArrayList<>();
  List<Transaction> transactionList = new ArrayList<>();
  TransactionType transactionTypeIncoming = TransactionType.INCOMING;
  TransactionType transactionTypeOutgoing = TransactionType.OUTGOING;

  @Test
  @Order(1)
  @DisplayName("Тестируем создание категорий")
  public void addCategoryTest() {
    transactionTypeIncoming.getCategoryList().add(new Category("Доходы"));
    transactionTypeOutgoing.getCategoryList().add(new Category("Расходы"));

    assertEquals("Доходы", transactionTypeIncoming.getCategoryList().get(0).getTitle());
    assertEquals("Расходы", transactionTypeOutgoing.getCategoryList().get(0).getTitle());
  }

  @Test
  @Order(2)
  @DisplayName("Тестируем создание валют")
  public void addCurrencyTest() {
    currencyList.add(new Currency("US dollar", "USD", 1000));

    assertEquals("US dollar", currencyList.get(0).getTitle());
    assertEquals("USD", currencyList.get(0).getAcronym());
    assertEquals(1000, currencyList.get(0).getTotal());
  }

  @Test
  @Order(3)
  @DisplayName("Тестируем создание записи")
  public void addTransactionTest() {
    Category category = transactionTypeIncoming.getCategoryList().get(0);
    currencyList.add(new Currency("US dollar", "USD", 1000));
    Currency currency = currencyList.get(0);
    transactionList.add(
        new Transaction("Доход", "описание", transactionTypeIncoming, category, currency, 10, new Date()));

    assertEquals("Доход", transactionList.get(0).getTitle());
    assertEquals("описание", transactionList.get(0).getDescription());
    assertEquals(transactionTypeIncoming, transactionList.get(0).getType());
    assertEquals(category, transactionList.get(0).getCategory());
    assertEquals(currency, transactionList.get(0).getCurrency());
    assertEquals(10, transactionList.get(0).getAmount());
  }

  @Test
  @Order(4)
  @DisplayName("Тестируем перевод Date в String")
  public void dateToStringTest() throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
    Date currentDate = dateFormat.parse("20.04.2023  17:25");

    String answerDate = Input.dateToString(currentDate, "dd.MM.yyyy  HH:mm");

    assertEquals("20.04.2023  17:25", answerDate);
  }

  @Test
  @Order(5)
  @DisplayName("Тестируем перевод String в Date")
  public void stringToDateTest() throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
    Date currentDate = dateFormat.parse("20.04.2023  17:25");

    Date answerDate = Input.dateFromString("20.04.2023  17:25");

    assertEquals(currentDate, answerDate);
  }

  @Test
  @Order(6)
  @DisplayName("Тестируем создание хеш пароля")
  public void passwordHashTest() {
    String login = "Vadim";
    String password = "123456";
    String passwordHash = "f5d10ef2b67e11bf74771ed7a7722c53b89ea7630d2d28cd2a4506b013275daa";

    assertEquals(passwordHash, User.makeHash(password, login));
  }
}
