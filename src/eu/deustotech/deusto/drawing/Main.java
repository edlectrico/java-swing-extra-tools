package eu.deustotech.deusto.drawing;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main extends JFrame {
	
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static final String BACKGROUND_IMAGE = "img/plan.png"; // Background
	private static final String FOREGROUND_IMAGE = "img/circle.png"; // Foreground
	
	public static void main(String[] args) throws IOException {
		MyJFrame frame = new MyJFrame(true, true);
		
		BufferedImage bufferedImage = ImageIO.read(new File(BACKGROUND_IMAGE));
		BufferedImage foregroundImage = ImageIO.read(new File(FOREGROUND_IMAGE));
		
		//Leave uncommented just the method you want to test.
		if (bufferedImage != null){
			//1. Show a image in a frame
			frame.drawImage(bufferedImage);
			
			//2. resize image
			frame.resizeImage(bufferedImage, new Dimension(300, 300), true);
			
			//3. overlay images
			frame.overlayImage(foregroundImage, 500, 500);
			
			//4. draw text over image
//			frame.drawTextOverImage(bufferedImage, "Text");
		}
		
		//TODO:
		//5. change image colors
		
	}
}
