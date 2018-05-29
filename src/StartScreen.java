import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class that generates the starting/home tab.
 */
class StartScreen extends JPanel {
    // Initialization
    private JPanel startPanel;
    private JButton moreButton;
    private JButton closeSidePanelButton;
    private JPanel sidePanel;
    private JLabel informationLabel;
    private JButton dataTabButton;
    private final Screen mainScreen = Screen.getScreen();
    private final JTabbedPane tabPane;

    public StartScreen() {
        this.tabPane = mainScreen.tabPane; // Connects the main screen handler to the start screen
        // Setting layout
        this.setLayout(new GridLayout(0,1));
        startPanel.setLayout(new GridLayout(0,2));

        add(startPanel, 0); // Set root panel
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));
        sidePanel.setVisible(false); // Disable sidePanel

        // Creating the panel that shows the map
        JPanel panel = new JPanel(new BorderLayout());

        // Opening the map, add it to the panel, with a mouse-listener
        processing.core.PApplet map = new MapTest();
        panel.add(map);
        map.init();
        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                startPanel.add(sidePanel, 1);
                sidePanel.setVisible(true);
            }
        });
        startPanel.add(panel, 0); // Adds panel (with map) to screen


        // Listeners for the sidepanel buttons
        moreButton.addActionListener(e -> {
            //StartScreen.super.action(e);
            String title = "Second tab";
            TabContent content = new SettingsTab(title);
            if (tabPane.indexOfTab(title) != -1) {
                tabPane.setSelectedIndex(tabPane.indexOfTab(title));
            } else {
                mainScreen.addTab(content);
            }
        });
        closeSidePanelButton.addActionListener(e -> sidePanel.setVisible(false));
        dataTabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = "Data Tab";
                TabContent content = new DataTab(title);
                if (tabPane.indexOfTab(title) != -1) {
                    tabPane.setSelectedIndex(tabPane.indexOfTab(title));
                } else {
                    mainScreen.addTab(content);
                }
            }
        });
    }

}
