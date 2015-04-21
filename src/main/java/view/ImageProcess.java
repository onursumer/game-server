package view;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Class that contains static methods for image manipulation operations.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ImageProcess
{
	/**
	 * Creates an image icon for the given filename, and resizes the icon 
	 * to the given width and height. Returns the scaled image icon.
	 * 
	 * @param filename	source filename
	 * @param width		width to scale
	 * @param height	height to scale
	 * @return			scaled ImageIcon
	 */
	public static ImageIcon scaleIcon(String filename, 
			int width,
			int height)
	{
		// try to generate a new image from the game name
		ImageIcon icon = new ImageIcon(filename);
		
		// scale image to the given dimensions
		Image scaled = icon.getImage().getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		
		// return scaled image icon
		return new ImageIcon(scaled);
	}
}
