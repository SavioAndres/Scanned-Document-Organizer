package organize_files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import application.MainScreenController;

public class MoveFile {

	private String folderName;
	private String fileName;
	private ArrayList<Integer> portariaPages;

	public void MoveFiles(ArrayList<File> fileImages) {
		File directory = MainScreenController.directory;
		File file = null;

		File newDirectory = new File(directory.getAbsolutePath() + "\\" + folderName);
		if (!newDirectory.exists())
			newDirectory.mkdir();

		for (int i = 0; i < fileImages.size(); i++) {
			file = fileImages.get(i);
			if (file.isFile()) {
				// System.out.println(directory.getAbsolutePath() + " -- " +
				// fileImages.get(i).getName());
				String port = "";
				if (portariaPages != null)
					if (portariaPages.contains(i + 1))
						port = "Portaria ";

				try {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					String fileNewName = port + fileName + " " + timestamp.getTime() + i + "."
							+ file.getName().substring(file.getName().lastIndexOf(".") + 1);

					if (i == fileImages.size() - 1) {
						newDirectory = new File(directory.getAbsolutePath() + "\\pretas");
						if (!newDirectory.exists())
							newDirectory.mkdir();
						fileNewName = folderName + " " + fileName + " " + timestamp.getTime() + "."
								+ file.getName().substring(file.getName().lastIndexOf(".") + 1);
					}

					Files.move(Paths.get(directory.getAbsolutePath() + "\\" + file.getName()),
							Paths.get(newDirectory.getAbsolutePath() + "\\" + fileNewName));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (file.isDirectory()) {
				System.out.println("Directory " + file.getName());
			}
		}
	}

	public void setData(LocalDate date, String portariaEdoc, String comuInt, String docType, String portariaPage) {
		if (!portariaEdoc.isEmpty())
			portariaEdoc = " " + portariaEdoc;
		if (!comuInt.isEmpty())
			comuInt = " " + comuInt;

		folderName = date.getYear() + "." + String.format("%02d", date.getMonthValue()) + "."
				+ String.format("%02d", date.getDayOfMonth()) + portariaEdoc + comuInt;
		fileName = docType;
		if (!portariaPage.isEmpty()) {
			String[] result = portariaPage.split(",");
			portariaPages = new ArrayList<Integer>();
			for (int i = 0; i < result.length; i++) {
				portariaPages.add(Integer.parseInt(result[i].trim()));
			}
		}
	}

}
