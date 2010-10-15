package tz.web.client.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class AndCondition implements Condition {
    private List<Condition> conditions = new ArrayList<Condition>();

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
