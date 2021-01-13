package organize_files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

import application.FXMLMainScreenController;
import javafx.scene.image.Image;

public class MoveFile {

	private String folderName;
	private String fileName;
	
	public void MoveFiles(ArrayList<File> fileImages) {
		File directory = FXMLMainScreenController.directory;
		File file = null;
		
		for (int i = 0; i < fileImages.size(); i++) {
			file = fileImages.get(i);
			if (file.isFile()) {
				//System.out.println(directory.getAbsolutePath() + " -- " + fileImages.get(i).getName());
				File newDirectory = new File(directory.getAbsolutePath() + "\\" + folderName);
				if (!newDirectory.exists())
					newDirectory.mkdir();
				
				try {
					Files.move(Paths.get(directory.getAbsolutePath() + "\\" + file.getName()),
							Paths.get(newDirectory.getAbsolutePath() + "\\" + fileName + " " + i + "." + file.getName().substring(file.getName().lastIndexOf(".") + 1)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if (file.isDirectory()) {
				System.out.println("Directory " + file.getName());
			}
		}
	}
	
	public void setData(String date, String portariaEdoc, String docType) {
		folderName = date + " " + portariaEdoc;
		fileName = docType;
	}

}
