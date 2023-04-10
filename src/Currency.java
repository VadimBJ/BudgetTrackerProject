public class Currency {
  private static int counter = 0;
  private final int id;
  private final String title;
  private final String acronym;
  private double total;

  public Currency(String title, String acronym, double total) {
    id = ++counter;
    this.title = title;
    this.acronym = acronym;
    this.total = total;
  }

  @Override
  public String toString() {
    String line = String.format("%s : %15.2f %3s",title,total,acronym);
    return String.format("      │ %99s │",line);
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public String getTitle() {
    return title;
  }

  public String getAcronym() {
    return acronym;
  }

  public double getTotal() {
    return total;
  }
}
