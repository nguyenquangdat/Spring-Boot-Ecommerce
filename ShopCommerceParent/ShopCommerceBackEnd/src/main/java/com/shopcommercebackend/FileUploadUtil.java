package com.shopcommercebackend;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

// tao user-photos va them anh vao khi add - update :D
public class FileUploadUtil {

	public static void saveFile(String uploadDri, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDri);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream inputStream =multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException ex) {
			throw new IOException("coundn't save file"+fileName,ex);
		}
	}
	
	public static void deleteCategoryDriect(String categoriDirect) {
		cleanFile(categoriDirect);
		try {
			Files.delete(Paths.get(categoriDirect));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void cleanFile(String Direct) {
		Path path = Paths.get(Direct);
		
		try {
			Files.list(path).forEach(x ->{
				if(Files.isDirectory(path)) {
					try {
						Files.delete(x);
					} catch (IOException e) {
						System.out.println("can't remove");
					}
				}
			});
		} catch (IOException e) {
			System.out.println("not found file");
		}
	}
}
