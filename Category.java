/**
 * Category
 * A group for expenses, like Food, Transportation, or Bills.
 */
public class Category {
    private String categoryID;
    private String categoryName;

    public Category(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        this.categoryName = name;
    }

    @Override
    public String toString() {
        return categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        return categoryID.equals(((Category) o).categoryID);
    }

    @Override
    public int hashCode() {
        return categoryID.hashCode();
    }
}
