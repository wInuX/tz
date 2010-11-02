package tz.game.ui;

import com.google.inject.Inject;
import tz.game.GameModule;
import tz.game.scenario.ExplorationScenatio;
import tz.game.scenario.Scenario;
import tz.game.scenario.ScenarioListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class ScenarioPanel extends JPanel {
    private static List<Class<? extends Scenario>> types = new ArrayList<Class<? extends Scenario>>();

    private List<Scenario> scenarios = new ArrayList<Scenario>();
    private ScenarioTableModel scenarioModel;

    @Inject
    private GameModule module;

    private JTable scenarioTable;

    public void initialize() {
        setLayout(new BorderLayout());
        JList list = new JList(types.toArray());
        add(new JScrollPane(list), BorderLayout.WEST);
        scenarioModel = new ScenarioTableModel();
        scenarioTable = new JTable(scenarioModel);
        add(new JScrollPane(scenarioTable), BorderLayout.CENTER);
        add(createButtonsPanel(), BorderLayout.NORTH);
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Class<? extends Scenario> type = types.get(0);
                try {
                    Scenario scenario = type.getConstructor().newInstance();
                    module.inject(scenario);
                    scenarioModel.add(scenario);
                    scenario.addListsner(new ScenarioListener() {
                        public void scenarioLoaded(Scenario scenario) {
                            
                        }

                        public void scenarioUnloaded(Scenario scenario) {
                            scenarioModel.remove(scenario);
                        }
                    });
                    scenario.start();
                } catch (InstantiationException e1) {
                    throw new IllegalStateException();
                } catch (IllegalAccessException e1) {
                    throw new IllegalStateException();
                } catch (InvocationTargetException e1) {
                    throw new IllegalStateException();
                } catch (NoSuchMethodException e1) {
                    throw new IllegalStateException();
                }
            }
        });
        panel.add(loadButton);
        JButton unloadButton = new JButton("Unload");
        unloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = scenarioTable.getSelectedRow();
                if (row >= 0 && row < scenarioModel.getRowCount()) {
                    Scenario scenario = scenarioModel.getScenarios().get(row);
                    scenario.interrupt();
                }
            }
        });
        panel.add(unloadButton);
        return panel;
    }

    private class ScenarioTableModel extends AbstractTableModel {
        private List<Scenario> scenarios = new ArrayList<Scenario>();

        public int getRowCount() {
            return scenarios.size();
        }

        public int getColumnCount() {
            return 1;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Scenario scenario = scenarios.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return scenario.getClass().getName();
            }
            return null;
        }

        public void add(Scenario scenario) {
            scenarios.add(scenario);
            fireTableRowsInserted(scenarios.size() - 1, scenarios.size() - 1);
        }

        public void remove(Scenario scenario) {
            int index = scenarios.indexOf(scenario);
            scenarios.remove(scenario);
            fireTableRowsDeleted(index, index);
        }

        public List<Scenario> getScenarios() {
            return scenarios;
        }
    }


    static {
        types.add(ExplorationScenatio.class);
    }
}
