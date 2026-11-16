
import javax.swing.SwingUtilities;

import model.Calendar;
import controller.MainController;
import ui.MainFrame;

/** starts the program */
public class Main {
    /**
     * instaniates classes on swing's ui EDT thread, in the end JFrame is set to
     * visible
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calendar calendar = new Calendar();
            MainFrame frame = new MainFrame();
            MainController controller = new MainController(calendar, frame);
            controller.start();
        });
    }
}
