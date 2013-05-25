package eu.deustotech.deusto.drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyJFrame extends JFrame {

	/**
	 * default generated ID
	 */
	private static final long serialVersionUID = 1L;

	private static final String BACKGROUND_IMAGE = "img/plan.png"; // Background
	private static final String RESULT = "img/result.png"; // Result image file
	private static final String FORMAT_PNG = "PNG";

	/**
	 * Constructor. It creates a JFrame with an Image on it taking into account
	 * the fullscreen and undecorated parameters.
	 * 
	 * @param fullscreen
	 * @param undecorated
	 * @throws IOException
	 */
	public MyJFrame(boolean fullscreen, boolean undecorated) {
		super("@Test");

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setUndecorated(undecorated);

		if (fullscreen) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setBounds(0, 0, screenSize.width, screenSize.height);
		}
	}

	/**
	 * Given a BufferdImage this method draws it over this JFrame and generates
	 * a result.png file
	 * 
	 * @param backgroundImage
	 * @throws IOException
	 */
	public void drawImage(BufferedImage backgroundImage) throws IOException {
		Graphics graphics = backgroundImage.getGraphics();
		graphics.drawImage(backgroundImage, 0, 0, null);

		ImageIO.write(backgroundImage, FORMAT_PNG, new File(RESULT));

		// now showing the image into the JFrame
		Image image = Toolkit.getDefaultToolkit().getImage(RESULT);
		ImageIcon imageIcon = new ImageIcon(image);

		JLabel label = new JLabel("");
		label.setIcon(imageIcon);

		getContentPane().add(label, BorderLayout.CENTER);

		setVisible(true);
	}

	/**
	 * This method resizes the given image using Image.SCALE_SMOOTH.
	 * 
	 * @param image
	 * @param dimension
	 * @param max
	 * @throws IOException
	 */
	public void resizeImage(Image image, Dimension dimension, boolean max)
			throws IOException {
		if (image == null) {
			image = Toolkit.getDefaultToolkit().getImage(RESULT);
		}

		int currentHeight = image.getHeight(null);
		int currentWidth = image.getWidth(null);
		int expectedWidth = (int) (dimension.getHeight() * currentWidth)
				/ currentHeight;

		int size = (int) dimension.getHeight();
		if (max && expectedWidth > dimension.getWidth()) {
			size = (int) dimension.getWidth();
		} else if (!max && expectedWidth < dimension.getWidth()) {
			size = (int) dimension.getWidth();
		}
		final Image resizedImage = resizeImageBy(image, size,
				(size == dimension.getWidth()));

		final BufferedImage bufferedResizedImage = imageToBufferedImage(
				resizedImage, (int) dimension.getWidth(),
				(int) dimension.getHeight());

		drawImage(bufferedResizedImage);
	}

	/**
	 * Resizes the given image using Image.SCALE_SMOOTH.
	 * 
	 * @param image
	 * @param size
	 * @param setWidth
	 * @return the resized image
	 */
	private Image resizeImageBy(Image image, int size, boolean setWidth) {
		if (setWidth) {
			return image.getScaledInstance(size, -1, Image.SCALE_SMOOTH);
		} else {
			return image.getScaledInstance(-1, size, Image.SCALE_SMOOTH);
		}
	}

	/**
	 * Overlays the BufferedImage received by parameter over a background image
	 * 
	 * @param foregroundImage
	 * @param posX
	 * @param posY
	 * @throws IOException
	 */
	public void overlayImage(BufferedImage foregroundImage, int posX, int posY)
			throws IOException {
		BufferedImage backgroundImage = null;
		backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE));

		int w = Math
				.max(backgroundImage.getWidth(), foregroundImage.getWidth());
		int h = Math.max(backgroundImage.getHeight(),
				foregroundImage.getHeight());

		BufferedImage combined = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics graphics = combined.getGraphics();
		graphics.drawImage(backgroundImage, 0, 0, null);

		overlayImages(foregroundImage, graphics, posX, posY);

		ImageIO.write(combined, FORMAT_PNG, new File(RESULT));

		final Image image = Toolkit.getDefaultToolkit().getImage(RESULT);

		final BufferedImage bufferedImage = imageToBufferedImage(image,
				image.getWidth(null), image.getHeight(null));

		drawImage(bufferedImage);
	}

	private void overlayImages(BufferedImage foregroundImage, Graphics g,
			int posX, int posY) {
		g.drawImage(foregroundImage, posX, posY, null);
	}

	/**
	 * Converts a Image object into a BufferedImage object
	 * 
	 * @param im
	 * @param width
	 * @param heigth
	 * 
	 * @return a BufferedImage from a Image parameter
	 */
	private BufferedImage imageToBufferedImage(Image im, int width, int height) {
		ImageIcon icon = new ImageIcon(im);
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();

		GraphicsConfiguration gc = getGraphicsConfiguration();
		BufferedImage bi = gc.createCompatibleImage(w, h,
				Transparency.TRANSLUCENT);
		Graphics2D g = bi.createGraphics();
		icon.paintIcon(this, g, 0, 0);
		g.dispose();

		return bi;
	}

	/**
	 * Overlays a String text message over a BufferedImage
	 * 
	 * @param src
	 * @param text
	 * @return A BufferedImage with a text over it
	 * @throws IOException
	 */
	public void drawTextOverImage(BufferedImage src, String text)
			throws IOException {
		int w = src.getWidth();
		int h = src.getHeight();
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(src, 0, 0, null);
		g2d.setPaint(Color.red);
		g2d.setFont(new Font("Serif", Font.BOLD, 20));
		FontMetrics fm = g2d.getFontMetrics();

		int x = img.getWidth() - fm.stringWidth(text) - 5;
		int y = fm.getHeight();

		g2d.drawString(text, x, y);
		g2d.dispose();

		drawImage(img);
	}

}
