package stock_market_app_pro;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * This class implements Table Model class based on Abstract Table Model. The 
 * Table Model will used to provide data to JTable when column names and table 
 * data passed when initialised. 
 * 
 * @see AbstractTableModel
 *
 * @author Kintesh
 */
public class MyTableModel extends AbstractTableModel {

    private String[] columnNames = {};
    private ArrayList<StockTableData> tableData = new ArrayList<StockTableData>();

    /**
     * Constructor for MyTableModel which sets up columnNames and tableData when 
     * initialised.
     * 
     * @param columnNames String array of Column Names.
     * @param tableData ArrayList of StockTableData containing Table Data.
     */
    public MyTableModel(String[] columnNames, ArrayList<StockTableData> tableData) {
        this.columnNames = columnNames;
        this.tableData = tableData;
    }
    
    /**
     * Implementation of getColumnCount method from AbstractTableModel Class.
     *
     * @return length(size) of columnNames.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Implementation of getColumnName method from AbstractTableModel Class.
     *
     * @param column index of column.
     * @return Column Name as String of given index.
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Implementation of getRowCount method from AbstractTableModel Class.
     *
     * @return length(size) of Table Data.
     */
    @Override 
    public int getRowCount() {
        return tableData.size();
    }

    /**
     * Implementation of getValueAt method from AbstractTableModel Class.
     *
     * @param rowIndex index of row.
     * @param columnIndex index of column.
     * @return object located at given index.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableData.get(rowIndex).getValue(columnIndex);
    }
}
