import models.CityTotal;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
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
    private JPanel settingsPanel;
    private JLabel settingsLabel;
    private JLabel routeSwitchLabel;
    private JComboBox infoModeBox;
    private JLabel typeBoxLabel;
    private JLabel messageLabel;
    private JButton submitButton;
    private JCheckBox citiesCheckBox;
    private JCheckBox routesCheckBox;
    private JFormattedTextField routesAmountField;
    private JTable Information_table;
    private JProgressBar Querybar;
    private JFormattedTextField citiesAmountField;
    private final Screen mainScreen = Screen.getScreen();
    private final JTabbedPane tabPane;
    public processing.core.PApplet map;
    public int citiesAmount = 50;
    public int routesAmount = 50;
    public boolean citiesDisplayed = true;
    public boolean routesDisplayed = true;
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

        gbc.gridx = 0; // Choose city-hub label
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        settingsPanel.add(routeSwitchLabel, gbc);

        gbc.gridx = 3; // City-hub check buttons
        settingsPanel.add(citiesCheckBox, gbc);
        gbc.gridy = 4;
        settingsPanel.add(routesCheckBox, gbc);

        gbc.gridx = 5; // Number fields
        gbc.gridy = 2;
        settingsPanel.add(citiesAmountField, gbc);
        gbc.gridy = 4;
        settingsPanel.add(routesAmountField, gbc);


        gbc.gridx = 0; // Switch type frequency-time
        gbc.gridy = 5;
        settingsPanel.add(typeBoxLabel, gbc);
        gbc.gridx = 3;
        settingsPanel.add(infoModeBox, gbc);

        gbc.gridx = 0; // Message label and submit button
        gbc.gridy = 8;
        settingsPanel.add(messageLabel, gbc);
        gbc.gridx = 3;
        settingsPanel.add(submitButton, gbc);

        // Attach a mouselistener to the mapPanel
        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sidePanel.setVisible(true);
            }
        });

        // Listeners for the sidepanel buttons
//        moreButton.addActionListener(e -> {
//            //StartScreen.super.action(e);
//            String title = "Second tab";
//            TabContent content = new SettingsTab(title);
//            if (tabPane.indexOfTab(title) != -1) {
//                tabPane.setSelectedIndex(tabPane.indexOfTab(title));
//            } else {
//                mainScreen.addTab(content);
//            }
//        });
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

        // Listens for submission
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Submission of options!");

                // Sets the modes (data and information)
//                if (citiesCheckBox.isSelected()) {
//
//                } else {
//                    citiesDisplayed = false;
//                }
//                if (routesCheckBox.isSelected()) {
//                    routesDisplayed = true;
//                    routesAmountField.setEnabled(true);
//                } else {
//                    routesDisplayed = false;
//                    routesAmountField.setEnabled(false);
//                }
                if (infoModeBox.getSelectedItem().equals("Popularity")) {
                    curInfoMode = 1;
                } else if (infoModeBox.getSelectedItem().equals("Duration")) {
                    curInfoMode = 2;
                }
                System.out.println("Changed data and info");
                    //TODO: Do something
                if (citiesDisplayed) {
                    citiesAmount = Integer.parseInt(citiesAmountField.getText());
                    if (citiesAmount > 1796) {
                        citiesAmount = 1796;
                        citiesAmountField.setValue(1796);
                    } else if (citiesAmount < 10) {
                        citiesAmount = 10;
                        citiesAmountField .setValue(10);
                    }
                }
                if (routesDisplayed) {
                    routesAmount = Integer.parseInt(routesAmountField.getText());
                    if (routesAmount > 1000) {
                        routesAmount = 1000;
                        routesAmountField.setValue(1000);
                    } else if (routesAmount < 10) {
                        routesAmount = 10;
                        routesAmountField.setValue(10);
                    }
                }

                // Sets the new amount of information
                map.method("changeMap");

                System.out.println("Redrawn (from startscreen)");
                    //TODO: Do something


            }
        });
        citiesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (citiesCheckBox.isSelected()) {
                    citiesDisplayed = true;
                } else {
                    citiesDisplayed = false;
                }
                citiesAmountField.setEnabled(citiesDisplayed);
            }
        });
        routesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (routesCheckBox.isSelected()) {
                    routesDisplayed = true;
                } else {
                    routesDisplayed = false;
                }
                routesAmountField.setEnabled(routesDisplayed);
            }
        });
    }

    /**
     * Function that sets content of the side panel when a marker is clicked
     * @param corX x-coordinate of the marker
     * @param corY y-coordinate of the marker
     */
    public CityTotal setMarker(float corX, float corY,DataManager dataManager) {
        Querybar.setVisible(true);
        Information_table.setVisible(false);
        CityTotal city= dataManager.getCityStatistics(corY,corX,Querybar);
        String[][] tableData = city.getinfo();
        String[] columnNames = new String[2];
        columnNames[0] = "Name";
        columnNames[1] = "Value";
        Information_table.setModel(new DefaultTableModel(tableData,columnNames));
        Information_table.setVisible(true);
        Querybar.setVisible(false);
        return city;
    }
    public void filltable(CityTotal city){
        Information_table.setVisible(false);
        String[][] tableData = city.getinfo();
        String[] columnNames = new String[2];
        columnNames[0] = "Name";
        columnNames[1] = "Value";
        Information_table.setModel(new DefaultTableModel(tableData,columnNames));
        Information_table.setVisible(true);
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
        return "Amount of cities and routes";
    }

}
