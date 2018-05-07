import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SecondTab extends Tab {
    private JPanel contentPane;
    private JButton deleteButton;

    public SecondTab() {
        add(contentPane);
        add(deleteButton);
        setTitle("Second");
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                closeTab();
            }
        });
    }
}
