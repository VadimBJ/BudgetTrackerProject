import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Output implements Finals {

  public static void writeToFile(List<Transaction> transactionList,
                                 List<Currency> currencyList) throws IOException {
    File file = new File("res/Test.csv");
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
      resultStr.append(Input.dateToString(transaction.getDate()));
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
    System.out.println("      ┌" + "─".repeat(37) + "[\u001B[34m Общая сумма по валютам \u001B[0m]" + "─".repeat(38) + "┐");
    printList(currencyList);
    System.out.println("      └" + "─".repeat(101) + "┘");
  }

  public static void printTransactionAll(List<Transaction> transactionList, List<Currency> currencyList, String title) {
    printCurrencyTotal(currencyList);
    if (title.trim().isEmpty()) { //todo итерация по 10, передаем в метод с границами цикла по списку
      title = title.trim();
    } else {
      title = "[ " + YELLOW + title + RESET + " ]";
    }
    int left = 101 / 2 - title.length() / 2;
    int right = 101 - left - title.length();

    System.out.println("      ┌" + "─".repeat(left + 5) + title + "─".repeat(right + 4) + "┐");
//    System.out.println("      ┌" + "─".repeat(101) + "┐");
    for (int i = 0; i < transactionList.size(); i++) {
      System.out.println(transactionList.get(i).printString(i+1));
    }

//    printList(transactionList);
    System.out.println("      └" + "─".repeat(101) + "┘");
  }
}
