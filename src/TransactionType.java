public enum TransactionType {
  INCOMING(1, "ДОХОД",0),
  OUTGOING(2, "РАСХОД",0);

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
