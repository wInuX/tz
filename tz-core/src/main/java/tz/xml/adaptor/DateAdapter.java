package tz.xml.adaptor;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

/**
 * @author Dmitry Shyshkin
 */
public class DateAdapter extends XmlAdapter<String, Date> {
    @Override
    public Date unmarshal(String v) throws Exception {
        return v != null ? new Date(Long.parseLong(v) * 1000) : null;
    }

    @Override
    public String marshal(Date v) throws Exception {
        return v != null ? Long.toString(v.getTime() / 1000) : null;
    }
}
