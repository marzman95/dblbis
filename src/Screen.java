import javax.swing.*;
import java.awt.*;

/**
 * Main handles of the screen
 */
public class Screen extends JFrame{
    private JPanel screenWrapper;
    private JTabbedPane tabPane;
    private static Screen screen = new Screen(); // Static
    private static StartScreen startScreen = new StartScreen(); // Static

    private Screen(){}

    public static Screen getScreen() {
        return screen;
    }

    /**
     * Generates and shows the main screen
     */
    private void createScreen() {
        //screen = new JFrame("Test");
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setContentPane(screenWrapper);
        screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screen.pack();
        screen.setVisible(true);
    }

    /**
     * Starts the screen
     */
    public void startScreen() {
        tabPane.addTab("Start", startScreen);
        createScreen();
    }

    /**
     * Adds a panel to the tabbed screen
     * @param tab the panel to be added
     */
    public void addTab(Tab tab) {
        tabPane.addTab(tab.getTitle(), tab);
        tabPane.setSelectedComponent(tab);
    }

    /**
     * Helper function to set focus back to the start tab
     */
    public void focusStartTab() {
        tabPane.setSelectedComponent(startScreen);
    }
}
