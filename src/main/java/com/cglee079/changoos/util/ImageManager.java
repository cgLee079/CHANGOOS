package com.cglee079.changoos.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;

public class ImageManager {
	public static final String EXT_JPG = "jpg";
	public static final String EXT_PNG = "png";
	
	public synchronized static Image fileToImage(File file) throws IOException{
		Image image = ImageIO.read(file);
		return image;
	}
	
	public synchronized static BufferedImage getLowScaledImage(File file, int h , String imgExt) throws IOException {
		BufferedImage srcImg = ImageIO.read(file);
		int width = srcImg.getWidth();
		int height = srcImg.getHeight();
		
		if(height < h) { return srcImg; }
		
		double ratio = (double)h / (double)height;
		int w = (int)(width * ratio);
		
		BufferedImage destImg =  null;
		if(imgExt.equalsIgnoreCase(ImageManager.EXT_PNG)) { destImg = new BufferedImage( w, h, BufferedImage.TYPE_4BYTE_ABGR); }
		if(imgExt.equalsIgnoreCase(ImageManager.EXT_JPG)) { destImg = new BufferedImage( w, h, BufferedImage.TYPE_3BYTE_BGR); }

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
	
    public synchronized static int getOrientation(File file) throws IOException, MetadataException, ImageProcessingException {
        int orientation = 1;
	    Metadata metadata = ImageMetadataReader.readMetadata(file);
	    ExifIFD0Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
	    if(directory != null){
	    	if(directory.hasTagName(ExifIFD0Directory.TAG_ORIENTATION) && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)){
	    		orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
	    	}
	    } 
	    return orientation;
        
    }
    
    public synchronized static BufferedImage rotateImageForMobile(BufferedImage bi, int orientation) throws IOException {
		if(orientation == 6){
		        return rotateImage(bi, 90);
		} else if (orientation == 1){
		        return bi;
		} else if (orientation == 3){
		        return rotateImage(bi, 180);
		} else if (orientation == 8){
		        return rotateImage(bi, 270);      
		} else{
		        return bi;
		}       
    }
    
    public synchronized static BufferedImage rotateImageForMobile(File file, int orientation) throws IOException {
    	BufferedImage bi = ImageIO.read(file);
    	return rotateImageForMobile(bi, orientation);
    }
    
    public synchronized static BufferedImage rotateImage(BufferedImage orgImage,int radians) {
		BufferedImage newImage;
		 if(radians==90 || radians==270){
		       newImage = new BufferedImage(orgImage.getHeight(), orgImage.getWidth(), orgImage.getType());
		} else if (radians==180){
		       newImage = new BufferedImage(orgImage.getWidth(), orgImage.getHeight(), orgImage.getType());
		} else{
		       return orgImage;
		}
		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.rotate(Math. toRadians(radians), newImage.getWidth() / 2, newImage.getHeight() / 2);
		graphics.translate((newImage.getWidth() - orgImage.getWidth()) / 2, (newImage.getHeight() - orgImage.getHeight()) / 2);
		graphics.drawImage(orgImage, 0, 0, orgImage.getWidth(), orgImage.getHeight(), null );
		
		return newImage;	
    }
    
    public synchronized static String getExt(String filename){
    	int pos = filename.lastIndexOf( "." );
    	String ext = filename.substring( pos + 1 );
    	if(ext.equalsIgnoreCase(ImageManager.EXT_JPG)) { return ImageManager.EXT_JPG; };
    	if(ext.equalsIgnoreCase(ImageManager.EXT_PNG)) { return ImageManager.EXT_PNG; };
    	return ImageManager.EXT_JPG;
    }
}
