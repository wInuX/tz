package tz.game.ui;

import com.google.inject.Inject;
import tz.game.service.AbstractUserListener;
import tz.game.service.UserService;
import tz.xml.Item;
import tz.xml.MyParameters;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class GameFrame extends JFrame {
    @Inject
    private UserService userService;

    private ItemTableModel itemsModel;

    public GameFrame() throws HeadlessException {
    }

    public void start() {
        userService.addListener(new AbstractUserListener() {
            public void parameterChanged(MyParameters parameters) {
                setTitle(parameters.getLogin());
            }

            @Override
            public void itemsChanged(List<Item> items) {
                itemsModel.setItems(items);
            }
        });
        setSize(400, 300);
        setVisible(true);
        setLayout(new BorderLayout());
        JTabbedPane jTabbedPane = new JTabbedPane();
        add(jTabbedPane, BorderLayout.CENTER);
        jTabbedPane.addTab("Items", createItemsPanel());
    }

    private Component createItemsPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        itemsModel = new ItemTableModel();
        JTable jTable = new JTable(itemsModel);
        jPanel.add(new JScrollPane(jTable), BorderLayout.CENTER);
        return jPanel;
    }

    private class ItemTableModel extends AbstractTableModel {
        private static final long serialVersionUID = -7991136017915398715L;

        private String[] headers = new String[]{"id", "name", "count"};
        private List<Item> items ;

        private ItemTableModel() {
            items = new ArrayList<Item>();
        }

        private ItemTableModel(List<Item> items) {
            this.items = items;
        }

        public int getRowCount() {
            return items.size();
        }

        public int getColumnCount() {
            return headers.length;
        }

        @Override
        public String getColumnName(int i) {
            return headers[i];
        }

        public Object getValueAt(int row, int column) {
            Item item = items.get(row);
            switch (column) {
                case 0:
                    return item.getId();
                case 1:
                    return item.getText();
                case 2:
                    return item.getCount();
            }
            return null;
        }

        public void setItems(List<Item> items) {
            this.items = items;
            fireTableDataChanged();
        }
    }
}
