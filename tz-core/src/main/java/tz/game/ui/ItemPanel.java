package tz.game.ui;

import com.google.inject.Inject;
import tz.game.service.AbstractUserListener;
import tz.game.service.UserService;
import tz.xml.Item;
import tz.xml.MyParameters;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.List;
import java.util.*;

/**
 * @author Dmitry Shyshkin
 */
public class ItemPanel extends JPanel {
    @Inject
    private UserService userService;

    private ItemTableModel itemsModel;

    public void initialize() {
        userService.addListener(new AbstractUserListener() {
            @Override
            public void itemsChanged(java.util.List<Item> items) {
                itemsModel.setItems(items);
            }
        });
        setLayout(new BorderLayout());
        itemsModel = new ItemTableModel();
        JTable jTable = new JTable(itemsModel);
        add(new JScrollPane(jTable), BorderLayout.CENTER);
    }

    private class ItemTableModel extends AbstractTableModel {
        private static final long serialVersionUID = -7991136017915398715L;

        private String[] headers = new String[]{"id", "name", "count"};
        private java.util.List<Item> items ;

        private ItemTableModel() {
            items = new ArrayList<Item>();
        }

        private ItemTableModel(java.util.List<Item> items) {
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

        public void setItems(java.util.List<Item> items) {
            this.items = items;
            fireTableDataChanged();
        }
    }

}
