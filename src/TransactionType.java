public enum TransactionType {
  INCOMING(1, "\u001B[32mДОХОД\u001B[0m",0),
  OUTGOING(2, "\u001B[31mРАСХОД\u001B[0m",0);

  private int id;
  private String title;
  private double total;

  TransactionType(int id, String title, double total) {
    this.id = id;
    this.title = title;
    this.total = total;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public double getTotal() {
    return total;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTotal(double total) {
    this.total = total;
  }
}
