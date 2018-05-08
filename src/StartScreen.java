import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class that generates the starting/home tab.
 */
public class StartScreen extends JPanel {
    private JPanel startPanel;
    private JButton addSecondPanelButton;
    private JButton addThirdPanelButton;
    private Screen mainScreen = Screen.getScreen();
    public JTabbedPane tabPane;

    public StartScreen() {
        this.tabPane = mainScreen.tabPane;
        add(startPanel);
        addSecondPanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String title = "Second tab";
                TabContent content = new SomeTab(title);
                mainScreen.addTab(content);

            }
        });
        addThirdPanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String title = "Third tab";
                TabContent content = new SomeTab(title);
                mainScreen.addTab(content);
            }
        });
    }

}
