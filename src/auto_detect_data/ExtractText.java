package auto_detect_data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import organize_files.DocumentType;

public class ExtractText {

	private ITesseract instance;

	public ExtractText() {
		instance = new Tesseract();

		try {
			instance.setDatapath(new File(".").getCanonicalPath() + "/tessdata");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		instance.setLanguage("por");
	}

	public DocumentInformation readImage(File imageFile) throws IOException, TesseractException {

		BufferedImage image = ImageIO.read(imageFile);

		return dataExtracted(instance.doOCR(image));
	}

	private DocumentInformation dataExtracted(String text) {
		String cleanText = text.toLowerCase().replace("\n", " ");

		DocumentInformation docInfo = new DocumentInformation();
		
		String typeDoc = getTypeDoc(cleanText);
		docInfo.setProtocolo(getProtocolo(cleanText));
		docInfo.setData(getDate(cleanText, typeDoc));
		docInfo.setTipoDocumento(typeDoc);
		docInfo.setOriginalText(text);

		return docInfo;
	}

	private String getProtocolo(String text) {
		String regex = "(\\d{6}.\\d{5}/\\d{4}.\\d{1} | " + "\\d{6}.\\d{5}/\\d{4}-\\d{1} | "
				+ "\\d{3}.\\d{3}-\\d{5}/\\d{4}-\\d{1} | " + "\\d{3}.\\d{3}.\\d{5}/\\d{4}-\\d{1} | "
				+ "\\d{3}.\\d{3}.\\d{5}/\\d{4}.\\d{1})*";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(text);

		String protocolo = "";

		while (matcher.find()) {
			if (matcher.group().contains(".")) {
				protocolo = matcher.group();
				break;
			}
		}
		return protocolo.replace("-", ".").replace("/", ".").trim();
	}

	private String getDateText(String text) {
		String[] months = new String[] {"janeiro" , "fevereiro", 
				"março", "abril", "maio", "junho", "julho", "agosto", 
				"setembro", "outubro", "novembro", "dezembro"};
		
		String regex = "";
		
		for (int i = 0; i < months.length; i++) {
			regex = regex + " | ((0?[1-9]|[12][0-9]|3[01]) de " + months[i] + " de ((?:19|20)[0-9][0-9]))";
		}
		
		regex = "(" + regex.substring(3) + ")*";
		
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(text);
		
		String date = "";
		String finalDate = "";
		
		while (matcher.find()) {
			date = matcher.group().trim();
			if (!date.isEmpty()) {
				break;
			}
		}

		for (int i = 0; i < months.length; i++) {
			if (date.contains(months[i])) {
				String[] dateSplit = date.split(" ");
				String day = String.format("%02d", Integer.parseInt(dateSplit[0]));
				String month = String.format("%02d", (i + 1));
				finalDate = day + "/" + month + "/" + dateSplit[4];
			}
		}

		return finalDate;
	}
	
	private LocalDate getDate(String text, String typeDoc) {
		text = getDateText(text) + text;
		
		String regex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(text);

		String date = null, finalDate = "";
		int year, lastYear = 0;
		int month, lastMonth = 0;
		int day, lastDay = 0;

		while (matcher.find()) {
			date = matcher.group().trim();
			
			if (typeDoc.equals("Férias")) {
				finalDate = date;
				break;
			}

			if (!date.isEmpty()) {
				year = Integer.parseInt(date.split("/")[2]);
				month = Integer.parseInt(date.split("/")[1]);
				day = Integer.parseInt(date.split("/")[0]);
				if (year > lastYear) {
					lastYear = year;
					finalDate = date;
				} else if (year == lastYear) {
					if (month > lastMonth) {
						lastMonth = month;
						finalDate = date;
					} else if (month == lastMonth) {
						if (day > lastDay) {
							lastDay = day;
							finalDate = date;
						}
					}
				}
			}
			
		}
		
		

		if (!finalDate.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate = LocalDate.parse(finalDate, formatter);
			return localDate;
		}
		return null;
	}

	private String getTypeDoc(String text) {
		String regex = "";
		String[] typeDocs = DocumentType.types();

		for (int i = 1; i < typeDocs.length; i++) {
			regex = regex + " | " + typeDocs[i].toLowerCase();
		}
		regex = "(" + regex.substring(3) + ")*";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		String typeDoc = "";

		while (matcher.find()) {
			typeDoc = matcher.group();
			if (!typeDoc.isEmpty()) {
				break;
			}
		}

		int indexTypeDoc = -1;
		for (int i = 1; i < typeDocs.length; i++) {
			if (typeDoc.trim().equals(typeDocs[i].toLowerCase())) {
				indexTypeDoc = i;
				break;
			}
		}

		return indexTypeDoc != -1 ? typeDocs[indexTypeDoc] : "";
	}

}
