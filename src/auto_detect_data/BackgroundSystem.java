package auto_detect_data;

import java.io.File;

public class BackgroundSystem implements Runnable {

	private File directory;

	public BackgroundSystem(File directory) {
		this.directory = directory;
	}

	@Override
	public void run() {
		SeparateDocument.files(directory);

	}

}
