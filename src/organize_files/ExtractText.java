package organize_files;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import structure.ResizeImage;

public class ExtractText {
	
	private static String text;
	
	public static String readImage(File imageFile) throws IOException {
		long tempoInicio = System.currentTimeMillis();
		//File imageFile = new File("C:\\Users\\savio\\Pictures\\20170606_153524.jpg");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        instance.setDatapath("C:\\Users\\savio\\Projects\\Java\\Scanned-Document-Organizer\\tessdata"); // path to tessdata directory
        instance.setLanguage("por");

        try {
        	//instance.doOCR(imageFile)imageFile.;
        	
		    
		    BufferedImage image = ResizeImage.resize(imageFile, 2);
			
        	
        	
            String result = instance.doOCR(image);
            
            long dif = System.currentTimeMillis() - tempoInicio;
    		System.out.println("Tempo Total: "+(String.format("%02d segundos  e %02d milisegundos", dif/1000, dif%1000)));
    		
            text = result;
            return result;
        } catch (TesseractException e) {
            e.getMessage();
        }
		return text;
	}
	
	private void regex() {
		
	}
	
	public static String getPortaria() {
		return null;
	}
}
