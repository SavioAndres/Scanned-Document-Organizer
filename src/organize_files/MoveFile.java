package organize_files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import application.MainScreenController;
import javafx.scene.control.ChoiceDialog;
import structure.Format;

public class MoveFile {

	private String folderName;
	private String fileName;
	private ArrayList<Integer> portariaPages;
	private boolean OldFile = false;

	public void MoveFiles(ArrayList<File> fileImages) {
		File directory = MainScreenController.directory;
		File file = null;
		
		File newDirectory = null;
		File newDirectoryFolder = createDirectory(directory, folderName);
		File newDirectoryBlack = createDirectory(directory, "pretas");
		File newDirectoryOld = createDirectory(directory, "arquivo");

		for (int i = 0; i < fileImages.size(); i++) {
			file = fileImages.get(i);

			try {
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				String fileNewName;
				String fileExtension = "." + file.getName().substring(file.getName().lastIndexOf(".") + 1);
				
				if (i == fileImages.size() - 1 || i == fileImages.size() - 2) {
					OldFile = false;
					newDirectory = newDirectoryBlack;
					fileNewName = folderName + " " + fileName + timestamp.getTime() + i + fileExtension;
				} else if (OldFile) {
					newDirectory = newDirectoryOld;
					fileNewName = timestamp.getTime() + i + fileExtension;
				} else {
					newDirectory = newDirectoryFolder;
					fileNewName = fileNewNamePortaria(fileName, i + 1, file) + timestamp.getTime() + i + fileExtension;
				}
				
				Files.move(Paths.get(directory.getAbsolutePath() + "\\" + file.getName()),
						Paths.get(newDirectory.getAbsolutePath() + "\\" + fileNewName));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String fileNewNamePortaria(String fileNewName, int num, File file) {
		if (portariaPages != null)
			if (portariaPages.contains(num))
				fileNewName = adialog(file, num) + " ";
		return fileNewName;
	}
	
	private String adialog(File file, int num) {
		DocumentType.subTypes("Portaria");
		String[] choices = DocumentType.itens;

		ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
		dialog.setTitle("Tipo de Portaria");
		dialog.setHeaderText("Que portaria é a página " + num + "?\nImagem: " + file.getName());
		dialog.setContentText("Portaria");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    return "Portaria " + result.get();
		}
		
		return "Portaria";
	}

	public void setData(LocalDate date, String protocoloEdoc, String comuInt, String docType, String portariaPage) {
		if (!protocoloEdoc.isEmpty()) {
			protocoloEdoc = Format.protocolo(protocoloEdoc);
			protocoloEdoc = protocoloEdoc.replace("/", ".").replace("-", ".");
			protocoloEdoc = " " + protocoloEdoc;
		}
		if (!comuInt.isEmpty())
			comuInt = " " + Format.comunicaoInterna(comuInt, date.getYear() + "");

		folderName = date.getYear() + "." + String.format("%02d", date.getMonthValue()) + "."
				+ String.format("%02d", date.getDayOfMonth()) + protocoloEdoc + comuInt;
		fileName = docType + " ";
		if (!portariaPage.isEmpty()) {
			String[] result = portariaPage.split(",");
			portariaPages = new ArrayList<Integer>();
			for (int i = 0; i < result.length; i++) {
				portariaPages.add(Integer.parseInt(result[i].trim()));
			}
		}
	}
	
	public void setDataOldFiles() {
		OldFile = true;
		folderName = "arquivo";
		fileName = "";
	}
	
	private File createDirectory(File directory, String name) {
		File newDirectory = new File(directory.getAbsolutePath() + "\\" + name);
		if (!newDirectory.exists())
			newDirectory.mkdir();
		
		return newDirectory;
	}

}
