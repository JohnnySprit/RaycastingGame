package RaycastingGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class RaycasterPanel extends JPanel {

    private final RaycasterRunner RUNNER;
    private final int RESOLUTION;
    private final Color myColor = new Color(128, 128, 128, 255);

    private Camera camera;
    private List<RectangleObject> walls = new ArrayList<>();
    private RaycasterProjectionPanel projectionPanel;

    public RaycasterPanel(final RaycasterRunner raycasterRunner) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RESOLUTION = 500;
        this.requestFocusInWindow(true);

        generateWalls(); // ✅ Generate walls first
        this.camera = new Camera(320, 320, walls); // then create camera with walls
        this.addKeyListener(this.camera.getCameraMovementListener());
    }

    public void setProjectionPanel(RaycasterProjectionPanel projectionPanel) {
        this.projectionPanel = projectionPanel;
    }

    private void generateWalls() {
        walls.add(new RectangleObject(0.0, 0.0, 640.0, 25.0, Color.BLACK));
        walls.add(new RectangleObject(0.0, 0.0, 25.0, 640.0, Color.BLACK));
        walls.add(new RectangleObject(615.0, 0.0, 25.0, 640.0, Color.BLACK));
        walls.add(new RectangleObject(0.0, 615.0, 640.0, 25.0, Color.BLACK));

        for (int i = 0; i < 5; i++) {
            double x, y, width = 64, height = 64;
            boolean overlap;
            do {
                x = RaycasterUtils.randomDouble(50, 550);
                y = RaycasterUtils.randomDouble(50, 550);
                overlap = false;
                for (RectangleObject wall : walls) {
                    if (wall != null && rectanglesOverlap(x, y, width, height,
                            wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight())) {
                        overlap = true;
                        break;
                    }
                }
            } while (overlap);
            walls.add(new RectangleObject(x, y, width, height, Color.BLACK));
        }
    }

    private boolean rectanglesOverlap(double x1, double y1, double w1, double h1,
                                      double x2, double y2, double w2, double h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

    public void drawEnvironment(Graphics2D g2d) {
        for (RectangleObject wall : walls) {
            if (wall != null) wall.drawObject(g2d);
        }
    }

    public void update() {
        if (camera != null) {
            camera.computeRays(70, getWidth(), walls);
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(myColor);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawEnvironment(g2d);
        if (camera != null) camera.drawCamera(g2d);
    }

    public Camera getCamera() { return camera; }
    public void setCamera(Camera camera) { this.camera = camera; }
}
