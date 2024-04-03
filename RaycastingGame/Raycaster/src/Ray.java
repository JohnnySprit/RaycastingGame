import java.awt.*;
import java.awt.geom.Line2D;

public class Ray {
    private Line2D.Double line;

    public void drawRay(final Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.draw(line);
    }
}
