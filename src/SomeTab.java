import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SomeTab extends TabContent {
    private JPanel contentPane;
    private JButton closeTabButton;


    public SomeTab(String title) {
        super(title);
        add(contentPane);
        add(closeTabButton);
        closeTabButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Container parent = getParent();
                System.out.println(parent);
                //Screen.getScreen().removeTab();
                int removeIndex = Screen.getScreen().tabPane.indexOfTab(title);
                System.out.println(parent.getComponent(removeIndex));
                Screen.getScreen().removeTab(title);

            }
        });
    }

}
