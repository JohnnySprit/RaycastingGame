import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class RectangleObject implements Drawable {
    private double x;
    private double y;
    private double width;
    private double height;
    private Color color;

    public RectangleObject(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    @Override
    public void drawObject(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.drawRect((int) x, (int) y, (int) width, (int) height);
    }
    public Point2D.Double rectIntersection(final Ray ray) {
        Line2D.Double[] sides = {
                new Line2D.Double(x, y, x + width, y), // Top side
                new Line2D.Double(x + width, y, x + width, y + height), // Right side
                new Line2D.Double(x + width, y + height, x, y + height), // Bottom side
                new Line2D.Double(x, y + height, x, y) // Left side
        };

        Point2D.Double closestIntersection = null;
        double closestDistance = Double.MAX_VALUE;

        for (Line2D.Double side : sides) {
            Point2D.Double intersection = RaycasterUtils.intersection(ray, side);
            if (intersection != null) {
                double distance = intersection.distance(ray.x1, ray.y1);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestIntersection = intersection;
                }
            }
        }

        return closestIntersection;
    }
}
