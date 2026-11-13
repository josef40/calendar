
import javax.swing.SwingUtilities;

// importing our own classes
// import model.Calendar;
// import controller.MainController;
import ui.MainFrame;
// import util.AppPreferences;

/* app entry point */
public class Main {
    /* initializes mvc architecture */
    public static void main(String[] args) {
        // schedules code to run on ui thread
        SwingUtilities.invokeLater(() -> {
            // Calendar model = new Calendar();
            MainFrame view = new MainFrame();

            // load data
            // AppPreferences.load(model);

            // wire model and view
            // new MainController(model, view);

            view.setVisible(true);
        });
    }
}
