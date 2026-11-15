
import javax.swing.SwingUtilities;

import model.Calendar;
import controller.MainController;
import ui.MainFrame;

/* app entry point */
public class Main {
    /* initializes mvc architecture */
    public static void main(String[] args) {
        // schedules code to run on ui thread
        SwingUtilities.invokeLater(() -> {
            Calendar calendar = new Calendar();
            MainFrame frame = new MainFrame();
            MainController controller = new MainController(calendar, frame);
            controller.start();
        });
    }
}
