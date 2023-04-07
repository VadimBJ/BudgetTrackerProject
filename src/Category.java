public class Category {
  private static int counter = 0;
  private int id;
  private String title;
  private double total;

  public Category(String title, double total) {
    id = ++counter;
    this.title = title;
    this.total = total;
  }

  @Override
  public String toString() {
    return "Category{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", total=" + total +
        '}';
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public String getTitle() {
    return title;
  }

  public double getTotal() {
    return total;
  }
}
