import java.util.ArrayList;
import java.util.List;

public enum TransactionType {
  INCOMING(1, "\u001B[32mДОХОД \u001B[0m", new ArrayList<>()),
  OUTGOING(2, "\u001B[31mРАСХОД\u001B[0m", new ArrayList<>());

  private final int id;
  private final String title;
  private final List<Category> categoryList;

  TransactionType(int id, String title, List<Category> categoryList) {
    this.id = id;
    this.title = title;
    this.categoryList = categoryList;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public List<Category> getCategoryList() {
    return categoryList;
  }
}
