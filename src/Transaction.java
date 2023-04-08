import java.util.Date;

public class Transaction {
  private String title;
  private String Description;
  private Date date;
  private double amount;
  private TransactionType type;
  private Category category;
  private Currency currency;

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
  public Transaction(String title, String description, Category category, TransactionType type, Currency currency, double amount) {
    this.title = title;
    Description = description;
    this.date = new Date();
    this.category = category;
    this.type = type;
    this.currency = currency;
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "\nTransaction{" +
        "title='" + title + '\'' +
        ", \nDescription='" + Description + '\'' +
        ", \ndate=" + Input.dateToString(date) +
        ", \namount=" + amount +
        ", \ntype=" + type.getTitle() +
        ", \ncategory=" + category.getTitle() +
        ", \ncurrency=" + currency.getTitle() +
        '}';
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    Description = description;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setAmount(double amount) {
    this.amount = amount;
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

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return Description;
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
}
