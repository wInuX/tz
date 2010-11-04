package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public class ShopCategory {
    private NameCategory category;

    private int itemCount;

    public ShopCategory(NameCategory category, int itemCount) {
        this.category = category;
        this.itemCount = itemCount;
    }

    public NameCategory getCategory() {
        return category;
    }

    public void setCategory(NameCategory category) {
        this.category = category;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
