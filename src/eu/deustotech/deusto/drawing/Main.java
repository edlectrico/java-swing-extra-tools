package eu.deustotech.deusto.drawing;

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
	
	public static void main(String[] args) {
		MyJFrame frame = new MyJFrame(true, true);
		
		//TODO:
		//1. Show a image in a frame
		try {
			frame.drawBackgroundImage(ImageIO.read(new File(BACKGROUND_IMAGE)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//2. Put the image fullscreen
		//3. resize image
		//4. overlay images
		//5. Change colors
		
//		frame.resizeImage(null, new Dimension(300, 300), true);
		
	}
}
