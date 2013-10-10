package stock_market_app_pro;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;


/**
 * UIPanel class extends JPanel and implements ActionListener and 
 * WindowListener interfaces.
 * 
 * This class is used to setup user interface of this application. This setup 
 * consist of various JAVA SWING Widgets which allows users to interact and 
 * input data.
 * 
 * This panel uses default FlowLayout.
 * 
 * @see FlowLayout.
 *
 * @author Kintesh Patel
 */
class UIPanel extends JPanel implements ActionListener, WindowListener {
    
    private JTextField jtf_Input = new JTextField();
    private JComboBox jcb_SDay = new JComboBox();
    private JComboBox jcb_SMonth = new JComboBox();
    private JComboBox jcb_SYear = new JComboBox();
    private JComboBox jcb_EDay = new JComboBox();
    private JComboBox jcb_EMonth = new JComboBox();
    private JComboBox jcb_EYear = new JComboBox();
    private JComboBox jcb_Interval = new JComboBox();
    private JButton jb_Lookup = new JButton();
    private JButton jb_Order = new JButton();
    private boolean cOrder = false;
    private ArrayList<Window> al_windows = new ArrayList<Window>();

    /**
     * Constructor for UIPanel which calls initUIPanel when initialised.
     */
    public UIPanel() {
        initUIPanel();
    }
    
    /**
     * Initialises the UIPanel with SWING components with appropriate layout 
     * managers as explained below.
     * </br>
     * This panel (UIPanel) consist of five panels in following order: inputPanel, 
     * beginPanel endPanel intervalPanel and lookupPanel.
     * 
     * @see FlowLayout
     */
    private void initUIPanel() {
        /**
         * Input Panel Setup.
         * Creates new instance of JPanel and sets following properties:
         * Panel border to Titled Border using BorderFactory with "Input" as title.
         * Panel Size to 300px x 62px.
         * Panel Layout to GridBagLayout.
         * Using GridBagConstraints a Label with text "Stock Symbol: " and 
         * Input Field jtf_Input.
         * The input field has action listener which this class implements.
         * The input field has key listener which is used to transform input key 
         * values to Uppercase.
         * 
         */
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        inputPanel.setPreferredSize(new Dimension(300, 62));
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 5;
        inputPanel.add(new JLabel("Stock Symbol: "), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 0;
        inputPanel.add(jtf_Input, c);        
        jtf_Input.setActionCommand("Lookup");
        jtf_Input.addActionListener(this);
        jtf_Input.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                jtf_Input.setText(jtf_Input.getText().toUpperCase());
            }
        });
        

        /**
         * ComboBoxes Setup.
         * Four String arrays holds data for four combo boxes that this program uses.
         * For month and interval combo boxes the data is provided as array. For
         * Day and Year combo boxes, the data is populated using for loop.
         * Once the data are generated, there are assigned to appropriate combo box.
         * All combo boxes are set to custom size.
         * For end date combo boxes the value is selected based on today's date.
         */
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        int yearDiff = currYear - 1969;
        String[] cb_Days = new String[31];
        String[] cb_Months = {"January", "February", "March", "April", "May", "June", 
            "July", "August", "September", "October", "November", "December"};
        String[] cb_Years = new String[yearDiff];
        String[] cb_Interval = {"Monthly", "Weekly", "Daily"};

        //for loop generates list of days
        for (int i = 0; i < cb_Years.length; i++) {
            if(i < cb_Days.length) {
                    if (i < 9) {
                    cb_Days[i] = "0" + (i + 1);
                } else {
                    cb_Days[i] = "" + (i + 1);
                }
            }
            if (i == 0) {
                cb_Years[i] = ""+currYear;
            } else {
                cb_Years[i] = "" + (Integer.parseInt(cb_Years[i - 1]) - 1);
            }
        }

        //Adding data to comboboxes.
        jcb_Interval = new JComboBox(cb_Interval);
        jcb_SDay = new JComboBox(cb_Days);
        jcb_SMonth = new JComboBox(cb_Months);
        jcb_SYear = new JComboBox(cb_Years);
        jcb_EDay = new JComboBox(cb_Days);
        jcb_EMonth = new JComboBox(cb_Months);
        jcb_EYear = new JComboBox(cb_Years);

        //Setting comboxes size.
        jcb_Interval.setPreferredSize(new Dimension(150, 25));
        jcb_SDay.setPreferredSize(new Dimension(60, 25));
        jcb_SMonth.setPreferredSize(new Dimension(100, 25));
        jcb_SYear.setPreferredSize(new Dimension(75, 25));
        jcb_EDay.setPreferredSize(new Dimension(60, 25));
        jcb_EMonth.setPreferredSize(new Dimension(100, 25));
        jcb_EYear.setPreferredSize(new Dimension(75, 25));
        
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        jcb_EDay.setSelectedItem(dateFormat.format(date));
        dateFormat = new SimpleDateFormat("MM");
        jcb_EMonth.setSelectedIndex(Integer.parseInt(dateFormat.format(date))-1);
        dateFormat = new SimpleDateFormat("yyyy");
        jcb_EYear.setSelectedItem(dateFormat.format(date));
        

        /**
         * Begin Panel Setup.
         * Creates new instance of JPanel and sets following properties:
         * Panel border to Titled Border using BorderFactory with "Begin" as title.
         * Panel Size to 300px x 62px.
         * Panel uses default FLowLayout.
         * All three combo boxes to select begin date are added to panel.
         */
        JPanel beginPanel = new JPanel();
        beginPanel.setBorder(BorderFactory.createTitledBorder("Begin"));
        beginPanel.setPreferredSize(new Dimension(300, 62));
        beginPanel.add(jcb_SDay);
        beginPanel.add(jcb_SMonth);
        beginPanel.add(jcb_SYear);
        jcb_SYear.setSelectedIndex(12);

        /**
         * End Panel Setup.
         * Creates new instance of JPanel and sets following properties:
         * Panel border to Titled Border using BorderFactory with "End" as title.
         * Panel Size to 300px x 62px.
         * Panel uses default FLowLayout.
         * All three combo boxes to select end date are added to panel.
         */
        JPanel endPanel = new JPanel();
        endPanel.setBorder(BorderFactory.createTitledBorder("End"));
        endPanel.setPreferredSize(new Dimension(300, 62));
        endPanel.add(jcb_EDay);
        endPanel.add(jcb_EMonth);
        endPanel.add(jcb_EYear);

        /**
         * Interval Panel Setup.
         * Creates new instance of JPanel and sets following properties:
         * Panel border to Titled Border using BorderFactory with "End" as title.
         * Panel Size to 300px x 62px.
         * Panel Layout to GridBagLayout.
         * Using GridBagConstraints, Label with text "Select Interval: " and 
         * interval combo box is added to panel.
         */
        JPanel intervalPanel = new JPanel();
        intervalPanel.setBorder(BorderFactory.createTitledBorder("Interval"));
        intervalPanel.setPreferredSize(new Dimension(300, 62));
        intervalPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 5;
        intervalPanel.add(new JLabel("Select Interval: "), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridx = 1;
        c.gridy = 0;
        intervalPanel.add(jcb_Interval, c);

        /**
         * Lookup Panel Setup.
         * Creates new instance of JPanel and sets following properties:
         * Panel border to Titled Border using BorderFactory with "End" as title.
         * Panel Size to 300px x 62px.
         * Panel uses default FLowLayout.
         * Adds Lookup button and Chronological button which act as toggle button.
         */
        JPanel lookupPanel = new JPanel();
        //lookupPanel.setBorder(BorderFactory.createTitledBorder("Lookup"));
        lookupPanel.setPreferredSize(new Dimension(300, 62));
        lookupPanel.add(jb_Lookup);
        lookupPanel.add(jb_Order);
        jb_Lookup.setText("     Lookup     ");
        jb_Lookup.setActionCommand("Lookup");
        jb_Lookup.addActionListener(this);
        jb_Order.setText("Chronological: OFF");
        jb_Order.setActionCommand("Order");
        jb_Order.addActionListener(this);

        /**
         * this (UIPanel) Panel Setup.
         * Adds inputPanel, beginPanel endPanel intervalPanel and lookupPanel to 
         * UPPanel using default flow layout.
         */
        add(inputPanel);
        add(beginPanel);
        add(endPanel);
        add(intervalPanel);
        add(lookupPanel);
    }

    
/** 
 * ActionListener - actionPerformed.
 */
    /**
     * Implementation of actionPerformed method from ActionListener Interface.
     * This method is called whenever an action event is performed such as Click 
     * on a button or input in text field.
     * 
     * @param ae instance of ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        /**
         * The IF statement checks Action Command, whether its Lookup or Order.
         * If Action Command is Lookup then
         *   The input field is verified using series of if statements.
         *     The first if condition checks if the text filed is empty or not.
         *       If text field is empty then error message is displayed.
         *     The second if condition check if the input size, if input size is
         *       more then 8 characters then error message is displayed.
         *     The third if condition check if the input matches a regular expression 
         *       "[A-Z0-9.]*" which states that text must contain either Uppercase 
         *       A to Z character or 0 to 9 digit or a period. If it does not 
         *       match with this condition then error message is displayed.
         *   
         *   Both input dates are checked to make sure that the begin date is 
         *   before the end date. This is done by converting input date in string 
         *   format to data format which then it can be compared using If statement 
         *   and appropriate message is displayed.
         *   
         *   If all input is valid then makeDataWindow() method is called.
         * 
         * Else If  Action Command is Order then 
         *   If order is false then
         *     set button text to Chronological: ON and set order to true
         *   Else If order it true then 
         *     set button text to Chronological: OFF and set order to false.
         * 
         */

        if ("Lookup".equals(ae.getActionCommand())) {
            boolean verify = true;
            String input = jtf_Input.getText();
            String message = "";
            Pattern pattern = Pattern.compile("[A-Z0-9.]*");
            Matcher matcher = pattern.matcher(input);
            
            //Formats the dates for comparison and verification. 
            Date sDate = new Date(), eDate = new Date();
            try {
                sDate = DateFormat.getDateInstance().parse(jcb_SDay.getSelectedItem() + "-"
                    + jcb_SMonth.getSelectedItem() + "-" + jcb_SYear.getSelectedItem());
                eDate = DateFormat.getDateInstance().parse(jcb_EDay.getSelectedItem() + "-"
                    + jcb_EMonth.getSelectedItem() + "-" + jcb_EYear.getSelectedItem());
            } catch (ParseException ex) {
                Logger.getLogger(UIPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            if (input.isEmpty()) {
                message = "Input is Empty!";
                showWarningMessageDialog(message,"Input Error");
                verify = false;
                jtf_Input.requestFocus();
            } else if (input.length() > 8) {
                message = "'" + jtf_Input.getText() + "' must be maximum 8 "
                        + "characters in length (uppercase, digits and period)!";
                showWarningMessageDialog(message,"Input Error");
                verify = false;
                jtf_Input.requestFocus();
            } else if (!matcher.matches()) {
                message = "Input contains invalid characters! \n"
                        + "Only uppercase, digits and period are allowed.";
                showWarningMessageDialog(message,"Input Error");
                verify = false;
                jtf_Input.requestFocus();
            } else if(!(sDate.before(eDate))){
                String messageDate = "The begin date entered is before or same as the end date."
                        + "\n\nBegin Date: "+sDate+"\nEnd Date: "+eDate;
                showWarningMessageDialog(messageDate,"Input Date Error");
                verify = false;
            }

            //If all inputs are valid then call makeDataWindow() method.
            if (verify) {
                makeDataWindow();
            }
            
        } else if("Order".equals(ae.getActionCommand())) {
            if(cOrder == false) {
                jb_Order.setText("<html>Chronological: <font color=green><b>ON</b></font></html>");
                cOrder = true;
            } else if(cOrder == true) {
                jb_Order.setText("<html>Chronological: OFF</html>");
                cOrder = false;
            }
        }
        
        if("SidexSide".equals(ae.getActionCommand())) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            
            int width = dimension.width/2;
            int height = dimension.height-75;
            System.out.println(dimension.height/2);
            System.out.println(dimension.width/2);
            
            for(int i=0;i<al_windows.size();i++) {
                Dimension d = new Dimension(width, height);
                al_windows.get(i).setSize(d);
                if (i%2 == 0)
                    al_windows.get(i).setLocation(0, 0);
                else
                    al_windows.get(i).setLocation(width, 0);
            }
        }
    }
    
/**
 * showWarningMessageDialog Method.
 */    
    /**
     * This method displays warning message dialog.
     * 
     * @param message of message dialog..
     * @param title of message dialog.
     */
    private void showWarningMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message,
                        title, JOptionPane.WARNING_MESSAGE);
    }
    
/**
 * makeDataWindow Method.
 */
    /**
     * This method gets stock data and adds information to table. 
     * This method is part of this class and it strictly depends on properties 
     * defined by this class.
     */
    private void makeDataWindow() {
        /**
         * Sets interval to m, w, d based on current selection of interval Combobox.
         * Generates URL string with user's input for query.
         * Then URL is queried by calling readURL method from URLReader class.
         * 
         * Using If statement the result of query is checked. 
         * If the query return null then 
         *   Invalid Symbol message is displayed.
         * Else
         *   Data Window title is generated.
         *   And Stock symbol is checked whether its opened or not by calling 
         *   openedWindows method. If Window is not opened then a new instance of 
         *   StockDataWindow is created with rawData, title, name and cOrder 
         *   properties in constructor.
         *   Then the instance is added to windows ArrayList and symbol is added 
         *   to Symbols.
         * 
         */
        String interval = "m";
        if (jcb_Interval.getSelectedIndex() == 1) {
            interval = "w";
        } else if (jcb_Interval.getSelectedIndex() == 2) {
            interval = "d";
        }

        String url = "http://ichart.yahoo.com/table.csv?"
                + "s=" + jtf_Input.getText().toUpperCase() + "&"
                + "a=" + jcb_SMonth.getSelectedIndex() + "&"
                + "b=" + jcb_SDay.getSelectedItem() + "&"
                + "c=" + jcb_SYear.getSelectedItem() + "&"
                + "d=" + jcb_EMonth.getSelectedIndex() + "&"
                + "e=" + jcb_EDay.getSelectedItem() + "&"
                + "f=" + jcb_EYear.getSelectedItem() + "&"
                + "g=" + interval + "";

        if(!openedWindows(url)) {
            String rawData = URLReader.readURL(url);
            if (rawData == null) {
                String message = "'" + jtf_Input.getText().toUpperCase() + "' is not a valid symbol!";
                JOptionPane.showMessageDialog(null, message,
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                jtf_Input.requestFocus();
            } else {
                String order = "Reverse Chronological";
                if (cOrder) {
                    order = "Chronological";
                }
                String name = jtf_Input.getText().toUpperCase();
                String title = name + ": " + jcb_SYear.getSelectedItem() + "-"
                        + jcb_SMonth.getSelectedItem() + "-" + jcb_SDay.getSelectedItem()
                        + " to " + jcb_EYear.getSelectedItem() + "-"
                        + jcb_EMonth.getSelectedItem() + "-" + jcb_EDay.getSelectedItem()
                        + " (" + jcb_Interval.getSelectedItem() + ") "
                        + order;


                StockDataWindow dataWindow = new StockDataWindow(rawData, title, url, cOrder);
                dataWindow.addWindowListener(this);

                al_windows.add(dataWindow);
            }
        }
    }
    
/**
 * openedWindows Method.
 */
    /**
     * This method check whether an instance of StockDataWindow exist or not.
     * 
     * @param name Name of window to look/search.
     * @return true if window with given name is found and sets focus to window.
     */
    private boolean openedWindows(String name) {
        /**
         * For loops goes through Symbols ArrayList and looks for window based on name.
         * If window is found it returns true and sets focus to opened window.
         */
        for(int i=0;i<al_windows.size();i++) {
            if(name.equals(al_windows.get(i).getName())) {
                al_windows.get(i).requestFocus();
                al_windows.get(i).toFront();
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * WindowListener - windowClosed, windowOpened, windowClosing, windowIconified, 
     *                  windowDeiconified, windowActivated and windowDeactivated.
     *
     * Implementation of all abstract methods from WindowListener Interface.
     * This method is called whenever a window event is performed such as
     * opening or closing windows.
     *
     */
    
    /**
     * Implementation of windowClosed method from WindowListener Interface.
     *
     * @param we instance of WindowEvent
     */
    @Override
    public void windowClosed(WindowEvent we) {
        /**
         * When a window is closed, its removed for the array and returns focus 
         * back to main window then inputfield.
         */
        al_windows.remove(we.getWindow());
        this.requestFocus();
        jtf_Input.requestFocus();
    }

    /**
     * Implementation of windowOpened method from WindowListener Interface.
     *
     * @param we instance of WindowEvent
     */
    @Override
    public void windowOpened(WindowEvent we) {
    }

    /**
     * Implementation of windowClosing method from WindowListener Interface.
     *
     * @param we instance of WindowEvent
     */
    @Override
    public void windowClosing(WindowEvent we) {
    }

    /**
     * Implementation of windowIconified method from WindowListener Interface.
     *
     * @param we instance of WindowEvent
     */
    @Override
    public void windowIconified(WindowEvent we) {
    }

    /**
     * Implementation of windowDeiconified method from WindowListener Interface.
     *
     * @param we instance of WindowEvent
     */
    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    /**
     * Implementation of windowActivated method from WindowListener Interface.
     *
     * @param we instance of WindowEvent
     */
    @Override
    public void windowActivated(WindowEvent we) {
    }

    /**
     * Implementation of windowDeactivated method from WindowListener Interface.
     *
     * @param we instance of WindowEvent
     */
    @Override
    public void windowDeactivated(WindowEvent we) {
    }
}
