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
}