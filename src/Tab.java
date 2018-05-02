import javax.swing.*;

/**
 * Abstract class that defines the functionality each tab
 */
public abstract class Tab extends JPanel {

    private String title; // Defines the title to be displayed in the tab

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

}
