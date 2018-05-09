import javax.swing.*;
import java.awt.*;

/**
 * Main handles of the screen
 */
public class Screen extends JFrame {
    private JPanel screenWrapper;
    public JTabbedPane tabPane;
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
     * Adds a new tab, with content, to the screen
     * @param newTab tab to be added
     */
    private void addTabToScreen(Tab newTab) {
        int i = tabPane.getTabCount();
        System.out.println("Index of new: " + i);
        //tabPane.addTab(title, newTab);
        tabPane.add(newTab.title, newTab.content);
        tabPane.setTabComponentAt(i, newTab);
        tabPane.setSelectedIndex(i);
    }

    /**
     * Creates the new tab based on the given content
     * @param content content for the new tab
     */
    public void addTab(TabContent content) {
        Tab newTab = new Tab(tabPane, content.getTitle(), content);
        addTabToScreen(newTab);
    }

    /**
     * Removes the given tab from the screen
     * @param title of the tab to be removed
     */
    public void removeTab(String title) {
        int i = tabPane.indexOfTab(title);

        // Selects the current selected tab
        Component curSelected = tabPane.getSelectedComponent();
        if (i != -1) {
            // Selects the tab-component that has to be removed
            Component removing = tabPane.getComponentAt(i);
            if (removing == curSelected) {
                // If removing component is same as selected, switch to start-tab
                focusStartTab();
            }
            // Remove the tab
            tabPane.remove(removing);
        } else {
            throw new NullPointerException("Tab does no exists");
        }
    }

    /**
     * Helper function to set focus back to the start tab
     */
    private void focusStartTab() {
        tabPane.setSelectedComponent(startScreen);
    }
}
