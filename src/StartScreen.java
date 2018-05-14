import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class that generates the starting/home tab.
 */
class StartScreen extends JPanel {
    private JPanel startPanel;
    private JButton moreButton;
    private JButton closeSidePanelButton;
    private JPanel sidePanel;
    private JLabel informationLabel;
    private final Screen mainScreen = Screen.getScreen();
    private final JTabbedPane tabPane;

    public StartScreen() {
        this.tabPane = mainScreen.tabPane;
        this.setLayout(new GridLayout(0,1));
        startPanel.setLayout(new GridLayout(0,2));
        add(startPanel, 0); // Set root panel
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));
        sidePanel.setVisible(false); // Disable sidePanel

        // TODO: Change filepath of image
        ImageIcon image = new ImageIcon("C:\\Users\\s147433\\OneDrive - TU Eindhoven\\TUE\\Jaar 4\\Y4Q4\\2IOC0 (DBL Business Information Systems)\\Drafts Jeroen\\Map.jpg");

        JLabel label = new JLabel("", image, JLabel.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.BEFORE_FIRST_LINE);
        startPanel.add(panel, 0);

        // Listener for the map
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                startPanel.add(sidePanel, 1);
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
    }

}
