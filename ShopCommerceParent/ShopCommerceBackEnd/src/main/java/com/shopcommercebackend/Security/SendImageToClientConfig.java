package com.shopcommercebackend.Security;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SendImageToClientConfig implements WebMvcConfigurer{

	// xu li de tai anh len client :D
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "user-photos";
		Path userPhoto = Paths.get(dirName);
		
		String userPhotosPath = userPhoto.toFile().getAbsolutePath();
		
		String dirNameCategory= "../category-images";
		Path categoryPhoto = Paths.get(dirNameCategory);
		String catgoryPhotoPath = categoryPhoto.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/"+dirName+"/**").addResourceLocations("file:/" +userPhotosPath + "/");
		registry.addResourceHandler("/"+dirNameCategory+"/**").addResourceLocations("file:/" + catgoryPhotoPath+"/");
	}
	
	

	
}
