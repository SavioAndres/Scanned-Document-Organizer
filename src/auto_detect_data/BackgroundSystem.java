package auto_detect_data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.sourceforge.tess4j.TesseractException;

public class BackgroundSystem implements Runnable {

	private File directory;
	private ArrayList<File> selectedFiles;
	private ArrayList<DocumentInformation> dataExtractedText;
	private ArrayList<String> originalText;
	
	public BackgroundSystem(File directory) {
		this.directory = directory;
		selectedFiles = new ArrayList<File>();
		dataExtractedText = new ArrayList<DocumentInformation>();
		originalText = new ArrayList<String>();
	}
	
	@Override
	public void run() {
		SeparateDocument separateDocument = new SeparateDocument();
		selectedFiles = separateDocument.files(directory);
		ExtractText extractText = new ExtractText();
		try {
			extractText.readImage(selectedFiles);
			originalText = extractText.getOriginalText();
			dataExtractedText = extractText.getDataText();
		} catch (IOException | TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<DocumentInformation> getDataText() {
		return dataExtractedText;
	}
	
	public ArrayList<String> getOriginalText() {
		return originalText;
	}

}
