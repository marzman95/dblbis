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

    public StartScreen() {
        add(startPanel);
        addSecondPanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Tab secondTab = new SecondTab();
                mainScreen.addTab(secondTab);
            }
        });
        addThirdPanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Tab thirdTab = new ThirdTab();
                mainScreen.addTab(thirdTab);
            }
        });
    }

}
