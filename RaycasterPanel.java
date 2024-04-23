import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Displays and updates the logic for the top-down raycasting implementation.
 * This class is where the collision detection and movement occurs, whereas the
 * RaycasterPerspectivePanel just projects it to a pseudo-3d environment.
 */
public final class RaycasterPanel extends JPanel {

    /**
     * We need to keep a reference to the parent swing app for sizing and
     * other bookkeeping.
     */
    private final RaycasterRunner RUNNER;

    /**
     * Number of rays to fire from the camera.
     */
    private final int RESOLUTION;
    private final Color myColor = new Color(128, 128, 128, 255);
    private Camera camera;
    private List<RectangleObject> walls = new ArrayList<>();
    private RaycasterProjectionPanel projectionPanel; // Add this line


    public RaycasterPanel(final RaycasterRunner raycasterRunner) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RESOLUTION = 500;
        this.requestFocusInWindow(true);
        this.camera = new Camera(0, 0);
        this.addMouseMotionListener(this.camera.getCameraMotionListener());
        this.addKeyListener(this.camera.getCameraMovementListener());
        this.walls = new ArrayList<>();
        generateWalls();
    }
    public void setProjectionPanel(RaycasterProjectionPanel projectionPanel) {
        this.projectionPanel = projectionPanel;
    }
    private void generateWalls() {
        // Add outer walls
        walls.add(new RectangleObject(0.0, 0.0, 640.0, 25.0, Color.BLACK));
        walls.add(new RectangleObject(0.0, 0.0, 25.0, 640.0, Color.BLACK));
        walls.add(new RectangleObject(615.0, 0.0, 25.0, 640.0, Color.BLACK));
        walls.add(new RectangleObject(0.0, 615.0, 640.0, 25.0, Color.BLACK));

        // Add inner walls with random positions and sizes
        for (int i = 0; i < 5; i++) { // Adjust the number of inner walls as needed
            double x, y, width, height;
            boolean overlap;
            do {
                // Generate random position and size
                x = RaycasterUtils.randomDouble(50, 550); // Adjust range to avoid edges
                y = RaycasterUtils.randomDouble(50, 550); // Adjust range to avoid edges
                width = 64; // Adjust range for width
                height = 64; // Adjust range for height

                // Check for overlap with existing walls
                overlap = false;
                for (RectangleObject wall : walls) {
                    if (wall != null && rectanglesOverlap(x, y, width, height, wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight())) {
                        overlap = true;
                        break;
                    }
                }
            } while (overlap);

            Color color = Color.BLACK; // Color for wall texture
            walls.add(new RectangleObject(x, y, width, height, color));
        }
    }

    // Method to check if two rectangles overlap
    private boolean rectanglesOverlap(double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }
    public void drawEnvironment(Graphics2D g2d) {
        for (RectangleObject wall : walls) {
            if (wall != null)
                wall.drawObject(g2d);
        }
    }
    public void update() {
        Camera camera = getCamera();
        if (camera != null) {
            camera.computeRays(70, getWidth(), walls);
        }
    }
    public Camera getCamera() {
        return camera;
    }
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(myColor);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawEnvironment(g2d);
        camera.drawCamera(g2d);
        System.out.printf("%f %f\n", camera.getX(), camera.getY());
    }
}
