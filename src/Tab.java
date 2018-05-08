import javax.swing.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

/**
 * Class that defines the functionality each tab
 */
public class Tab extends JPanel {
    private final JTabbedPane tabPane;
    public final String title;
    private final Screen mainScreen = Screen.getScreen();
    public Component content;

    public Tab(final JTabbedPane tabPane, String title, Component tabContent) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (tabPane == null) {
            throw new NullPointerException("Tabbed Pane is null");
        }
        this.tabPane = tabPane;
        this.title = title;
        this.content = tabContent;
        setOpaque(false);

        JLabel label = new JLabel() {
            public String getText() {

                int i = tabPane.indexOfTabComponent(Tab.this);
                if (i != -1) {
                    return tabPane.getTitleAt(i);
                }
                return null;
            }
        };

        // Adds the elements to the tab
        add(label);
        label.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
        JButton closeButton = new CloseButton();
        add(closeButton);
        setBorder(BorderFactory.createEmptyBorder(2,0,0,0));
    }

    private class CloseButton extends JButton implements ActionListener {
        /**
         * Implements the closing button constructor
         */
        public CloseButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("Close this tab"); // TODO: Implement title in close tooltip
            setUI(new BasicButtonUI());
            setContentAreaFilled(false);
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            addActionListener(this);
        }

        /**
         * Closes the tab
         * @param e clicking action
         */
        public void actionPerformed(ActionEvent e) {
            int i = tabPane.indexOfTabComponent(Tab.this);
            if (i != -1) {
                tabPane.remove(i);
                mainScreen.focusStartTab();
            }
        }

        public void updateUI() {}

        /**
         * Paints the cross
         * @param g
         */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    /**
     * Implements the hover of the mouse on the button
     */
    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };

//    private String title; // Defines the title to be displayed in the tab
//    private Screen screen = Screen.getScreen();
//    public final JTabbedPane tabsPane;
//
//    public Tab(final JTabbedPane tabsPane) {
//        super(new FlowLayout(FlowLayout.LEFT, 0,0));
//        this.tabsPane = tabsPane;
//
//        JLabel label = new JLabel() {
//            public String getText() {
//                int i = tabsPane.indexOfTabComponent(Tab.this);
//                if (i != -1) {
//                    return tabsPane.getTitleAt(i);
//                }
//                return getTitle();
//            }
//        };
//
//        add(label);
//        label.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
//        JButton tabCloseButton = new CloseButton();
//        add(tabCloseButton);
//        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
//    }
//
//    /**
//     * Retrieves the title of the current tab
//     * @return title of the current tab
//     */
//    public String getTitle() {
//        return title;
//    }
//
//    /**
//     * Sets the title of the tab
//     * @param newTitle the new title of the current tab
//     */
//    public void setTitle(String newTitle) {
//        title = newTitle;
//    }
//
//
//    /**
//     * Closes the current tab
//     */
//    public void closeTab() {
//        Container i = getParent();
//        i.remove(this);
//        screen.focusStartTab();
//    }
//
//    private class CloseButton extends JButton implements ActionListener {
//        public CloseButton() {
//            int size = 17;
//            setPreferredSize(new Dimension(size, size));
//            setToolTipText("close this tab");
//            //Make the button looks the same for all Laf's
//            setUI(new BasicButtonUI());
//            //Make it transparent
//            setContentAreaFilled(false);
//            //No need to be focusable
//            setFocusable(false);
//            setBorder(BorderFactory.createEtchedBorder());
//            setBorderPainted(false);
//            //Making nice rollover effect
//            //we use the same listener for all buttons
//            addMouseListener(buttonMouseListener);
//            setRolloverEnabled(true);
//            //Close the proper tab by clicking the button
//            addActionListener(this);
//        }
//
//        public void actionPerformed(ActionEvent e) {
//            closeTab();
//        }
//
//        //we don't want to update UI for this button
//        public void updateUI() {
//        }
//
//        //paint the cross
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2 = (Graphics2D) g.create();
//            //shift the image for pressed buttons
//            if (getModel().isPressed()) {
//                g2.translate(1, 1);
//            }
//            g2.setStroke(new BasicStroke(2));
//            g2.setColor(Color.BLACK);
//            if (getModel().isRollover()) {
//                g2.setColor(Color.MAGENTA);
//            }
//            int delta = 6;
//            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
//            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
//            g2.dispose();
//        }
//    }
//
//    private final static MouseListener buttonMouseListener = new MouseAdapter() {
//        public void mouseEntered(MouseEvent e) {
//            Component component = e.getComponent();
//            if (component instanceof AbstractButton) {
//                AbstractButton button = (AbstractButton) component;
//                button.setBorderPainted(true);
//            }
//        }
//
//        public void mouseExited(MouseEvent e) {
//            Component component = e.getComponent();
//            if (component instanceof AbstractButton) {
//                AbstractButton button = (AbstractButton) component;
//                button.setBorderPainted(false);
//            }
//        }
//    };

}
