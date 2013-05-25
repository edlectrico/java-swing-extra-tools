package eu.deustotech.deusto.drawing;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {
	
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static final String BACKGROUND_IMAGE = "img/plan.png"; // Background
	private static final String RESULT = "img/result.png"; // Result image file
	
	public static void main(String[] args) throws IOException {
		MyJFrame frame = new MyJFrame(true, true);
		
		BufferedImage bufferedImage = ImageIO.read(new File(BACKGROUND_IMAGE));
		
		if (bufferedImage != null){
			//1. Show a image in a frame
			frame.drawImage(bufferedImage);
			
//			Image image = Toolkit.getDefaultToolkit().getImage(RESULT);
//			ImageIcon imageIcon = new ImageIcon(image);
//
//			JLabel label = new JLabel("");
//			label.setIcon(imageIcon);
//			//
//			frame.getContentPane().add(label, BorderLayout.CENTER);
//
//			frame.setVisible(true);
			
			
			
			//2. resize image
//			frame.resizeImage(bufferedImage, new Dimension(300, 300), true);
			
			
		}
		
		//TODO:
		//4. overlay images
		//5. Change colors
		
//		frame.resizeImage(null, new Dimension(300, 300), true);
		
	}
}
