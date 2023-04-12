import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BudgetTrackerRunner implements Finals {
  public static void main(String[] args) throws IOException, InterruptedException, AWTException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    List<Currency> currencyList = new ArrayList<>();
    List<Transaction> transactionList = new ArrayList<>();


    System.out.printf(LOGO, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW,
        BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, YELLOW, BLUE, RESET);


    Menu.menuLogin(transactionList, currencyList);



//    Input.readFromFile(transactionList, currencyList);
    Collections.sort(transactionList);



    Menu.menuMain(br, transactionList, currencyList);


  }


}