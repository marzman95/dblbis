import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class that generates the starting/home tab.
 */
public class StartScreen extends JPanel {
    private JPanel startPanel;
    public JButton moreButton;
    private JButton closeSidePanelButton;
    private JPanel sidePanel;
    private ImageIcon image;
    private Screen mainScreen = Screen.getScreen();
    public JTabbedPane tabPane;

//
    public StartScreen() {
        this.tabPane = mainScreen.tabPane;
        add(startPanel); // Set root panel
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));
        sidePanel.setVisible(false); // Disable sidePanel

        image = new ImageIcon("C:\\Users\\s147433\\OneDrive - TU Eindhoven\\TUE\\Jaar 4\\Y4Q4\\2IOC0 (DBL Business Information Systems)\\Drafts Jeroen\\Map.jpg");

        JLabel label = new JLabel("", image, JLabel.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.BEFORE_FIRST_LINE);
        add(panel);

        // Listener for the map
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Map was clicked");
                add(sidePanel);
                sidePanel.setVisible(true);
            }
        });

        // Listeners for the sidepanel buttons
        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //StartScreen.super.action(e);
                String title = "Second tab";
                TabContent content = new SomeTab(title);
                if (tabPane.indexOfTab(title) != -1) {
                    tabPane.setSelectedIndex(tabPane.indexOfTab(title));
                } else {
                    mainScreen.addTab(content);
                }
            }
        });
        closeSidePanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sidePanel.setVisible(false);
            }
        });
    }

}
