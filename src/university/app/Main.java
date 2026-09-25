package university.app;

import javax.swing.SwingUtilities;
import university.service.ManagementService;
import university.ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame(new ManagementService()));
    }
}
