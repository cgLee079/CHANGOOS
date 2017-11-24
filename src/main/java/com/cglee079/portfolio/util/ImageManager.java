package com.cglee079.portfolio.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ImageManager {
	public synchronized static Image fileToImage(File file) throws IOException{
		Image image = ImageIO.read(file);
		return image;
	}
	
	public synchronized static BufferedImage getScaledImage(File file, int h) throws IOException {
		BufferedImage srcImg = ImageIO.read(file);
		int width = srcImg.getWidth();
		int height = srcImg.getHeight();
		double ratio = (double)h / (double)height;
		int w = (int)(width * ratio);
	    BufferedImage destImg = new BufferedImage( w, h, BufferedImage.TYPE_3BYTE_BGR);
	    Graphics2D g = destImg.createGraphics();
	    g.drawImage(srcImg, 0, 0, w, h, null);
	    return destImg;
	}    
	
	public synchronized static HashMap<String, String> getImageMetaData(File file) throws ImageProcessingException, IOException{
		final String sep1 = "\\]";
		final String sep2 = "\\-";
		HashMap<String, String> map = new HashMap<String, String>();
		String sepeartor[] = null;
		String key;
		String value;
		
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		for (Directory directory : metadata.getDirectories()) {
		    for (Tag tag : directory.getTags()) {
		    	sepeartor = tag.toString().split(sep1);
		    	sepeartor = sepeartor[1].split(sep2);
		    	key = sepeartor[0].trim();
		    	value = sepeartor[1].trim();
		    	map.put(key, value);
		    }
		}
		return map;
	}
}
