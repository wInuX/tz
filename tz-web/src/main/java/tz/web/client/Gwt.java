package tz.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

/**
 * @author Dmitry Shyshkin
 */
public class Gwt implements EntryPoint {
    public void onModuleLoad() {
        RootPanel.get().add(new Label("123"));
    }
}
