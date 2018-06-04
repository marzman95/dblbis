import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Example class for a tab, using properties of @see TabContent.
 * Create form by right-click on /src/ -- new -- Dialog, use GridBayLayout;
 * see example of GridBagLayout in startScreen
 */
class SomeTab extends TabContent {
    private JPanel contentPane;
    private JButton closeTabButton;

    // Constructor, title should be unique
    public SomeTab(String title) {
        super(title);
        add(contentPane);
        add(closeTabButton);
        closeTabButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Screen.getScreen().removeTab(title);

            }
        });
    }

}
