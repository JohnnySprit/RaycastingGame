package RaycastingGame;

public class RaycasterProjectionPanel_Textures {
/*
    private BufferedImage wallTexture; // Texture for the walls




    public RaycasterProjectionPanel(final RaycasterRunner raycasterRunner, final RaycasterPanel raycasterPanel) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RAYCASTER_PANEL = raycasterPanel;
        try {
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
                int projectionWidth = getWidth();
                int projectionHeight = getHeight();
                double delta = 40.0;


                for (Ray ray : rays) {
                    double rayDist = ray.getP1().distance(ray.getP2());

                    double wallHeight = projectionHeight * delta / rayDist;

                    double wallY = projectionHeight / 2 - wallHeight / 2;

                    double wallWidth = projectionWidth / rays.size();

                    double leftX = rays.indexOf(ray) * wallWidth;
                    double rightX = leftX + wallWidth;

                    TexturePaint texturePaint = new TexturePaint(wallTexture, new Rectangle(0, 0, wallTexture.getWidth(), wallTexture.getHeight()));

                    g2d.setPaint(texturePaint);

                    g2d.fill(new Rectangle2D.Double(leftX, wallY, wallWidth, wallHeight));
                }
            }
        }
    }
*/

}
