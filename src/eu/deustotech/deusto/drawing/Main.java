package eu.deustotech.deusto.drawing;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		MyJFrame frame = new MyJFrame(true);
		
		//TODO:
		//1. Show a image in a frame
		//2. Put the image fullscreen
		//3. resize image
		//4. overlay images
		
//		frame.resizeImage(null, new Dimension(300, 300), true);
		
		frame.setVisible(true);
	}
}
