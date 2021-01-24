package auto_detect_data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import net.sourceforge.tess4j.TesseractException;

public class BackgroundSystem implements Runnable {

	private File directory;
	private ArrayList<File> selectedFiles;

	public BackgroundSystem(File directory) {
		this.directory = directory;
		selectedFiles = new ArrayList<File>();
	}

	@Override
	public void run() {
		//SeparateDocument separateDocument = new SeparateDocument();
		SeparateDocument.files(directory);
		// ExtractText extractText = new ExtractText();
		

	}

}
