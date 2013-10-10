package stock_market_app_pro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

/**
 *
 * @author Kintesh
 */
public class StockDataWindow extends JFrame {

    private String[] columnNames = {};
    private ArrayList<StockTableData> tableData = new ArrayList<StockTableData>();
    private ArrayList<StockTableData> tableDataC = new ArrayList<StockTableData>();
    private boolean cOrder = false;
    private JLabel jl_Average = new JLabel();
    private JLabel jl_Drawdown = new JLabel();

    /**
     * Creates new window with JTable with rowdata filled.
     * This windows included a status bar which displays information about the 
     * data in JTable such as Adj Close Average, Maximal Drawdown and number of 
     * records in Table.
     * 
     * @param rawData data to fill JTable with
     * @param title Title of window.
     * @param name Name of Window.
     * @param cOrder true if data needs to be displayed in chronological order.
     * 
     * @see JFrame
     * @see JPanel
     * @see JTable
     * @see BorderLayout
     */
    public StockDataWindow(String rawData, String title, String name, boolean cOrder) {
        /**
         * RawData is separated in to two arrays. FIrst array is columnNames
         * and second array is collection of StockTableData objects which stores 
         * data in 7 (seven) different fields.
         * 
         * Then analyseAdjCol() method is called.
         * If chronological order is set to true then tabledata reference is 
         * change to ordered array tableDataC.
         */
        Scanner scanner = new Scanner(rawData);
        this.columnNames = scanner.nextLine().split("[,]");
        while (scanner.hasNext()) {
            StockTableData std = new StockTableData();
            std.updateData(scanner.nextLine());
            this.tableData.add(std);
        }
        this.cOrder = cOrder;

        analyseAdjCol();
        
        if(this.cOrder){
            tableData = tableDataC;
        }

        /**
         * New instance of JTable is created with MyTableModel and passing two 
         * parameters, columnNames and tableData.
         * various jtable properties are set. 
         */
        JTable jTable = new JTable(new MyTableModel(columnNames, tableData));
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable.setFillsViewportHeight(true);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        /**
         * Status bar is generated using JPanel.
         * The Status bar consist of 3(three) Labels which are separated by 
         * separator. All components are arranged using FLowLayout aligned to LEFT.  
         */
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setPreferredSize(new Dimension(5, 15));
        
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.add(jl_Average);
        statusBar.add(separator);
        statusBar.add(jl_Drawdown);
        separator = new JSeparator(JSeparator.VERTICAL);
        separator.setPreferredSize(new Dimension(5, 15));
        statusBar.add(separator);
        statusBar.add(new JLabel(" Showing "+tableData.size()+" records."));
        
        /*
         * Window(JFrame) Setup.
         * This JFrame ueses BOrderLyout with Table Header in NORTH, Table 
         * (using JScrollPane) in CENTER and Status Bar on SOUTH.
         * Once the Components are added, Title and name are set.
         * The default close operation is set to DISPOSE_ON_CLOSE.
         * Set Location Relative To null.
         * and finally window is set to visible.
         */
        setLayout(new BorderLayout());
        add(jTable.getTableHeader(), BorderLayout.NORTH);
        add(new JScrollPane(jTable), BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        
        setTitle(title);
        setName(name);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(525, 200));
        setSize(525, 380);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
/**
 * analyseAdjCol Method.
 */
    /**
     * This method analyses Adj CLose column to set colour and calculate Average 
     * and Maximal Drawdown.
     * 
     * This method is part of this class and it strictly depends on properties 
     * defined by this class.
     */
    private void analyseAdjCol() {
        int tableDataSize = tableData.size() - 1;
        double previousPrice = 0, newPrice = 0, MDD = 0, peak = -99999, sum = 0;
        String string;
        
        /**
         * This or loop goes through Adj CLose column of and compares each row 
         * with its last row to find and set its colour appropriately using HTML 
         * formating.
         * If the chronological order is ture then the data is added to another 
         * array starting from index 0.
         * Label in Status bar panel are updated. 
         */
        for (int i = tableDataSize; i >= 0; i--) {
            StockTableData std = tableData.get(i);
            newPrice = Double.parseDouble(std.getValue(6).toString());
            if (i == tableDataSize || newPrice == previousPrice) {
                string = "<html><font color=black>" + newPrice + "</font></html>";
            } else if (newPrice > previousPrice) {
                string = "<html><font color=green>" + newPrice + "</font></html>";
            } else {
                string = "<html><font color=red>" + newPrice + "</font></html>";
            }
            std.setValue(6, string);
            previousPrice = newPrice;
            if(cOrder){
                tableDataC.add(std);
            }
            
            //Calculates Maximal Drawdown.
            if (newPrice > peak) {
                peak = newPrice;
            } else {
                double DD = (peak - newPrice);
                if (DD > MDD) {
                    MDD = DD;
                }
            }
            
            sum = sum + newPrice;
        }
        
        DecimalFormat df = new DecimalFormat(".##");
        this.jl_Average.setText(" Adj Close Average: "+df.format(sum/tableDataSize)+" ");
        this.jl_Drawdown.setText(" Maximal Drawdown: "+df.format(MDD)+" ");
    }
}
