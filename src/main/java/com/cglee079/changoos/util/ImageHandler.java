package com.cglee079.changoos.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

@Component
public class ImageHandler {
	public static final String EXT_PNG = "png";
	public static final String EXT_GIF = "gif";

	public boolean saveLowscaleImage(File file, int w, String imgExt) {
		try {
			BufferedImage srcImg = ImageIO.read(file);

			int orientation = this.getOrientation(file);
			if (orientation != 1) {
				srcImg = this.rotateImageForMobile(srcImg, orientation);
			}

			int width = srcImg.getWidth();
			int height = srcImg.getHeight();

			if (width < w) {
				return true;
			}

			double ratio = (double) w / (double) width;
			int h = (int) (height * ratio);

			BufferedImage scaledImage = null;
			if (srcImg != null) {
				scaledImage = Scalr.resize(srcImg, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, w, h, null);
				// BufferedImage convertedImg = null;
				// if(imgExt.equalsIgnoreCase(ImageManager.EXT_PNG)) { convertedImg = new
				// BufferedImage( w, h, BufferedImage.TYPE_4BYTE_ABGR); }
				// if(imgExt.equalsIgnoreCase(ImageManager.EXT_JPG)) { convertedImg = new
				// BufferedImage( w, h, BufferedImage.TYPE_3BYTE_BGR); }
				// convertedImg.getGraphics().drawImage(scaledImage, 0, 0, w, h, null);
				// scaledImage = convertedImg;
			}
			ImageIO.write(scaledImage, imgExt, file);

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public int getOrientation(File file) throws IOException {
		int orientation = 1;
		Metadata metadata;
		try {
			metadata = ImageMetadataReader.readMetadata(file);
			ExifIFD0Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
			if (directory != null) {
				if (directory.hasTagName(ExifIFD0Directory.TAG_ORIENTATION)
						&& directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
					orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
				}
			}
		} catch (ImageProcessingException | MetadataException e) {
			e.printStackTrace();
			return 1;
		}

		return orientation;

	}

	public BufferedImage rotateImageForMobile(BufferedImage bi, int orientation) throws IOException {
		if (orientation == 6) {
			return rotateImage(bi, 90);
		} else if (orientation == 1) {
			return bi;
		} else if (orientation == 3) {
			return rotateImage(bi, 180);
		} else if (orientation == 8) {
			return rotateImage(bi, 270);
		} else {
			return bi;
		}
	}

	public BufferedImage rotateImageForMobile(File file, int orientation) throws IOException {
		BufferedImage bi = ImageIO.read(file);
		return rotateImageForMobile(bi, orientation);
	}

	public BufferedImage rotateImage(BufferedImage orgImage, int radians) {
		BufferedImage newImage;
		if (radians == 90 || radians == 270) {
			newImage = new BufferedImage(orgImage.getHeight(), orgImage.getWidth(), orgImage.getType());
		} else if (radians == 180) {
			newImage = new BufferedImage(orgImage.getWidth(), orgImage.getHeight(), orgImage.getType());
		} else {
			return orgImage;
		}
		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.rotate(Math.toRadians(radians), newImage.getWidth() / 2, newImage.getHeight() / 2);
		graphics.translate((newImage.getWidth() - orgImage.getWidth()) / 2,
				(newImage.getHeight() - orgImage.getHeight()) / 2);
		graphics.drawImage(orgImage, 0, 0, orgImage.getWidth(), orgImage.getHeight(), null);

		return newImage;
	}

}
