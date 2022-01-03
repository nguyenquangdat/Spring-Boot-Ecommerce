package com.shopcommercebackend.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopcommercebackend.FileUploadUtil;
import com.shopcommercebackend.service.BrandService;
import com.shopcommercebackend.service.ProductService;
import com.shopcommercecommon.model.Brand;
import com.shopcommercecommon.model.Product;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	
	@GetMapping("/products")
	public String listProduct(Model model) {
		List<Product> products = productService.getAllProduct();
		model.addAttribute("products", products);
		return "products";
	}
	
	@GetMapping("/products/new")
	public String informationForm(Model model) {
		List<Brand> brands = brandService.getAllBrand();
		Product product = new Product();
		product.setEnabled(true);
		product.setInStock(true);
		model.addAttribute("product", product);
		model.addAttribute("brands", brands);
		return "product_form";
	}
	
	@PostMapping("/products/create")
	public String saveProduct (Product product , RedirectAttributes re ,
			@RequestParam("fileImage") MultipartFile mainImageMultipartFIle,
			@RequestParam("extraImage") MultipartFile[] extraImagemultipartFile,
			@RequestParam(name = "detailNames", required = false) String [] detailNames,
			@RequestParam(name = "detailValues", required = false) String [] detailValues
			) throws IOException {
		
		  setMainImageName(mainImageMultipartFIle, product);	  
		  setExtraImages(extraImagemultipartFile,product);
		  setProductDetails(detailNames,detailValues,product);
		  
		  Product saveProduct = productService.saveProduct(product);
		  
		  saveUploadImages(mainImageMultipartFIle,extraImagemultipartFile ,saveProduct);
				
				
		 
		re.addFlashAttribute("message", "The product has been saved successful");
		return "redirect:/products";
	}
	


	private void setProductDetails(String[] detailNames, String[] detailValues, Product product) {
		if(detailNames ==null || detailNames.length ==0) return;
		
		for(int i =0 ; i < detailNames.length ; i++) {
			String name = detailNames[i];
			String value = detailValues[i];
			if(!name.isEmpty() && !value.isEmpty()) {
				product.addProductDetial(name, value);
			}
		}
		
	}

	private void saveUploadImages(MultipartFile mainImageMultipartFIle, MultipartFile[] extraImagemultipartFile,
			Product saveProduct) throws IOException {
		if ( !mainImageMultipartFIle.isEmpty()) {
			String fileName = mainImageMultipartFIle.getOriginalFilename();
			String uploadDri = "../product-images/"+saveProduct.getId();  // de cung hang vs shoppmeBackend and fontEnd vi ca 2 cung dung
			FileUploadUtil.cleanFile(uploadDri);
			FileUploadUtil.saveFile(uploadDri, fileName, mainImageMultipartFIle);
		}
 
		if(extraImagemultipartFile.length >0) {
			
			String uploadDri = "../product-images/"+saveProduct.getId() + "/extras";
			for( MultipartFile multipartFile : extraImagemultipartFile) {
				if(!multipartFile.isEmpty()) {
					String fileName = multipartFile.getOriginalFilename();
					FileUploadUtil.saveFile(uploadDri, fileName, multipartFile);
				}
			}
	}
}

	private void setExtraImages(MultipartFile[] extraImagemultipartFile, Product product) {
		if(extraImagemultipartFile.length >0) {
			for( MultipartFile multipartFile : extraImagemultipartFile) {
				if(!multipartFile.isEmpty()) {
					String fileName = multipartFile.getOriginalFilename();
					product.addExtraImage(fileName);
				}
			}
		}
		
	}

	private void setMainImageName(MultipartFile mainImageMultipartFIle, Product product) {
		if(!mainImageMultipartFIle.isEmpty()) {
	    	String fileName = mainImageMultipartFIle.getOriginalFilename();
			product.setMainImage(fileName);
		}
	}
	
	@GetMapping("product/{id}/enable/{enable}")
	public String updateEnable(@PathVariable(name = "id") Integer id ,
			@PathVariable(name = "enable") boolean enable , RedirectAttributes redirectAttributes ) {
		productService.updateEnableProduct(id, enable);
		if(enable == true) {
			redirectAttributes.addFlashAttribute("message", "changed to true");
		}
		else {
			redirectAttributes.addFlashAttribute("message", "changed to false");
		}
		return "redirect:/products";
	}
	
	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id, 
			RedirectAttributes redirectAttributes) {
		productService.deleteProduct(id);
		String productExtraImages = "../product-images/"+id+ "/extras";
		String productImages = "../product-images/"+id;
		FileUploadUtil.deleteCategoryDriect(productExtraImages);
		FileUploadUtil.deleteCategoryDriect(productImages);
			redirectAttributes.addFlashAttribute("message", "The product has been deleted  succesfull");
		
		return "redirect:/products";
	}
	
	@GetMapping("/product/edit/{id}")
	public String editProduct(@PathVariable("id") Integer id , Model model) {
		Product product = productService.getProductById(id);
		List<Brand> brands = brandService.getAllBrand();
		
		
		model.addAttribute("product", product);
		model.addAttribute("brands",brands);
		model.addAttribute("pageTitle","Edit Product");
		
		
		return "product_form";
	}
}

