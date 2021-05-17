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
}
