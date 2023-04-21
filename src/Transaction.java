import java.util.Date;

public class Transaction implements Finals, Comparable<Transaction> {
  private String title;
  private String description;
  private final Date date;
  private TransactionType type;
  private Category category;
  private Currency currency;
  private double amount;

  /**
   * конструктор транзакции
   *
   * @param title       название
   * @param description описание
   * @param category    категоря транзакции
   * @param type        INCOMING/OUTGOING
   * @param currency    валюта
   * @param amount      сумма транзакции
   * @param date        дата транзакции
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

  /**
   * формирует строку с записью транзакции в виде таблицы
   *
   * @return возвращает строку с записью транзакции в виде таблицы
   */
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
        type.getTitle(), BLUE, category.getTitle(), RESET, Input.dateToString(date, "dd.MM.yyyy  HH:mm"), summa, shortDescription)
        + "    ├" + "─".repeat(101) + "┤";
  }

  /**
   * формирует строку с записью транзакции в виде таблицы с указанием индекса
   *
   * @param i порядковый индекс транзакции в таблице
   * @return возвращает строку с записью транзакции в виде таблицы с указанием индекса
   */
  public String printString(int i) {
    String sign = type == TransactionType.INCOMING ? "➕" : "➖";
    String summa = String.format("%s%.2f %s", sign, Math.abs(amount), currency.getAcronym());
    String shortDescription;
    if (!description.isEmpty()) {
      shortDescription = (title.length() + description.length() + 2) > 80 ?
          title + ": " + description.substring(0, 75 - title.length()) + ".." : title + ": " + description;
    } else shortDescription = title;
    String num;
    if (String.valueOf(i).length() < 3) {
      num = "000" + i;
      num = num.substring(num.length() - 3);
    } else num = String.valueOf(i);

    return String.format("""
                  │    %-12s       |   %s%-60s%s  %16s │
            %5s │ %-15s   %-80s\u2009\u2009│%n""",
        type.getTitle(), BLUE, category.getTitle(), RESET, Input.dateToString(date, "dd.MM.yyyy  HH:mm"), num, summa, shortDescription)
        + "      ├" + "─".repeat(101) + "┤";
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public void setAmount(double amount) {
    this.amount = amount;
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
    return this.date.compareTo(obj.getDate());
  }
}
