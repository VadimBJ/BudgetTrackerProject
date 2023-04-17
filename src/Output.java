import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Output implements Finals {

  public static void saveLoginFile(Map<String, String> userData) throws IOException {
    File file = new File("res/dd2495l.txt");
    if (!file.exists()) {
      System.out.print(RED + "... файл не найден ..." + RESET);
      if (file.createNewFile()) {
        System.out.print(YELLOW + "... файл создан ..." + RESET);
      } else {
        System.out.print(RED + "... файл не создан ..." + RESET);
        return;
      }
    }
    FileWriter fileWriter = new FileWriter(file);
    for (Map.Entry<String, String> loginPass : userData.entrySet()) {
//      String line = loginPass.getKey()+";"+loginPass.getValue();
      String line = Encryption.encryptStrCesar(loginPass.getKey() + ";" + loginPass.getValue(), 11);
      fileWriter.write(line + "\n");
    }
    fileWriter.close();
  }

  public static void writeToEncryptFile(List<Transaction> transactionList,
                                        List<Currency> currencyList) throws IOException, InterruptedException {
    String filename = Input.getUser().getPasswordHash().substring(2, 10);
    File file = new File("res/" + filename + ".txt");
    if (!file.exists()) {
      System.out.print(RED + "... файл не найден ..." + RESET);
      if (file.createNewFile()) {
        System.out.print(YELLOW + "... файл создан ..." + RESET);
      } else {
        System.out.print(RED + "... файл не создан ..." + RESET);
        return;
      }
    } else {
      System.out.print(YELLOW + "... файл найден ..." + RESET);
    }
    FileWriter fileWriter = new FileWriter(file);
    System.out.println();
    System.out.print("FILE ENCRYPTION ");

    //выгружаем категории по типам операции
    String type = TransactionType.INCOMING.name();
    int categoryCount = TransactionType.INCOMING.getCategoryList().size();
    StringBuilder resultStr = new StringBuilder();
    resultStr.append(type).append(";").append(categoryCount).append(";");
    for (Category category : TransactionType.INCOMING.getCategoryList()) {
      System.out.print("▌");
      TimeUnit.MILLISECONDS.sleep(10);
      resultStr.append(category.getTitle()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(Encryption.encryptStrCesar(resultStr.toString(), 17) + "\n");

    type = TransactionType.OUTGOING.name();
    categoryCount = TransactionType.OUTGOING.getCategoryList().size();
    resultStr = new StringBuilder();
    resultStr.append(type).append(";").append(categoryCount).append(";");
    for (Category category : TransactionType.OUTGOING.getCategoryList()) {
      System.out.print("▌");
      TimeUnit.MILLISECONDS.sleep(10);
      resultStr.append(category.getTitle()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(Encryption.encryptStrCesar(resultStr.toString(), 17) + "\n");

    //выгружаем валюты
    resultStr = new StringBuilder();
    resultStr.append(currencyList.size()).append(";");
    for (Currency currency : currencyList) {
      System.out.print("▌");
      TimeUnit.MILLISECONDS.sleep(10);
      resultStr.append(currency.getTitle()).append(";");
      resultStr.append(currency.getAcronym()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(Encryption.encryptStrCesar(resultStr.toString(), 17) + "\n");

    //выгружаем все записи
    fileWriter.write(Encryption.encryptStrCesar(String.valueOf(transactionList.size()), 17) + "\n");
    for (Transaction transaction : transactionList) {
      System.out.print("▌");
      TimeUnit.MILLISECONDS.sleep(5);
      resultStr = new StringBuilder();
      resultStr.append(transaction.getTitle()).append(";");
      resultStr.append(transaction.getDescription()).append(";");
      resultStr.append(transaction.getType()).append(";");
      resultStr.append(transaction.getCategory().getTitle()).append(";");
      resultStr.append(transaction.getCurrency().getAcronym()).append(";");
      resultStr.append(transaction.getAmount()).append(";");
      resultStr.append(Input.dateToString(transaction.getDate(), "dd.MM.yyyy  HH:mm"));
      fileWriter.write(Encryption.encryptStrCesar(resultStr.toString(), 17) + "\n");
    }

    fileWriter.close();
    System.out.println();
    System.out.println(GREEN + "... Файл сохранен ..." + RESET);
  }

  public static void printList(List<?> listToPrint) {
    for (Object obj : listToPrint) {
      System.out.println(obj);
    }
  }

  public static void printCurrencyTotal(List<Currency> currencyList) {
    System.out.println("      ╭" + "─".repeat(37) + "[\u001B[34m Общая сумма по валютам \u001B[0m]" + "─".repeat(38) + "╮");
    printList(currencyList);
    System.out.println("      ╰" + "─".repeat(101) + "╯");
  }

  public static void printTransactionAll(BufferedReader br, List<Transaction> transactionList,
                                         List<Currency> currencyList) throws AWTException, InterruptedException, IOException {
    clearScreen();
    if (currencyList.size() == 0 || transactionList.size() == 0) {
      System.out.println(RED + "⛔ Перед просмотром Вам необходимо создать хотя бы одну запись!" + RESET);
      System.out.println();
      System.out.println(" === Нажмите ENTER для возврата в главное меню === ");
      String wait = br.readLine();
      return;
    }
    printCurrencyTotal(currencyList);
    String title = String.format("[%s Показано записей: %d    Период: с %s по %s %s]", YELLOW, transactionList.size(),
        Input.dateToString(transactionList.get(0).getDate(), "dd.MM.yyyy"),
        Input.dateToString(transactionList.get(transactionList.size() - 1).getDate(), "dd.MM.yyyy"), RESET);
    int left = 101 / 2 - title.length() / 2;
    int right = 101 - left - title.length();
    System.out.println("      ╭" + "─".repeat(left + 5) + title + "─".repeat(right + 4) + "╮");

    for (int i = transactionList.size() - 1; i >= 0; --i) {
      int ind = transactionList.size() - i - 1;
      System.out.println(transactionList.get(i).printString(ind + 1));
    }
    System.out.println("      ╰" + "─".repeat(101) + "╯");

    Menu.menuAfterTransactionListEnds(br, transactionList, currencyList, false);
  }

  public static void printTransactionBy10(List<Transaction> transactionList, List<Currency> currencyList) throws AWTException, InterruptedException, IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    if (currencyList.size() == 0 || transactionList.size() == 0) {
      clearScreen();
      System.out.println(RED + "⛔ Перед просмотром Вам необходимо создать хотя бы одну запись!" + RESET);
      System.out.println();
      System.out.println(" === Нажмите ENTER для возврата в главное меню === ");
      String wait = br.readLine();
      return;
    }
    int end = transactionList.size();
    for (int j = 0; j <= end / 10; j++) {
      clearScreen();
      printCurrencyTotal(currencyList);
      int current = (j * 10) + 10;
      if (current > end) {
        current = end;
      }
      String title = String.format("[%s Показано записей: %d из %d    Период: с %s по %s %s]", YELLOW, current, end,
          Input.dateToString(transactionList.get(0).getDate(), "dd.MM.yyyy"),
          Input.dateToString(transactionList.get(end - 1).getDate(), "dd.MM.yyyy"), RESET);
      int left = 101 / 2 - title.length() / 2;
      int right = 101 - left - title.length();
      System.out.println("      ╭" + "─".repeat(left + 5) + title + "─".repeat(right + 4) + "╮");
      for (int i = j * 10; i < current; i++) {
        int ind = transactionList.size() - i - 1;
        System.out.println(transactionList.get(ind).printString(i + 1));
      }
      String answer;
      if (current != end) {
        System.out.printf(
            "      ╘%s Введите любой символ для входа в %sМЕНЮ%s ▼            %sДля просмотра следующей страницы нажмите %sENTER%s ▶ ╛%n",
            YELLOW, PURPLE, RESET, YELLOW, PURPLE, RESET);
        System.out.print("➤ ");
        answer = br.readLine().trim();
      } else {
        answer = "";
        System.out.println("      ╰" + "─".repeat(101) + "╯");
        Menu.menuAfterTransactionListEnds(br, transactionList, currencyList, true);
        end = transactionList.size();
      }
      if (!answer.isEmpty()) {
        Menu.menuAnderTransactionList(br, transactionList, currencyList, true);
        --j;
        end = transactionList.size();
      }
    }
  }


  public static void printTransactionByDate(BufferedReader br, List<Transaction> transactionList,
                                            List<Currency> currencyList,
                                            Date firstDate, Date lastDate) throws AWTException, InterruptedException, IOException {
    clearScreen();
    if (currencyList.size() == 0 || transactionList.size() == 0) {
      System.out.println(RED + "⛔ Перед просмотром Вам необходимо создать хотя бы одну запись!" + RESET);
      System.out.println();
      System.out.println(" === Нажмите ENTER для возврата в главное меню === ");
      String wait = br.readLine();
      return;
    }

    printCurrencyTotal(currencyList);
    String title = String.format("[%s Показаны записи за выбранный период: с %s по %s %s]", YELLOW,
        Input.dateToString(firstDate, "dd.MM.yyyy"), Input.dateToString(lastDate, "dd.MM.yyyy"), RESET);
    int left = 101 / 2 - title.length() / 2;
    int right = 101 - left - title.length();
    System.out.println("      ╭" + "─".repeat(left + 5) + title + "─".repeat(right + 4) + "╮");

    for (int i = transactionList.size() - 1; i >= 0; --i) {
      int ind = transactionList.size() - i - 1;
      Date date = transactionList.get(i).getDate();
      if ((date.after(firstDate) || date.equals(firstDate)) && (date.before(lastDate) || date.equals(lastDate))) {
        System.out.println(transactionList.get(i).printString(ind + 1));
      }
    }
    System.out.println("      ╰" + "─".repeat(101) + "╯");

    Menu.menuAfterTransactionListEnds(br, transactionList, currencyList, false);
  }

  public static void showTransactionById(BufferedReader br, List<Transaction> transactionList,
                                         List<Currency> currencyList, int index, boolean isList10) throws IOException, InterruptedException, AWTException {
    clearScreen();
    int ind = transactionList.size() - index;
    Transaction transaction = transactionList.get(ind);
    String title = transaction.getTitle();
    String description = transaction.getDescription();
    String date = Input.dateToString(transaction.getDate(), "dd.MM.yyyy  HH:mm");
    String type = transaction.getType().getTitle();
    String category = transaction.getCategory().getTitle();
    String currency = transaction.getCurrency().getAcronym();
    double amount = Math.abs(transaction.getAmount());
    String sign = transaction.getType() == TransactionType.INCOMING ? "➕" : "➖";
    System.out.printf("""
        ╭──────────────────────[ Запись номер: %d ]──────────────────────────────────────────────────────┈┈┈┈┈┄┄┄
        │
        │    Категория: %s%s%s
        │    %s: %s%.2f %s
        │
        │     %s%s%s%n""", index, BLUE, category, RESET, type, sign, amount, currency, CYAN, title, RESET);
    if (!description.trim().isEmpty()) {
      String[] arrayStr = description.split(" ");
      int symbolCount = 0;
      System.out.print("│    ");
      for (String s : arrayStr) {
        symbolCount += s.length();
        System.out.print(s + " ");
        if (symbolCount > 70) {
          System.out.println();
          System.out.print("│    ");
          symbolCount = 0;
        }


      }

      System.out.println();
    }
    System.out.println("│    ");
    System.out.println("│    " + date);
    System.out.println("╰" + "─".repeat(45) + "┈┈┈┈┈┄┄┄┄┄┄");
    Menu.menuAnderTransactionView(br, transactionList, currencyList, ind, isList10);

  }


  public static void printTransactionFilteredByType(BufferedReader br, List<Transaction> transactionList,
                                                    List<Currency> currencyList) throws IOException, InterruptedException, AWTException {
    TransactionType transactionType = Input.takeType();
    clearScreen();
    for (Currency currency : currencyList) {
      currency.setTempTotal(0);
    }

    String title = String.format("[%s Записи отфильтрованы по типу операции: %s %s]", YELLOW, transactionType.getTitle(), RESET);
    int left = 59 - (title.length() / 2);
    int right = 59 - (title.length() / 2);
    System.out.println("      ╭" + "─".repeat(left) + title + "─".repeat(right) + "╮");
    for (int i = transactionList.size() - 1; i >= 0; --i) {
      int ind = transactionList.size() - i - 1;
      if (transactionList.get(i).getType() == transactionType) {
        System.out.println(transactionList.get(i).printString(ind + 1));
        Transaction transaction = transactionList.get(i);
        Currency currency = transactionList.get(i).getCurrency();
        currency.setTempTotal(currency.getTempTotal() + transaction.getAmount());
      }
    }
    System.out.println("      ╰" + "─".repeat(101) + "╯");
    System.out.println("      ╭" + "─".repeat(21) +
        "[\u001B[34m Итоговая сумма по валютам (для отфильтрованных записей) \u001B[0m]" + "─".repeat(21) + "╮");

    for (Currency currency : currencyList) {

      String line = String.format("%s : %15.2f %3s", currency.getTitle(), currency.getTempTotal(), currency.getAcronym());
      System.out.printf("      │ %99s │%n", line);
    }
    System.out.println("      ╰" + "─".repeat(101) + "╯");

    Menu.menuAfterTransactionListEnds(br, transactionList, currencyList, false);


  }

  public static void printTransactionFilteredByCategory(BufferedReader br, List<Transaction> transactionList,
                                                        List<Currency> currencyList) throws IOException, InterruptedException, AWTException {
    TransactionType transactionType = Input.takeType();
    Category category = Input.takeCategory(transactionType);
    clearScreen();
    for (Currency currency : currencyList) {
      currency.setTempTotal(0);
    }

    String title = String.format("[%s Записи отфильтрованы по категории: %s %s]", YELLOW, category.getTitle(), RESET);
    int left = 50 - (title.length() - 9) / 2;
    int right = 101 - (title.length() - 9) - left;
    System.out.println("      ╭" + "─".repeat(left) + title + "─".repeat(right) + "╮");
    for (int i = transactionList.size() - 1; i >= 0; --i) {
      int ind = transactionList.size() - i - 1;
      if (transactionList.get(i).getCategory() == category) {
        System.out.println(transactionList.get(i).printString(ind + 1));
        Transaction transaction = transactionList.get(i);
        Currency currency = transactionList.get(i).getCurrency();
        currency.setTempTotal(currency.getTempTotal() + transaction.getAmount());
      }
    }
    System.out.println("      ╰" + "─".repeat(101) + "╯");
    System.out.println("      ╭" + "─".repeat(21) +
        "[\u001B[34m Итоговая сумма по валютам (для отфильтрованных записей) \u001B[0m]" + "─".repeat(21) + "╮");
    for (Currency currency : currencyList) {
      String line = String.format("%s : %15.2f %3s", currency.getTitle(), currency.getTempTotal(), currency.getAcronym());
      System.out.printf("      │ %99s │%n", line);
    }
    System.out.println("      ╰" + "─".repeat(101) + "╯");
    Menu.menuAfterTransactionListEnds(br, transactionList, currencyList, false);
  }

  public static void printTransactionFilteredByCurrency(BufferedReader br, List<Transaction> transactionList,
                                                        List<Currency> currencyList) throws IOException, InterruptedException, AWTException {
    Currency currencyFilter = Input.takeCurrency(currencyList);
    for (Currency currency : currencyList) {
      currency.setTempTotal(0);
    }
    clearScreen();
    String title = String.format("[%s Записи отфильтрованы по валюте: %s %s]", YELLOW, currencyFilter.getTitle(), RESET);
    int left = 50 - (title.length() - 9) / 2;
    int right = 101 - (title.length() - 9) - left;
    System.out.println("      ╭" + "─".repeat(left) + title + "─".repeat(right) + "╮");
    for (int i = transactionList.size() - 1; i >= 0; --i) {
      int ind = transactionList.size() - i - 1;
      if (transactionList.get(i).getCurrency() == currencyFilter) {
        System.out.println(transactionList.get(i).printString(ind + 1));
      }
    }
    System.out.println("      ╰" + "─".repeat(101) + "╯");
    System.out.println("      ╭" + "─".repeat(32) +
        "[\u001B[34m Итоговая сумма в выбранной валюте \u001B[0m]" + "─".repeat(32) + "╮");

      String line = String.format("%s : %15.2f %3s", currencyFilter.getTitle(), currencyFilter.getTotal(), currencyFilter.getAcronym());
      System.out.printf("      │ %99s │%n", line);

    System.out.println("      ╰" + "─".repeat(101) + "╯");
    Menu.menuAfterTransactionListEnds(br, transactionList, currencyList, false);
  }

  public static void clearScreen() throws AWTException, InterruptedException {
    Robot robot = new Robot();
    robot.keyPress(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_ALT);
    robot.keyPress(KeyEvent.VK_SHIFT);
    robot.keyPress(KeyEvent.VK_Q);
    robot.keyRelease(KeyEvent.VK_Q);
    robot.keyRelease(KeyEvent.VK_SHIFT);
    robot.keyRelease(KeyEvent.VK_ALT);
    robot.keyRelease(KeyEvent.VK_CONTROL);
    TimeUnit.MILLISECONDS.sleep(50);


  }

}
