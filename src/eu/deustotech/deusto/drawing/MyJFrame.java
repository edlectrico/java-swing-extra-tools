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
	private static final String FOREGROUND_IMAGE = "img/circle.png"; // Foreground
	private static final String RESULT = "img/result.png"; // Result image file
	private static final String FORMAT_PNG = "PNG";

	private Image image;
	private ImageIcon imageIcon;

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

		// overlayImages();

//		image = Toolkit.getDefaultToolkit().getImage(RESULT);
//		// image = resizeImage(image, new Dimension(300,
//		// 300), true);
//		imageIcon = new ImageIcon(image);
//
//		JLabel label = new JLabel("");
//		label.setIcon(imageIcon);
//		//
//		getContentPane().add(label, BorderLayout.CENTER);
//
//		this.setVisible(visible);
	}

	/**
	 * Given a BufferdImage this method draws it over this JFrame and generates
	 * a result.png file
	 * 
	 * @param backgroundImage
	 * @throws IOException
	 */
	public void drawImage(BufferedImage backgroundImage) throws IOException {
		try {
			Graphics graphics = backgroundImage.getGraphics();
			graphics.drawImage(backgroundImage, 0, 0, null);

			ImageIO.write(backgroundImage, FORMAT_PNG, new File(RESULT));
			
			//now showing the image into the JFrame
			Image image = Toolkit.getDefaultToolkit().getImage(RESULT);
			ImageIcon imageIcon = new ImageIcon(image);

			JLabel label = new JLabel("");
			label.setIcon(imageIcon);
			
			getContentPane().add(label, BorderLayout.CENTER);

			setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method resizes the given image using Image.SCALE_SMOOTH.
	 * 
	 * @param image
	 * @param dimension
	 * @param max
	 * @return the resized image
	 */
	public void resizeImage(Image image, Dimension dimension, boolean max) {
		if (image == null) {
			image = Toolkit.getDefaultToolkit().getImage(RESULT);
		}

//		if (dimension.getWidth() < 0 && dimension.getHeight() > 0) {
//			return resizeImageBy(image, (int) dimension.getHeight(), false);
//		} else if (dimension.getWidth() > 0 && dimension.getHeight() < 0) {
//			return resizeImageBy(image, (int) dimension.getWidth(), true);
//		} else if (dimension.getWidth() < 0 && dimension.getHeight() < 0) {
//			return image;
//		}
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

		try {
			drawImage(bufferedResizedImage);
		} catch (IOException e) {
			e.printStackTrace();
		}

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
	 * Overlays several images over a background image
	 * 
	 * @param positions
	 *            where foreground images will be drawn
	 * @param colorFilters
	 *            several filters to be applied to the foreground images
	 */
	public void overlayImages() {
		BufferedImage backgroundImage = null;
		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE));

			BufferedImage foregroundImage = null;

			foregroundImage = ImageIO.read(new File(FOREGROUND_IMAGE));

			int w = Math.max(backgroundImage.getWidth(),
					foregroundImage.getWidth());
			int h = Math.max(backgroundImage.getHeight(),
					foregroundImage.getHeight());

			BufferedImage combined = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_ARGB);

			// paint both images, preserving the alpha channels
			Graphics graphics = combined.getGraphics();
			graphics.drawImage(backgroundImage, 0, 0, null);

//			overlayImages(foregroundImage, graphics);

			ImageIO.write(combined, FORMAT_PNG, new File(RESULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Draws a circle taking into account the probability/accuracy of each
	 * position to calculate its radius (width, height)
	 * 
	 * @param locations
	 * @param foregroundImage
	 * @param Graphics
	 *            g
	 * @param int i (position in the HashMap)
	 * @param locationFeature
	 *            (ACCURACY | PROBABILITY)
	 */
	/*
	private void overlayImages(BufferedImage foregroundImage, Graphics g) {
		Image resizedForegroundImage = null;

		int width = 350;
		int height = 350;

		resizedForegroundImage = resizeImage(Toolkit.getDefaultToolkit()
				.getImage(FOREGROUND_IMAGE), new Dimension(width, height), true);

		BufferedImage filteredImage = imageToBufferedImage(
				resizedForegroundImage, width, height);
		BufferedImage filteredWithTextImage = addTextToBufferedImage(
				filteredImage, "TEXT");
		g.drawImage(filteredWithTextImage, 500, 500, null);
	}
*/
	/**
	 * Converts a Image object into a BufferedImage object
	 * 
	 * @param im
	 * @param width
	 * @param heigth
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
	 */
	private BufferedImage addTextToBufferedImage(BufferedImage src, String text) {
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
		return img;
	}

}
