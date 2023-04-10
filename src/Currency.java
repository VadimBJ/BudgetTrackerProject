public class Currency {
  private static int counter = 0;
  private int id;
  private String title;
  private String acronym;
  private double total;

  public Currency(String title, String acronym, double total) {
    id = ++counter;
    this.title = title;
    this.acronym = acronym;
    this.total = total;
  }

  @Override
  public String toString() {
    String line = String.format("%s : %12.2f %3s",title,total,acronym);
    return String.format("      │ %99s │",line);
  }

  public static int getCounter() {
    return counter;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public int getId() {
    return id;
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
