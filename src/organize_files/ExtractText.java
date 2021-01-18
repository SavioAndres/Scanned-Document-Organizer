package organize_files;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import structure.ResizeImage;

public class ExtractText {
	
	private static String text;
	
	public static void readImage(File imageFile) throws IOException {
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
    		
            text = result.toLowerCase().replace("\n", " ");
        } catch (TesseractException e) {
            e.getMessage();
        }
	}
	
	public static String getText() {
		return text;
	}
	
	public static String regexPortaria() {
		String regex = "(\\d{6}.\\d{5}/\\d{4}.\\d{2} | "
				+ "\\d{6}.\\d{5}/\\d{4}-\\d{2} | "
				+ "\\d{3}.\\d{3}-\\d{5}/\\d{4}-\\d{2} | "
				+ "\\d{3}.\\d{3}.\\d{5}/\\d{4}-\\d{2} | "
				+ "\\d{3}.\\d{3}.\\d{5}/\\d{4}.\\d{2} | "
				+ "\\d{6}.\\d{5}/\\d{4}.\\d{1} | "
				+ "\\d{6}.\\d{5}/\\d{4}-\\d{1} | "
				+ "\\d{3}.\\d{3}-\\d{5}/\\d{4}-\\d{1} | "
				+ "\\d{3}.\\d{3}.\\d{5}/\\d{4}-\\d{1} | "
				+ "\\d{3}.\\d{3}.\\d{5}/\\d{4}.\\d{1} | "
				+ "\\d{3}.\\d{3}-\\d{6}/\\d{4}-\\d{1} | "
				+ "\\d{3}.\\d{3}.\\d{6}/\\d{4}-\\d{1} | "
				+ "\\d{3}.\\d{3}-\\d{6}/\\d{4}-\\d{2} | "
				+ "\\d{3}.\\d{3}.\\d{6}/\\d{4}-\\d{2})*";
		System.out.println(regex);
		//String text = "Em um texto 123.456.65432/1234-12 grande mary@xpto.com.zip.test pode 016.000-23421/1234-1 ser necessário procurar por uma lista de e-mails tom@abc.com and harry@zyx.com";

		Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(text);

        String portaria = "";

        while (matcher.find()) {
        	if (matcher.group().contains(".")) {
        		portaria = matcher.group();
        		break;
        	}
        	//listaEmail += matcher.group() + " ";
        }
        System.out.println(portaria);
		return portaria;
	}
	
	public static LocalDate regexDate() {
		//text = "geh jhf 12/12/2012 rgegrht";
		String regex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])";
		System.out.println(regex);
		//String text = "Em um texto 123.456.65432/1234-12 grande mary@xpto.com.zip.test pode 016.000-23421/1234-1 ser necessário procurar por uma lista de e-mails tom@abc.com and harry@zyx.com";

		Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(text);

        String date = "";
        //ArrayList<String> date = new ArrayList<String>();

        while (matcher.find()) {
        	if (matcher.group().contains("/")) {
        		date = matcher.group();
        		break;
        	}
        	//listaEmail += matcher.group() + " ";
        }
        
        System.out.println("---> " + date);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}
	
	public static String getPortaria() {
		return null;
	}
}
