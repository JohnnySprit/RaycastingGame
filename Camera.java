package RaycastingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Camera {
    private double x;
    private double y;
    private List<Ray> rays;
    private double angle;        // in degrees
    private double distance;     // max ray distance

    private double moveStep = 5.0;
    private double rotateStep = 5.0;

    // Movement flags
    public boolean movingForward = false;
    public boolean movingBackward = false;
    public boolean strafingLeft = false;
    public boolean strafingRight = false;
    public boolean rotatingLeft = false;
    public boolean rotatingRight = false;
    private Timer movementTimer;
    private List<RectangleObject> walls;

    private KeyboardController controller;

    public Camera(double x, double y, List<RectangleObject> walls) {
        this.x = x;
        this.y = y;
        this.rays = new ArrayList<>();
        this.angle = 0;
        this.distance = 1280;
        this.walls = walls != null ? walls : new ArrayList<>();
        this.controller = new KeyboardController();

        // Timer ticks every 16ms (~60 FPS) to update movement continuously
        movementTimer = new Timer(16, e -> update());
        movementTimer.start();
    }

    /** Update camera position and rotation based on movement flags */
    public void update() {
        double angleRadians = Math.toRadians(angle);

        // Rotation
        if (rotatingLeft) angle -= rotateStep;
        if (rotatingRight) angle += rotateStep;

        // Compute tentative positions
        double nextX = x;
        double nextY = y;

        if (movingForward) {
            nextX += moveStep * Math.cos(angleRadians);
            nextY += moveStep * Math.sin(angleRadians);
        }
        if (movingBackward) {
            nextX -= moveStep * Math.cos(angleRadians);
            nextY -= moveStep * Math.sin(angleRadians);
        }
        if (strafingLeft) {
            nextX += moveStep * Math.cos(angleRadians - Math.PI / 2);
            nextY += moveStep * Math.sin(angleRadians - Math.PI / 2);
        }
        if (strafingRight) {
            nextX += moveStep * Math.cos(angleRadians + Math.PI / 2);
            nextY += moveStep * Math.sin(angleRadians + Math.PI / 2);
        }

        // Only move if not colliding (X and Y separately for smooth sliding)
        if (!isColliding(nextX, y)) x = nextX;
        if (!isColliding(x, nextY)) y = nextY;
    }

    /** Returns the key listener for this camera */
    public KeyAdapter getCameraMovementListener() {
        return controller;
    }

    /** Compute rays for raycasting (projection panel friendly) */
    public void computeRays(double fov, int projectionWidth, List<RectangleObject> scene) {
        rays.clear();

        for (int i = 0; i < projectionWidth; i++) {
            // Map pixel column to camera FOV
            double normalizedAngle = RaycasterUtils.normalize(
                    i, 0, projectionWidth - 1, angle - fov / 2, angle + fov / 2);
            double rad = Math.toRadians(normalizedAngle);

            double endX = x + distance * Math.cos(rad);
            double endY = y + distance * Math.sin(rad);

            Ray ray = new Ray(x, y, endX, endY, normalizedAngle, distance);

            // Find closest intersection with scene objects
            Point2D.Double closestIntersection = null;
            double closestDist = Double.MAX_VALUE;

            for (RectangleObject obj : scene) {
                Point2D.Double intersection = obj.rectIntersection(ray);
                if (intersection != null) {
                    double dist = intersection.distance(x, y);
                    if (dist < closestDist) {
                        closestDist = dist;
                        closestIntersection = intersection;
                    }
                }
            }

            if (closestIntersection != null) {
                ray.x2 = closestIntersection.x;
                ray.y2 = closestIntersection.y;
            }

            if (ray.getP2() == null) {
                ray.x2 = endX;
                ray.y2 = endY;
            }

            rays.add(ray);
        }
    }

    /** Draw rays (optional for debugging) */
    public void drawRays(Graphics2D g2) {
        for (Ray ray : rays) {
            if (ray.getP1() != null && ray.getP2() != null) {
                ray.drawRay(g2);
            }
        }
    }

    /** Draw camera representation */
    public void drawCamera(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fill(new Ellipse2D.Double(x - 5, y - 5, 10, 10));
        drawRays(g2d); // ✅ keep ray projection drawing
    }

    /** Keyboard controller using flags */
    private class KeyboardController extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> rotatingLeft = true;
                case KeyEvent.VK_RIGHT -> rotatingRight = true;
                case KeyEvent.VK_UP, KeyEvent.VK_W -> movingForward = true;
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> movingBackward = true;
                case KeyEvent.VK_A -> strafingLeft = true;
                case KeyEvent.VK_D -> strafingRight = true;
            }
            update();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> rotatingLeft = false;
                case KeyEvent.VK_RIGHT -> rotatingRight = false;
                case KeyEvent.VK_UP, KeyEvent.VK_W -> movingForward = false;
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> movingBackward = false;
                case KeyEvent.VK_A -> strafingLeft = false;
                case KeyEvent.VK_D -> strafingRight = false;
            }
            update();
        }
    }

    private boolean isColliding(double nextX, double nextY) {
        double size = 10;
        for (RectangleObject wall : walls) {
            if (wall != null && rectanglesOverlap(nextX - size / 2, nextY - size / 2, size, size,
                    wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight())) {
                return true;
            }
        }
        return false;
    }

    private boolean rectanglesOverlap(double x1, double y1, double w1, double h1,
                                      double x2, double y2, double w2, double h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

    /** Getters / setters */
    public double getX() { return x; }
    public double getY() { return y; }
    public double getAngle() { return angle; }
    public void setAngle(double angle) { this.angle = angle; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public List<Ray> getRays() { return rays; }
}
