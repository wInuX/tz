package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public class Name {
    private String type;
    private String category;
    private int id;

    public Name(String name) {
        int index = name.indexOf('-');
        type = name.substring(0, index);
        if (!Character.isDigit(name.charAt(index + 1))) {
            category = name.substring(index + 1, index  + 2);
            id = Integer.parseInt(name.substring(index  + 2));
        } else {
            id = Integer.parseInt(name.substring(index  +1));
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryRaw() {
        return category;
    }

    public void setCategoryRaw(String category) {
        this.category = category;
    }

    public NameCategory getCategory() {
        return NameCategory.getByCode(category);
    }

    public void setCategory(NameCategory category) {
        this.category = category.getCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        sb.append("-");
        if (category != null) {
            sb.append(category);
        }
        sb.append(id);
        return sb.toString();
    }
}
