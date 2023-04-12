import java.util.Date;

public class Transaction implements Finals, Comparable<Transaction> {
  private final String title;
  private final String description;
  private final Date date;
  private final TransactionType type;
  private final Category category;
  private final Currency currency;
  private final double amount;

  /**
   * конструктор транзакции
   *
   * @param title       название
   * @param description описание
   * @param category    категоря транзакции
   * @param type        INCOMING/OUTGOING
   * @param currency    валюта
   * @param amount      сумма транзакции
   */
  public Transaction(String title, String description, TransactionType type, Category category, Currency currency,
                     double amount, Date date) {
    this.title = title;
    this.description = description;
    this.type = type;
    this.category = category;
    this.currency = currency;
    currency.setTotal(currency.getTotal() + amount);
    this.amount = amount;
    this.date = date;
  }

  @Override
  public String toString() {
    String sign = type == TransactionType.INCOMING ? "➕" : "➖";
    String summa = String.format("%s%.2f %s", sign, Math.abs(amount), currency.getAcronym());
    String shortDescription;
    if (!description.isEmpty()) {
      shortDescription = (title.length() + description.length() + 2) > 80 ?
          title + ": " + description.substring(0, 75 - title.length()) + ".." : title + ": " + description;
    } else shortDescription = title;

    return String.format("""
               │    %-12s       |   %s%-60s%s  %16s │
            1. │ %-15s   %-80s\u2009\u2009│%n""",
        type.getTitle(), BLUE, category.getTitle(), RESET, Input.dateToString(date,"dd.MM.yyyy  HH:mm"), summa, shortDescription)
        + "    ├" + "─".repeat(101) + "┤";
  }

  public String printString(int i) {
    String sign = type == TransactionType.INCOMING ? "➕" : "➖";
    String summa = String.format("%s%.2f %s", sign, Math.abs(amount), currency.getAcronym());
    String shortDescription;
    if (!description.isEmpty()) {
      shortDescription = (title.length() + description.length() + 2) > 80 ?
          title + ": " + description.substring(0, 75 - title.length()) + ".." : title + ": " + description;
    } else shortDescription = title;
    String num = "000" + i;
    num = num.substring(num.length()-3);

    return String.format("""
                  │    %-12s       |   %s%-60s%s  %16s │
            %5s │ %-15s   %-80s\u2009\u2009│%n""",
        type.getTitle(), BLUE, category.getTitle(), RESET, Input.dateToString(date,"dd.MM.yyyy  HH:mm"),num, summa, shortDescription)
        + "      ├" + "─".repeat(101) + "┤";
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Date getDate() {
    return date;
  }

  public double getAmount() {
    return amount;
  }

  public TransactionType getType() {
    return type;
  }

  public Category getCategory() {
    return category;
  }

  public Currency getCurrency() {
    return currency;
  }

  @Override
  public int compareTo(Transaction obj) {
    return obj.getDate().compareTo(this.date);
  }
}
