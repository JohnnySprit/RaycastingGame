import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Camera {
    private double x, y;
    private List<Ray> RayObjects;
    private double angle;
    public Camera(double x, double y, List<Ray> RayObjects, double angle) {
        this.x = x;
        this.y = y;
        this.RayObjects = RayObjects;
        this.angle = 0;
        this.state = CameraState.STANDSTILL;
    }

    public void update()
}
