import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RaycasterProjectionPanel extends JPanel {

    private final RaycasterRunner RUNNER;
    private final RaycasterPanel RAYCASTER_PANEL;
    private final Color WALL_COLOR = Color.WHITE;

    public RaycasterProjectionPanel(final RaycasterRunner raycasterRunner, final RaycasterPanel raycasterPanel) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RAYCASTER_PANEL = raycasterPanel;
    }

    private void project(Graphics2D g2d) {
        Camera camera = this.RAYCASTER_PANEL.getCamera();
        if (camera != null) {
            List<Ray> rays = camera.getRays();
            if (rays != null) {
                for (Ray ray : rays) {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.draw(ray);
                }
            }
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        project(g2d);
    }
}
