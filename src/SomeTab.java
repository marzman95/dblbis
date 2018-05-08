import javax.swing.*;

public class SomeTab extends TabContent {
    private JPanel contentPane;
    private JButton deleteButton;

    public SomeTab(String title) {
        super(title);
        add(contentPane);
        add(deleteButton);
        deleteButton.setText(" delete");
    }
}
