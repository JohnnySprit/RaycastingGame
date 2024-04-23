import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class RaycasterProjectionPanel extends JPanel {

    private final RaycasterRunner RUNNER;
    private final RaycasterPanel RAYCASTER_PANEL;
    private BufferedImage wallTexture; // Texture for the walls


    public RaycasterProjectionPanel(final RaycasterRunner raycasterRunner, final RaycasterPanel raycasterPanel) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RAYCASTER_PANEL = raycasterPanel;
        try {
            // Replace "C:/path/to/your/image.jpg" with the actual absolute path to your image file
            String absolutePath = "C:\\Users\\Johnny\\IdeaProjects\\RaycastingGame-main\\wall-texture.jpg";
            wallTexture = ImageIO.read(new File(absolutePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void project(Graphics2D g2d) {
        Camera camera = this.RAYCASTER_PANEL.getCamera();
        if (camera != null) {
            List<Ray> rays = camera.getRays();
            if (rays != null) {
                int projectionWidth = getWidth(); // Width of the projection panel
                int projectionHeight = getHeight(); // Height of the projection panel
                double delta = 40.0; // Constant size for appropriate wall scaling

                for (Ray ray : rays) {
                    // Calculate the distance from the camera to the wall
                    double rayDist = ray.getP1().distance(ray.getP2());

                    // Calculate the height of the wall based on distance
                    double wallHeight = projectionHeight * delta / rayDist;

                    // Calculate the vertical position of the wall on the projection panel
                    double wallY = projectionHeight / 2 - wallHeight / 2;

                    // Calculate the width of the wall segment
                    double wallWidth = projectionWidth / rays.size();

                    // Calculate the left and right coordinates of the wall segment
                    double leftX = rays.indexOf(ray) * wallWidth;
                    double rightX = leftX + wallWidth;

                    // Create a texture paint with the wall texture
                    TexturePaint texturePaint = new TexturePaint(wallTexture, new Rectangle(0, 0, wallTexture.getWidth(), wallTexture.getHeight()));

                    // Set the texture paint for filling the wall segment
                    g2d.setPaint(texturePaint);

                    // Fill the wall segment with the texture
                    g2d.fill(new Rectangle2D.Double(leftX, wallY, wallWidth, wallHeight));
                }
            }
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        project(g2d);
    }
}