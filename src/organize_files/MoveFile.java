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

	public void MoveFiles(ArrayList<File> fileImages) {
		File directory = FXMLMainScreenController.directory;
		
		for (int i = 0; i < fileImages.size(); i++) {
			if (fileImages.get(i).isFile()) {
				System.out.println(directory.getAbsolutePath() + " -- " + fileImages.get(i).getName());
				File newDirectory = new File(directory.getAbsolutePath() + "\\arq");
				if (!newDirectory.exists())
					newDirectory.mkdir();
				
				try {
					Files.move(Paths.get(directory.getAbsolutePath() + "\\" + fileImages.get(i).getName()),
							Paths.get(newDirectory.getAbsolutePath() + "\\" + fileImages.get(i).getName()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if (fileImages.get(i).isDirectory()) {
				System.out.println("Directory " + fileImages.get(i).getName());
			}
		}
	}

}
