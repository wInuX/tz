package tz.xml.adaptor;

import tz.xml.ActionMode;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Dmitry Shyshkin
 */
public class ActionModeAdaptor extends XmlAdapter<String, ActionMode> {
    @Override
    public ActionMode unmarshal(String v) throws Exception {
        return v != null ? ActionMode.getActionModeByCode(v) : null;
    }

    @Override
    public String marshal(ActionMode v) throws Exception {
        return v != null ? v.getCode() : null;
    }
}
