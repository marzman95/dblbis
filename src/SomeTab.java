import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SomeTab extends TabContent {
    private JPanel contentPane;
    private JButton sidePanelButton;
    private JPanel sidePanel;
    private JLabel sideBarTitle;
    private JButton sideBarCloseButton;

    public SomeTab(String title) {
        super(title);
        add(contentPane);
        add(sidePanelButton);
        sidePanel.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.MAGENTA));
        add(sidePanel);
        sidePanel.setVisible(false);
        sidePanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sidePanel.setVisible(true);
                sidePanelButton.setEnabled(false);
            }
        });
        sideBarCloseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sidePanel.setVisible(false);
                sidePanelButton.setEnabled(true);
            }
        });
    }

}
