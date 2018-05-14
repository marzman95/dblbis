import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

class SettingsTab extends TabContent {
    private JPanel contentPane;
    private JButton closeTabButton;
    JButton fileButton;
    JTextField filepath1;
    //JFileChooser fc;


    public SettingsTab(String title) {
        super(title);
        add(contentPane);
        add(closeTabButton);
        add(fileButton);
        add(filepath1);
        JFileChooser fc = new JFileChooser();
        add(fc);

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
        fileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int returnVal = fc.showOpenDialog(SettingsTab.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would save the file.
                    System.out.println(file);

                } else {

                }

            }


        });

    }

//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//    }
}