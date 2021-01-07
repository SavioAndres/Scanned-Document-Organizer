package organize_files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MoveFile {

	public void MoveFiles(File directory) {
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				
				System.out.println(directory.getAbsolutePath() + " -- " + files[i].getName());
				File newDirectory = new File(directory.getAbsolutePath() + "\\arq");
				if (!newDirectory.exists()){
					newDirectory.mkdir();
				}
				try {
					Path temp = Files.move(Paths.get(directory.getAbsolutePath() + "\\" + files[i].getName()),
							Paths.get(newDirectory.getAbsolutePath() + "\\" + files[i].getName()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if (files[i].isDirectory()) {
				System.out.println("Directory " + files[i].getName());
			}
		}
	}

}
