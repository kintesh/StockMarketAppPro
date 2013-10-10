package stock_market_app_pro;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * Main class of this application which extends JFrame.
 * 
 * This class is used to setup JFrame window. The JFrame setup is carried out
 * in the constructor of this class.
 * 
 * @author Kintesh Patel
 */
public class MainApp extends JFrame {

    /**
     * Title of this application, This title will be set as JFrame Title.
     */
    private String TITLE = "Stock Market App Pro by Kintesh Patel";
    
    /**
     * Initialises the JFrame in following sequence:
     * <ol>
     * <li>Tries to set Look and Feel of the frame to match Host computer's GUI.</li>
     * <li>Crates new instance of UIPanel class which initialises various widget
     *     in constructor.</li>
     * <li>Sets frame Title.</li>
     * <li>Sets default close operation so that applications ends/exits when 
     *     frame is closed by user.</li>
     * <li>Sets minimum frame size (330px x 380px).</li>
     * <li>Sets frame location relative to noting which opens frame in the center 
     *     of user's screen.</li>
     * <li>Finally, sets this frame visible.</li>
     * </ol>
     * 
     * @throws HeadlessException 
     * 
     * @see JFrame
     * @see UIManager
     */
    public MainApp() throws HeadlessException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        } catch (InstantiationException ex) {
            System.err.println(ex);
        } catch (IllegalAccessException ex) {
            System.err.println(ex);
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println(ex);
        }

        add(new UIPanel());
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(330, 380));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Adds Runnable instance of MainApp to Event Queue and call run method in 
     * dispatch thread. 
     * 
     * @param args the command line arguments
     * 
     * @see EventQueue
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                //throw new UnsupportedOperationException("Not supported yet.");
                MainApp stockMarketAppPro = new MainApp();
            }
        });
    }
}
