import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThirdTab extends Tab {
    private JPanel contentPane;
    private JButton deleteButton;

    public ThirdTab() {
        add(contentPane);
        add(deleteButton);
        setTitle("Third");
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Third closing is clicked");
                closeTab();
            }
        });
    }


}
