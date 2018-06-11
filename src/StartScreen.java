import processing.core.PApplet;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class that generates the starting/home tab.
 * Can be called using the Screen.getScreen().getStartScreen();
 */
class StartScreen extends JPanel {
    // Initialization
    private JPanel startPanel;
    private JButton moreButton;
    private JButton closeSidePanelButton;
    private JPanel sidePanel;
    private JLabel informationLabel;
    private JButton dataTabButton;
    private JLabel clickLabel;
    private JPanel settingsPanel;
    private JLabel settingsLabel;
    private JLabel routeSwitchLabel;
    private JRadioButton citiesRadio;
    private JRadioButton routesRadio;
    private JComboBox infoModeBox;
    private JLabel typeBoxLabel;
    public JTextField amountInput;
    private JLabel amountLabel;
    private JLabel messageLabel;
    private JButton submitButton;
    private JSlider amountSlider;
    private final Screen mainScreen = Screen.getScreen();
    private final JTabbedPane tabPane;
    public processing.core.PApplet map;
    public int curInfoAmount = 50;
    private int curDataMode = 1; // 1 for cities, 2 for routes
    private int curInfoMode = 1; // 1 for popularity, 2 for duration

    /**
     * Constructor of the start screen
     */
    public StartScreen() {
        this.tabPane = mainScreen.tabPane; // Connects the main screen handler to the start screen
        // Setting layout
        this.setLayout(new GridLayout());
        add(startPanel, 0); // Set root panel

        // Setup layout of the startpanel itself
        GridBagLayout grid = new GridBagLayout();
        startPanel.setLayout(grid);
        GridBagConstraints gbc = new GridBagConstraints();

        // Setting properties for map panel
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;

        // Creating the panel that shows the map
        JPanel mapPanel = new JPanel(new BorderLayout());

        // Opening the map, add it to the panel, with a mouse-listener
        map = new StartMap();
        mapPanel.setMinimumSize(new Dimension(800,600));
        mapPanel.add(map);
        map.init();
        startPanel.add(mapPanel, gbc); // Adds panel (with map) to screen

        // Setting up the sidepanel already, set to non-visible
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.gridx = GridBagConstraints.EAST;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        gbc.gridwidth = 1;
        startPanel.add(sidePanel, gbc);
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));
        sidePanel.setVisible(false); // Disable sidePanel

        // Setup options/settings panel
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.weighty = 0.1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        startPanel.add(settingsPanel, gbc);

        settingsPanel.setLayout(grid);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridheight = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        settingsPanel.add(settingsLabel, gbc);

        // Sets content of options panel
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; // Switch city-hub label
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        settingsPanel.add(routeSwitchLabel, gbc);

        gbc.gridx = 3; // City-hub radio buttons
        settingsPanel.add(citiesRadio, gbc);
        gbc.gridy = 4;
        settingsPanel.add(routesRadio, gbc);

        gbc.gridx = 0; // Switch type frequency-time
        gbc.gridy = 5;
        settingsPanel.add(typeBoxLabel, gbc);
        gbc.gridx = 3;
        settingsPanel.add(infoModeBox, gbc);

        gbc.gridx = 0; // Amount of cities/routes input
        gbc.gridy = 6;
        settingsPanel.add(amountLabel, gbc);
        gbc.gridx = 3;
        settingsPanel.add(amountInput, gbc);

        gbc.gridx = 0; // Message label and submit button
        gbc.gridy = 8;
        settingsPanel.add(messageLabel, gbc);
        gbc.gridx = 3;
        settingsPanel.add(submitButton, gbc);

        gbc.gridx = 0; // Amount slider
        gbc.gridy = 7;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        settingsPanel.add(amountSlider, gbc);


        // Setup of the amount input fields
        amountInput.setColumns(5);
        amountInput.setText(Integer.toString(curInfoAmount));
        amountSlider.setMaximum(1796);
        amountSlider.setMinimum(10);
        amountLabel.setText(amountLabelBuilder());


        // Attach a mouselistener to the mapPanel
        // TODO: Delete; and implement showing the sidePanel when clicking on a marker or leg
        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sidePanel.setVisible(true);
            }
        });

        // Listeners for the sidepanel buttons
        moreButton.addActionListener(e -> {
            //StartScreen.super.action(e);
            String title = "Second tab";
            TabContent content = new SettingsTab(title);
            if (tabPane.indexOfTab(title) != -1) {
                tabPane.setSelectedIndex(tabPane.indexOfTab(title));
            } else {
                mainScreen.addTab(content);
            }
        });
        closeSidePanelButton.addActionListener(e -> sidePanel.setVisible(false));
        dataTabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = "DataManager Tab";
                TabContent content = new DataTab(title);
                if (tabPane.indexOfTab(title) != -1) {
                    tabPane.setSelectedIndex(tabPane.indexOfTab(title));
                } else {
                    mainScreen.addTab(content);
                }
            }
        });


        // Listens to the amount slider
        amountSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                curInfoAmount = amountSlider.getValue();
                amountInput.setText(Integer.toString(curInfoAmount));

            }
        });

        // Listens for submission
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Submission of options!");

                // Sets the modes (data and information)
                if (citiesRadio.isSelected()) {
                    curDataMode = 1;
                } else if (routesRadio.isSelected()) {
                    curDataMode = 2;
                }
                if (infoModeBox.getSelectedItem().equals("Popularity")) {
                    curInfoMode = 1;
                } else if (infoModeBox.getSelectedItem().equals("Duration")) {
                    curInfoMode = 2;
                }
                System.out.println("Changed data and info");
                    //TODO: Do something

                // Sets the new amount of information
                curInfoAmount = Integer.parseInt(amountInput.getText());
                amountSlider.setValue(curInfoAmount);
                map.method("testMethod");

                System.out.println("Redrawn (from startscreen)");
                    //TODO: Do something

                // Changes the text label
                amountLabel.setText(amountLabelBuilder());

            }
        });
    }

    /**
     * Function that sets content of the side panel when a marker is clicked
     * @param corX x-coordinate of the marker
     * @param corY y-coordinate of the marker
     */
    public void setMarker(float corX, float corY) {
        clickLabel.setText("Clicked at x:" + corX + " and y: " + corY);
    }

    public String dataModeToString (int dataMode) {
        String returnText = "";
        switch (dataMode) {
            case 1:
                returnText = "cities";
                break;
            case 2:
                returnText = "routes";
                break;
        }
        return returnText;
    }

    public String infoModeToString (int infoMode) {
        String returnText ="";
        switch (infoMode) {
            case 1:
                returnText = "most popular";
                break;
            case 2:
                returnText = "longest waiting";
                break;
        }
        return returnText;
    }

    public String amountLabelBuilder() {
        return "Amount of " + infoModeToString(curInfoMode) + " " + dataModeToString(curDataMode);
    }


}
