import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Output implements Finals {

  public static void writeToEncryptFile2(List<Transaction> transactionList,
                                         List<Currency> currencyList) throws IOException {
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

    //выгружаем категории по типам операции
    String type = TransactionType.INCOMING.name();
    int categoryCount = TransactionType.INCOMING.getCategoryList().size();
    StringBuilder resultStr = new StringBuilder();
    resultStr.append(type).append(";").append(categoryCount).append(";");
    for (Category category : TransactionType.INCOMING.getCategoryList()) {
      resultStr.append(category.getTitle()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(Encryption.encryptStrCesar(resultStr.toString(), 17) + "\n");

    type = TransactionType.OUTGOING.name();
    categoryCount = TransactionType.OUTGOING.getCategoryList().size();
    resultStr = new StringBuilder();
    resultStr.append(type).append(";").append(categoryCount).append(";");
    for (Category category : TransactionType.OUTGOING.getCategoryList()) {
      resultStr.append(category.getTitle()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(Encryption.encryptStrCesar(resultStr.toString(), 17) + "\n");

    //выгружаем валюты
    resultStr = new StringBuilder();
    resultStr.append(currencyList.size()).append(";");
    for (Currency currency : currencyList) {
      resultStr.append(currency.getTitle()).append(";");
      resultStr.append(currency.getAcronym()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(Encryption.encryptStrCesar(resultStr.toString(), 17) + "\n");

    //выгружаем все записи
    fileWriter.write(Encryption.encryptStrCesar(String.valueOf(transactionList.size()), 17) + "\n");
    for (Transaction transaction : transactionList) {
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
    System.out.println(GREEN + "... Файл сохранен ..." + RESET);
    System.exit(0);
  }

  public static void writeToEncryptFile(List<Transaction> transactionList,
                                        List<Currency> currencyList) throws IOException {
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

    //выгружаем категории по типам операции
    String type = TransactionType.INCOMING.name();
    int categoryCount = TransactionType.INCOMING.getCategoryList().size();
    StringBuilder resultStr = new StringBuilder();
    resultStr.append(type).append(";").append(categoryCount).append(";");
    for (Category category : TransactionType.INCOMING.getCategoryList()) {
      resultStr.append(category.getTitle()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(Encryption.encryptXOR(resultStr.toString(), Input.user.getPasswordHash()) + "\n");

    type = TransactionType.OUTGOING.name();
    categoryCount = TransactionType.OUTGOING.getCategoryList().size();
    resultStr = new StringBuilder();
    resultStr.append(type).append(";").append(categoryCount).append(";");
    for (Category category : TransactionType.OUTGOING.getCategoryList()) {
      resultStr.append(category.getTitle()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(Encryption.encryptXOR(resultStr.toString(), Input.user.getPasswordHash()) + "\n");

    //выгружаем валюты
    resultStr = new StringBuilder();
    resultStr.append(currencyList.size()).append(";");
    for (Currency currency : currencyList) {
      resultStr.append(currency.getTitle()).append(";");
      resultStr.append(currency.getAcronym()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(Encryption.encryptXOR(resultStr.toString(), Input.user.getPasswordHash()) + "\n");

    //выгружаем все записи
    fileWriter.write(transactionList.size() + "\n");
    for (Transaction transaction : transactionList) {
      resultStr = new StringBuilder();
      resultStr.append(transaction.getTitle()).append(";");
      resultStr.append(transaction.getDescription()).append(";");
      resultStr.append(transaction.getType()).append(";");
      resultStr.append(transaction.getCategory().getTitle()).append(";");
      resultStr.append(transaction.getCurrency().getAcronym()).append(";");
      resultStr.append(transaction.getAmount()).append(";");
      resultStr.append(Input.dateToString(transaction.getDate(), "dd.MM.yyyy  HH:mm"));
      fileWriter.write(Encryption.encryptXOR(resultStr.toString(), Input.user.getPasswordHash()) + "\n");
    }

    fileWriter.close();
    System.out.println(GREEN + "... Файл сохранен ..." + RESET);
  }

  public static void writeToFile(List<Transaction> transactionList,
                                 List<Currency> currencyList) throws IOException {
    String filename = Input.getUser().getPasswordHash().substring(2, 10);
    File file = new File("res/" + filename + ".csv");
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

    //выгружаем категории по типам операции
    String type = TransactionType.INCOMING.name();
    int categoryCount = TransactionType.INCOMING.getCategoryList().size();
    fileWriter.write(type + ";" + categoryCount + ";");
    StringBuilder resultStr = new StringBuilder();
    for (Category category : TransactionType.INCOMING.getCategoryList()) {
      resultStr.append(category.getTitle()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(resultStr + "\n");

    type = TransactionType.OUTGOING.name();
    categoryCount = TransactionType.OUTGOING.getCategoryList().size();
    fileWriter.write(type + ";" + categoryCount + ";");
    resultStr = new StringBuilder();
    for (Category category : TransactionType.OUTGOING.getCategoryList()) {
      resultStr.append(category.getTitle()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(resultStr + "\n");

    //выгружаем валюты
    resultStr = new StringBuilder();
    resultStr.append(currencyList.size()).append(";");
    for (Currency currency : currencyList) {
      resultStr.append(currency.getTitle()).append(";");
      resultStr.append(currency.getAcronym()).append(";");
    }
    resultStr.deleteCharAt(resultStr.length() - 1);
    fileWriter.write(resultStr + "\n");

    //выгружаем все записи
    fileWriter.write(transactionList.size() + "\n");
    for (Transaction transaction : transactionList) {
      resultStr = new StringBuilder();
      resultStr.append(transaction.getTitle()).append(";");
      resultStr.append(transaction.getDescription()).append(";");
      resultStr.append(transaction.getType()).append(";");
      resultStr.append(transaction.getCategory().getTitle()).append(";");
      resultStr.append(transaction.getCurrency().getAcronym()).append(";");
      resultStr.append(transaction.getAmount()).append(";");
      resultStr.append(Input.dateToString(transaction.getDate(), "dd.MM.yyyy  HH:mm"));
      fileWriter.write(resultStr + "\n");
    }

    fileWriter.close();
    System.out.println(GREEN + "... Файл сохранен ..." + RESET);
  }

  /***
   * Выводит на экран все содержимое списка
   * @param listToPrint список для печати
   */
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
      System.out.print("Нажмите ENTER для возврата в главное меню: ");
      String wait = br.readLine();
      return;
    }

      printCurrencyTotal(currencyList);
      String title = String.format("[%s Показано записей: %d    Период: с %s по %s %s]", YELLOW, transactionList.size(),
          Input.dateToString(transactionList.get(transactionList.size() - 1).getDate(), "dd.MM.yyyy"),
          Input.dateToString(transactionList.get(0).getDate(), "dd.MM.yyyy"), RESET);
      int left = 101 / 2 - title.length() / 2;
      int right = 101 - left - title.length();

      System.out.println("      ╭" + "─".repeat(left + 5) + title + "─".repeat(right + 4) + "╮");
      for (int i = 0; i < transactionList.size(); i++) {
        System.out.println(transactionList.get(i).printString(i + 1));
      }
      System.out.println("      ╰" + "─".repeat(101) + "╯");
      Menu.menuAfterTransactionListEnds(br, transactionList, currencyList);
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

  public static void printTransactionBy10(List<Transaction> transactionList, List<Currency> currencyList) throws AWTException, InterruptedException, IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    if (currencyList.size() == 0 || transactionList.size() == 0) {
      clearScreen();
      System.out.println(RED + "⛔ Перед просмотром Вам необходимо создать хотя бы одну запись!" + RESET);
      System.out.println();
      System.out.print("Нажмите ENTER для возврата в главное меню: ");
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
          Input.dateToString(transactionList.get(end - 1).getDate(), "dd.MM.yyyy"),
          Input.dateToString(transactionList.get(0).getDate(), "dd.MM.yyyy"), RESET);
      int left = 101 / 2 - title.length() / 2;
      int right = 101 - left - title.length();
      System.out.println("      ╭" + "─".repeat(left + 5) + title + "─".repeat(right + 4) + "╮");
      for (int i = j * 10; i < current; i++) {
        System.out.println(transactionList.get(i).printString(i + 1));
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
        Menu.menuAfterTransactionListEnds(br, transactionList, currencyList);
        end = transactionList.size();
      }
      if (!answer.isEmpty()) {
        Menu.menuAnderTransactionList(br, transactionList, currencyList);
        --j;
        end = transactionList.size();
      }
    }
  }

  public static void showTransactionById(BufferedReader br, List<Transaction> transactionList,
                                         List<Currency> currencyList, int index) throws IOException, InterruptedException, AWTException {
    clearScreen();
    Transaction transaction = transactionList.get(index - 1);
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

    Menu.menuAnderTransactionView(br, transactionList, currencyList, index - 1);
  }
}
