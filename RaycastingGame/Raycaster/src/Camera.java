import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Camera {
    private double x, y;
    private List<Ray> RayObjects;
    private double angle;
    private int numRays;
    private int fov;
    
    public Camera(double x, double y, List<Ray> RayObjects, double angle) {
        this.x = x;
        this.y = y;
        this.RayObjects = RayObjects;
        this.angle = 0;
        this.state = CameraState.STANDSTILL;
        this.numRays = numRays;
        this.fov = fov;

        CameraMotionListener motionListener = new CameraMotionListener();
    }
    private class CameraMotionListener extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }
        public CameraMotionListener getCameraMotionListener() {
            return new CameraMotionListener();
        }

        public void projectRays() {
            RayObjects.clear();
            double angleStep = (double) fov / numRays;
            double startAngle = angle - (fov / 2.0);

            for (int i = 0; i < numRays; i++) {
                double rayAngle = startAngle + i * angleStep;
                Ray ray = new Ray(x, y, rayAngle);
                RayObjects.add(ray);
            }
            public void updateAngle(double deltaX, double deltaY) {
                double dx = deltaX * 0.005;
                double dy = deltaY * 0.005;

                angle += dx;
            }

            public List<Ray> getRays() {
                return RayObjects;
            }

            public double getAngle() {
                return angle;
            }
        }
    }
