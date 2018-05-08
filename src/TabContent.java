import javax.swing.*;

public abstract class TabContent extends JPanel {
    private String title;

    protected TabContent(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
