package auto_detect_data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

import application.MainScreenController;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import organize_files.DocumentType;

public class ExtractText {

	private static ITesseract instance;

	public ExtractText() {
		instance = new Tesseract();

		instance.setDatapath("C:\\Users\\savio\\Projects\\Java\\Scanned-Document-Organizer\\resources\\tessdata");
		// // path to tessdata directory
		//instance.setDatapath(getClass().getResourceAsStream("/tessdata").toString());

		instance.setLanguage("por");
	}

	public static void readImage(File imageFile) throws IOException, TesseractException {
		
		instance = new Tesseract();

		instance.setDatapath("C:\\Users\\savio\\Projects\\Java\\Scanned-Document-Organizer\\resources\\tessdata");
		// // path to tessdata directory
		//instance.setDatapath(getClass().getResourceAsStream("/tessdata").toString());

		instance.setLanguage("por");
		String result;
		DocumentInformation resultInfo;

		//for (int i = 0; i < imageFile.size(); i++) {
			BufferedImage image = ImageIO.read(imageFile);

			result = instance.doOCR(image);
			resultInfo = dataExtracted(result.toLowerCase().replace("\n", " "));

			MainScreenController.texts.add(result);
			// MainScreenController.originalText.add(result);
		//}

	}

	private static DocumentInformation dataExtracted(String text) {
		DocumentInformation docInfo = new DocumentInformation();
		docInfo.setProtocolo(getProtocolo(text));
		docInfo.setData(getDate(text));
		docInfo.setTipoDocumento(getTypeDoc(text));

		return docInfo;
	}

	private static String getProtocolo(String text) {
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
		return protocolo;
	}

	private static LocalDate getDate(String text) {
		String regex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(text);

		String date, finalDate = "";
		int year, lastYear = 0;
		int month, lastMonth = 0;
		int day, lastDay = 0;

		while (matcher.find()) {
			date = matcher.group().trim();

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

	private static String getTypeDoc(String text) {
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