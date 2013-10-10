package stock_market_app_pro;

/**
 * StockTableData class defines a simple way of way of storing table data in to 
 * into an array. Each instance(object) of this class represents a row of table 
 * with 7 columns.
 * 
 * @author Kintesh
 */
public class StockTableData {

    private Object sDate;
    private Object sOpen;
    private Object sHigh;
    private Object sLow;
    private Object sClose;
    private Object sVolume;
    private Object sAdjCLose;

    /**
     * Default Constructor for StockTableData.
     */
    public StockTableData() {
    }

    /**
     * A method to get value located at particular column index.
     * @param index of column of which to retrieve. 
     * @return Object located at given index.
     */
    public Object getValue(int index) {
        switch (index) {
            case 0:
                return this.sDate;
            case 1:
                return this.sOpen;
            case 2:
                return this.sHigh;
            case 3:
                return this.sLow;
            case 4:
                return this.sClose;
            case 5:
                return this.sVolume;
            case 6:
                return this.sAdjCLose;
            default:
                return "N/A";
        }
    }

    /**
     * A method to set value at given column index.
     * @param index of column.
     * @param value object value to add to given index.
     */
    public void setValue(int index, Object value) {
        switch (index) {
            case 0:
                this.sDate = value;
            case 1:
                this.sOpen = value;
            case 2:
                this.sHigh = value;
            case 3:
                this.sLow = value;
            case 4:
                this.sClose = value;
            case 5:
                this.sVolume = value;
            case 6:
                this.sAdjCLose = value;
        }
    }

    /**
     * A method to update data when given comma seperated values as single string. 
     * @param sData data as string in CSV format.
     */
    public void updateData(String sData) {
        if (sData != null) {
            String[] stockData = sData.split(",(?=[^ ])");
            sDate = stockData[0];
            sOpen = stockData[1];
            sHigh = stockData[2];
            sLow = stockData[3];
            sClose = stockData[4];
            sVolume = stockData[5];
            sAdjCLose = stockData[6];
        }

    }
}
