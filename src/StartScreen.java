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
        this.setLayout(new GridLayout());
        add(startPanel, 0); // Set root panel

        // Setup layout of the startpanel itself
        GridBagLayout grid = new GridBagLayout();
        startPanel.setLayout(grid);
        GridBagConstraints gbc = new GridBagConstraints();

        // Setting properties for map panel
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;

        // Creating the panel that shows the map
        JPanel mapPanel = new JPanel(new BorderLayout());

        // Opening the map, add it to the panel, with a mouse-listener
        processing.core.PApplet map = new MapTest();
        mapPanel.setMinimumSize(new Dimension(800,600));
        mapPanel.add(map);
        map.init();
        startPanel.add(mapPanel, gbc); // Adds panel (with map) to screen

        // Setting up the sidepanel already, set to non-visible
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.gridx = GridBagConstraints.EAST;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.2;
        gbc.gridwidth = 1;
        startPanel.add(sidePanel, gbc);
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));
        sidePanel.setVisible(false); // Disable sidePanel

        // Attach a mouselistener to the mapPanel
        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sidePanel.setVisible(true);
            }
        });

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
