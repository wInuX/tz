package tz.game.ui;

import com.google.inject.Inject;
import tz.game.service.AbstractUserListener;
import tz.game.service.UserService;
import tz.xml.MyParameters;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dmitry Shyshkin
 */
public class GameFrame extends JFrame {
    @Inject
    private UserService userService;

    @Inject
    private ItemPanel itemPanel;

    public GameFrame() throws HeadlessException {
    }

    public void start() {
        setSize(400, 300);
        setVisible(true);
        setLayout(new BorderLayout());
        JTabbedPane jTabbedPane = new JTabbedPane();
        add(jTabbedPane, BorderLayout.CENTER);
        jTabbedPane.addTab("Items", createItemsPanel());
        userService.addListener(new AbstractUserListener() {
            public void parameterChanged(MyParameters parameters) {
                setTitle(parameters.getLogin());
            }
        });

    }

    private Component createItemsPanel() {
        itemPanel.initialize();
        return itemPanel;
    }

}
