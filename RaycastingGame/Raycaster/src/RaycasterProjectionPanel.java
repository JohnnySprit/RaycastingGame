import javax.swing.*;
import java.awt.*;

public class RaycasterProjectionPanel extends JPanel {

    /**
     * Root driver object to keep track of sizing.
     */
    private final RaycasterRunner RUNNER;

    /**
     * Overhead panel to access the generated rays.
     */
    private final RaycasterPanel RAYCASTER_PANEL;

    public RaycasterProjectionPanel(final RaycasterRunner raycasterRunner, final RaycasterPanel raycasterPanel) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RAYCASTER_PANEL = raycasterPanel;
    }

    public void update() {
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
