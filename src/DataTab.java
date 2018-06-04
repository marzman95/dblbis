import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class DataTab extends TabContent {
    private JPanel contentPane;
    private JButton closeTabButton;
    private JButton fetchTripsButton;
    private JTable dataTable;

    public DataTab(String title) {
        super(title);
        add(contentPane);
        add(closeTabButton);
        add(dataTable);
        dataTable.setVisible(false);
        closeTabButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Screen.getScreen().removeTab(title);
            }
        });
        fetchTripsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Open table!");
                dataTable.setVisible(true);
                //System.out.println(DataManager.getDataManager().getCitiesList());
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        String[] columns = {"ID", "Name", "Longitude", "Latitude"};
        List<City> cities = new ArrayList<>();
        cities = DataManager.getDataManager().getCitiesList();
        System.out.println(cities);

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        dataTable = new JTable(tableModel);

        for (int i = 0; i < cities.size(); i++) {
            City curCity = cities.get(i);
            tableModel.addRow(new Object[] {
                    i,
                    curCity.getName(),
                    curCity.getLongitude(),
                    curCity.getLatitude()
            });
        }


        dataTable.setVisible(false);
    }
}