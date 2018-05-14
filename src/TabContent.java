import javax.swing.*;

abstract class TabContent extends JPanel {
    private final String title;

    TabContent(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
