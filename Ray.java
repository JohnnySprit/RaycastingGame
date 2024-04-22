import java.awt.*;
import java.awt.geom.Line2D;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Ray extends Line2D.Double {
    private double angle;
    private double distance;
    
    //Constructs a new ray object with the specified coordinates, angle, and distance.
    public Ray(double x1, double y1, double x2, double y2, double angle, double distance) {
        super(x1, y1, x2, y2);
        this.angle = angle;
        this.distance = distance;
    }
    // Gets the angle of the ray
    public double getAngle() {
        return angle;
    }

    // Get the distance the ray travels
    public double getDistance() {
        return distance;
    }
    // Draws the ray on the given graphics.
    public void drawRay(Graphics2D g2) {
        g2.setColor(java.awt.Color.WHITE);
        g2.draw(this);
    }
}
