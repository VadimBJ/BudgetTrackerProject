public class Category {
    private static int counter = 0;
    private final int id;
    private String title;

    public Category(String title) {
        id = ++counter;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
