import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        String[] columns = {"ID", "Name", "Extra"};
        Object[][] trips = { {1, "Me", "Hey"}, {2, "You", "Test"}};
        dataTable = new JTable(trips, columns);
        dataTable.setVisible(false);
    }
}

class Trip {
    int ID;
    String name;
    String extra;

    public Trip(int ID, String name, String extra) {
        this.ID = ID;
        this.name = name;
        this.extra = extra;
    }
}