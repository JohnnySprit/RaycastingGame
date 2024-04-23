import java.awt.*;

public class CircleObject implements Drawable {
    private double x, y, radius;
    private Color color;

    public CircleObject(int x, int y, int radius, Color color) {
        this.x = x - radius;
        this.y = y - radius;
        this.radius = radius;
        this.color = color;
    }
    @Override
    public void drawObject(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.drawOval((int) x, (int) y, (int) (radius*2), (int) (radius*2));
    }
}