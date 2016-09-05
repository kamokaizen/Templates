package com.example.springwebtemplate.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

/*
 * @author kamo
 *
 */
public class ImageUtil {

	private static final int IMG_WIDTH = 200;
	private static final int IMG_HEIGHT = 200;

	public static byte[] resizeImage(InputStream stream) throws IOException {
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Thumbnails.of(stream).size(IMG_WIDTH, IMG_HEIGHT).outputFormat("png").toOutputStream(baos);
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] toByteArray(InputStream stream){
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Thumbnails.of(stream).scale(1).outputFormat("png").toOutputStream(baos);
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] resizeImageWithWidthAndHeight(InputStream stream,int width, int height) throws IOException {
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Thumbnails.of(stream).size(width, height).outputFormat("png").toOutputStream(baos);
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] getOnePixelImageAsTransparent(){
		byte[] imageInByte = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try{
			BufferedImage singlePixelImage = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		    Color transparent = new Color(0, 0, 0, 0);
		    singlePixelImage.setRGB(0, 0, transparent.getRGB());
		    ImageIO.write( singlePixelImage, "jpg", baos );
		    baos.flush();
		    imageInByte = baos.toByteArray();
		    baos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return imageInByte;	    
	}
}