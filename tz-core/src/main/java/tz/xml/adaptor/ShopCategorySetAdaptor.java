package tz.xml.adaptor;

import tz.xml.NameCategory;
import tz.xml.ShopCategory;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
public class ShopCategorySetAdaptor extends XmlAdapter<String, Set<ShopCategory>> {
    @Override
    public Set<ShopCategory> unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        Set<ShopCategory> set = new HashSet<ShopCategory>();
        for (String s : v.split(",")) {
            if (s.length() == 0) {
                continue;
            }
            int index = s.indexOf(':');
            String name = s.substring(0, index);
            if (name.equals("0")) {
                continue;
            }
            int count = Integer.parseInt(s.substring(index + 1));
            set.add(new ShopCategory(NameCategory.getByCode(name), count));
        }
        return set;
    }

    @Override
    public String marshal(Set<ShopCategory> v) throws Exception {
        if (v == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (ShopCategory category : v) {
            sb.append(",");
            sb.append(category.getCategory().getCode()).append(":").append(category.getItemCount());
        }
        return sb.toString();
    }
}
