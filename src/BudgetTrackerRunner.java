import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class BudgetTrackerRunner implements Finals {
  public static void main(String[] args) throws IOException, InterruptedException, AWTException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Map<String, String> userData = new HashMap<>();
    List<Currency> currencyList = new ArrayList<>();
    List<Transaction> transactionList = new ArrayList<>();

    System.out.printf(LOGO, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW,
        BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, RESET);
    System.out.println("""
           
           Добро пожаловать в бюджет-трекер!
        Для начала работы Вам необходимо авторизоваться.
        Если у вас уже есть учетная запись, введите свой логин и пароль.
        Если Вы новый пользователь, пожалуйста, зарегистрируйтесь.
        Мы гарантируем безопасность ваших данных!""");

    Input.readLoginFile(userData);


//    System.out.println(userData);

//    userData.put("Vadim",User.makeHash("123456","Vadim"));
//    userData.put("aaa",User.makeHash("123","aaa"));


    Menu.menuLogin(transactionList, currencyList, userData);

    Menu.menuMain(transactionList, currencyList);

  }
}
