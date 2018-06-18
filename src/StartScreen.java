
import models.City;
import models.CityTotal;
import models.Destination;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


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
    private JLabel sidePanelTitle;
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
    private JTable table1;
    private JProgressBar Querybar;
    private JFormattedTextField citiesAmountField;
    private JTable table2;
    private JTextArea destinationsTextArea;
    private JTextArea informationTextArea;
    private JProgressBar Querybar1;
    private JScrollPane table1ScrollPane;
    private JScrollPane table2ScrollPane;
    private JTextField fileTextField;
    private JButton fileSubmitButton;
    private JProgressBar fileProgressBar;
    private JFileChooser fc = new JFileChooser();
    private File csvfile;
    private final Screen mainScreen = Screen.getScreen();
    private final JTabbedPane tabPane;
    public processing.core.PApplet map;
    public int citiesAmount = 50;
    public int routesAmount = 50;
    public boolean citiesDisplayed = true;
    public boolean routesDisplayed = true;
    public int curInfoMode = 1; // 1 for popularity, 2 for duration

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
        gbc.gridwidth = 1;

        // Creating the panel that shows the map
        JPanel mapPanel = new JPanel(new BorderLayout());

        // Opening the map, add it to the panel, with a mouse-listener
        map = new StartMap();
        mapPanel.setMinimumSize(new Dimension(1000,900));
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
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        startPanel.add(sidePanel, gbc);
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        sidePanel.setVisible(false); // Disable sidePanel

        // Setup options/settings panel
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.weighty = 0.1;
        gbc.gridwidth = 1;
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
        gbc.gridwidth = 5;
        settingsPanel.add(infoModeBox, gbc);
        gbc.gridwidth = 2;

        gbc.gridx = 0; // Message label and submit button
        gbc.gridy = 8;
        settingsPanel.add(messageLabel, gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 5;
        settingsPanel.add(submitButton, gbc);
        gbc.gridwidth = 2;

        gbc.gridx = 0; // Textfield for file input
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        settingsPanel.add(fileTextField, gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 5;
        settingsPanel.add(fileSubmitButton, gbc);
        gbc.gridx = 0;
        gbc.gridwidth = 7;
        gbc.gridy = 10;
        settingsPanel.add(fileProgressBar, gbc);

        // Layout for sidepanel
        sidePanel.setLayout(grid);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        sidePanel.add(sidePanelTitle, gbc);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sidePanel.add(informationTextArea, gbc);
        gbc.gridy = 2;
        sidePanel.add(Querybar, gbc);
        gbc.gridy = 3;
        gbc.gridheight = 5;
        sidePanel.add(table1ScrollPane, gbc);
        gbc.gridy = 9;
        gbc.gridheight = 1;
        sidePanel.add(destinationsTextArea, gbc);
        gbc.gridy = 10;
        sidePanel.add(Querybar1, gbc);
        gbc.gridy = 11;
        gbc.gridheight = 5;
        sidePanel.add(table2ScrollPane, gbc);
        gbc.gridy = 17;
        gbc.fill = GridBagConstraints.NONE;
        sidePanel.add(closeSidePanelButton, gbc);


        // Listener for closing sidepanel
        closeSidePanelButton.addActionListener(e -> sidePanel.setVisible(false));

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
                if (infoModeBox.getSelectedItem().equals("Frequency")) {
                    curInfoMode = 1;
                } else if (infoModeBox.getSelectedItem().equals("In Degree")) {
                    curInfoMode = 2;
                } else if (infoModeBox.getSelectedItem().equals("Out Degree")) {
                    curInfoMode = 3;
                } else if (infoModeBox.getSelectedItem().equals("Total Degree")) {
                    curInfoMode = 4;
                }

                System.out.println("Changed data and info");
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
        table2.addComponentListener(new ComponentAdapter() {
        });
        table2.addFocusListener(new FocusAdapter() {
        });
        table2.addContainerListener(new ContainerAdapter() {
        });
        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Object selectedCity = null;
                if (table2.getSelectedColumn() == 0) {
                    selectedCity = table2.getValueAt(table2.getSelectedRow(), table2.getSelectedColumn());
                } else if (table2.getSelectedColumn() == 1) {
                    selectedCity = table2.getValueAt(table2.getSelectedRow(), table2.getSelectedColumn()-1);
                } else if (table2.getSelectedColumn() == 2){
                    selectedCity = table2.getValueAt(table2.getSelectedRow(), table2.getSelectedColumn()-2);
                }
                System.out.println(selectedCity.toString());
            }
        });
        fileTextField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                int returnVal = fc.showOpenDialog(StartScreen.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                   csvfile = fc.getSelectedFile();
                   fileTextField.setText(csvfile.getAbsolutePath());
                } else {

                }
            }
        });
        fileSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("test");
                Thread thread = new Thread(new Setup(csvfile));
                thread.start();
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
        Querybar1.setVisible(true);
        table1.setVisible(false);
        table2.setVisible(false);
        CityTotal city= dataManager.getCityStatistics(corY,corX,Querybar,Querybar1);
        String[][] tableData1 = city.getinfo();
        String[] columnNames1 = new String[2];
        columnNames1[0] = "Name";
        columnNames1[1] = "Value";
        table1.setModel(new DefaultTableModel(tableData1,columnNames1));
        String[] columnNames2 = new String[3];
        columnNames2[0] = "Destination";
        columnNames2[1] = "Distance";
        columnNames2[2] = "Times-used";
        String[][] tableData2 = city.getDestinations();
        table2.setModel(new DefaultTableModel(tableData2,columnNames2));
        table2.setVisible(true);
        table1.setVisible(true);
        Querybar.setVisible(false);
        Querybar1.setVisible(false);
        sidePanelTitle.setText("<html>City: <b><span style='color:red'>" + city.getName() + "</b></span></html>");
        return city;
    }
    public void filltable(CityTotal city){
        table1.setVisible(false);
        table1.setDefaultEditor(Object.class, null);
        String[][] tableData = city.getinfo();
        String[] columnNames = new String[2];
        columnNames[0] = "Name";
        columnNames[1] = "Value";
        table1.setModel(new DefaultTableModel(tableData,columnNames));
        String[] columnNames2 = new String[3];
        columnNames2[0] = "Destination";
        columnNames2[1] = "Distance";
        columnNames2[2] = "Times-used";
        String[][] tableData2 = city.getDestinations();
        table2.setModel(new DefaultTableModel(tableData2,columnNames2));
        table2.setCellSelectionEnabled(true);
        table2.setVisible(true);
        table1.setVisible(true);
    }

    public void activateSidePanel() {
        sidePanelTitle.setText("City is loading");
        sidePanel.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        table1 = new JTable() {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table2 = new JTable() {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
