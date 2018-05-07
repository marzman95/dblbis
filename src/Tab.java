import javax.swing.*;
import java.awt.*;

/**
 * Abstract class that defines the functionality each tab
 */
public abstract class Tab extends JPanel {

    private String title; // Defines the title to be displayed in the tab
    private Screen screen = Screen.getScreen();

    /**
     * Retrieves the title of the current tab
     * @return title of the current tab
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the tab
     * @param newTitle the new title of the current tab
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    /**
     * Closes the current tab
     */
    public void closeTab() {
        Container i = getParent();
        i.remove(this);
        screen.focusStartTab();
    }

}
