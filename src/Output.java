import java.util.List;

public class Output {
  /***
   * Выводит на экран все содержимое списка
   * @param listToPrint список для печати
   */
  public static void printList(List<?> listToPrint) {
    for (Object obj : listToPrint) {
//      System.out.println("├"+"─".repeat(101)+"┤");
      System.out.println(obj);
    }
  }

  public static void printCurrencyTotal(List<Currency> currencyList) {
    System.out.println("┌" + "─".repeat(37) + "[\u001B[34m Общая сумма по валютам \u001B[0m]" + "─".repeat(38) + "┐");
    printList(currencyList);
    System.out.println("└" + "─".repeat(101) + "┘");
  }

  public static void printTransactionAll(List<Currency> currencyList, List<Transaction> transactionList) {
    printCurrencyTotal(currencyList);
    System.out.println("┌" + "─".repeat(101) + "┐");
    printList(transactionList);
    System.out.println("└" + "─".repeat(101) + "┘");
  }
}
