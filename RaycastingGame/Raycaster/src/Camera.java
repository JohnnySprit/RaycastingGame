import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Camera extends JPanel {
    private double x;
    private double y;
    private List<Ray> rays;
    private double angle;
    private double distance; // Maximum distance for the rays
    private CameraMotionListener cameraMotionListener;

    public Camera(double x, double y) {
        this.x = x;
        this.y = y;
        this.rays = new ArrayList<>();
        this.angle = 0;
        this.distance = 1280; // Setting default maximum distance
        this.cameraMotionListener = new CameraMotionListener();
        this.addKeyListener(new CameraMovementListener());
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                requestFocusInWindow();
            }
        });
        this.setFocusable(true); // Ensure the panel can receive key events
    }

    public void updatePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public List<Ray> getRays() {
        return rays;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public CameraMotionListener getCameraMotionListener() {
        return cameraMotionListener;
    }

    private class CameraMotionListener extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }
    private double moveStep = 5.0; // Amount to move when arrow keys are pressed

    private class CameraMovementListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                // Move camera left
                x -= moveStep * Math.cos(Math.toRadians(angle));
                y -= moveStep * Math.sin(Math.toRadians(angle));
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                // Move camera right
                x += moveStep * Math.cos(Math.toRadians(angle));
                y += moveStep * Math.sin(Math.toRadians(angle));
            }
        }
    }

    public KeyAdapter getCameraMovementListener() {
        return new CameraMovementListener();
    }

    public void computeRays(double fov, int projectionWidth, List<RectangleObject> scene) {
        rays.clear(); // Clear previous rays

        for (int i = 1; i <= projectionWidth; i++) {
            // Normalize i to be in the interval [𝜃 - Φ/2, 𝜃 + Φ/2]
            double normalizedAngle = RaycasterUtils.normalize(i, 1, projectionWidth, angle - fov / 2, angle + fov / 2);

            // Calculate angle in radians
            double angleRadians = Math.toRadians(normalizedAngle);

            // Calculate temporary end-point of the ray
            double endX = x + distance * Math.cos(angleRadians);
            double endY = y + distance * Math.sin(angleRadians);

            // Create a new Ray object with the temporary end-point
            Ray ray = new Ray(x, y, endX, endY, normalizedAngle, distance);

            // Find closest intersection point with scene objects
            Point2D.Double closestIntersection = null;
            double closestDistance = Double.MAX_VALUE;
            for (RectangleObject obj : scene) {
                Point2D.Double intersection = obj.rectIntersection(ray);
                if (intersection != null) {
                    double distanceToIntersection = intersection.distance(x, y);
                    if (distanceToIntersection < closestDistance) {
                        closestDistance = distanceToIntersection;
                        closestIntersection = intersection;
                    }
                }
            }

            // Modify the end-point of the ray object to be the closest intersection point found
            if (closestIntersection != null) {
                ray.x2 = closestIntersection.x;
                ray.y2 = closestIntersection.y;
            }

            // Add the ray to the list
            rays.add(ray);
        }
    }
    public void drawRays(Graphics2D g2) {
        for (Ray ray : rays) {
            ray.drawRay(g2);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw rays
        drawRays(g2);
    }
}
