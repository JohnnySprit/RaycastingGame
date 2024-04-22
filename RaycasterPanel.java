import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Displays and updates the logic for the top-down raycasting implementation.
 * This class is where the collision detection and movement occurs, whereas the
 * RaycasterPerspectivePanel just projects it to a pseudo-3d environment.
 */
public final class RaycasterPanel extends JPanel {

    /**
     * We need to keep a reference to the parent swing app for sizing and
     * other bookkeeping.
     */
    private final RaycasterRunner RUNNER;

    /**
     * Number of rays to fire from the camera.
     */
    private final int RESOLUTION;
    private final Color myColor = new Color(128, 128, 128, 255);
    private RectangleObject[] OuterWalls;
    private RectangleObject[] InnerWalls;
    private Camera camera;
    public RaycasterPanel(final RaycasterRunner raycasterRunner) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RESOLUTION = 500;
        this.requestFocusInWindow(true);
        this.camera = new Camera(0, 0);
        this.addMouseMotionListener(this.camera.getCameraMotionListener());
        this.addKeyListener(this.camera.getCameraMovementListener());
        OuterWalls = new RectangleObject[4];
        InnerWalls = new RectangleObject[4];
        OuterWalls[0] = new RectangleObject(0, 0, 25, 640, Color.BLACK);
        OuterWalls[1] = new RectangleObject(0, 0,640, 25, Color.BLACK);
        OuterWalls[2] = new RectangleObject(615, 0, 25, 640, Color.BLACK);
        OuterWalls[3] = new RectangleObject(0, 615, 640, 25, Color.BLACK);
        InnerWalls[0] = new RectangleObject(100,25,25, 200, Color.BLACK);
        InnerWalls[1] = new RectangleObject(295,415,25, 200, Color.BLACK);
        InnerWalls[2] = new RectangleObject(415,415,200, 25, Color.BLACK);
        InnerWalls[3] = new RectangleObject(415,165,200, 25, Color.BLACK);
    }
    public void drawEnvironment(Graphics2D g2d) {
        for (RectangleObject Outer : OuterWalls) {
            if (Outer != null)
                Outer.drawObject(g2d);
        }
        for (RectangleObject Inner : InnerWalls) {
            if (Inner != null)
                Inner.drawObject(g2d);
        }
    }
    public void update() {
        Camera camera = getCamera();
        if (camera != null) {
            camera.computeRays(90, getWidth(), Arrays.asList(OuterWalls));
            camera.computeRays(90, getWidth(), Arrays.asList(InnerWalls));
        }
    }
    public Camera getCamera() {
        return camera;
    }
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(myColor);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawEnvironment(g2d);
        camera.drawCamera(g2d);
        System.out.printf("%f %f\n", camera.getX(), camera.getY());
    }
}
