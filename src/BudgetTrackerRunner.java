import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class BudgetTrackerRunner implements Finals {
  public static void main(String[] args) throws IOException, InterruptedException, AWTException {
    Map<String, String> userData = new HashMap<>();
    List<Currency> currencyList = new ArrayList<>();
    List<Transaction> transactionList = new ArrayList<>();

    System.out.printf(LOGO, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW,
        BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE,YELLOW,  RESET);
    System.out.println("""
           
           Добро пожаловать в бюджет-трекер!
        Для начала работы Вам необходимо авторизоваться.
        Если у вас уже есть учетная запись, введите свой логин и пароль.
        Если Вы новый пользователь, пожалуйста, зарегистрируйтесь.
        Мы гарантируем безопасность ваших данных!""");

    Input.readLoginFile(userData);

    Menu.menuLogin(transactionList, currencyList, userData);

    Menu.menuMain(transactionList, currencyList);
  }
}
