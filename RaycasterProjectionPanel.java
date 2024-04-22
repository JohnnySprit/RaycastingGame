import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
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
                int projectionWidth = getWidth(); // Width of the projection panel
                int projectionHeight = getHeight(); // Height of the projection panel
                double delta = 40.0; // Constant size for appropriate wall scaling

                for (Ray ray : rays) {
                    // Calculate the distance from the camera to the wall
                    double rayDist = ray.getP1().distance(ray.getP2());

                    // Calculate the height of the wall based on distance
                    double wallHeight = projectionHeight * delta / rayDist;

                    // Calculate the vertical position of the wall on the projection panel
                    double wallY = projectionHeight / 2 - wallHeight / 2;

                    // Calculate the top and bottom coordinates of the wall
                    double topY = wallY;
                    double bottomY = wallY + wallHeight;

                    // Draw the wall segment
                    g2d.setColor(Color.GRAY);
                    g2d.draw(new Line2D.Double(ray.getX1(), topY, ray.getX1(), bottomY));
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
