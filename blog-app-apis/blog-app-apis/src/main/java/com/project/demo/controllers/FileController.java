package com.project.demo.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.demo.payload.FileResponse;
import com.project.demo.services.FileService;

@RestController
@RequestMapping("/api/file")
public class FileController {
	
	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(
			@RequestParam("image") MultipartFile image
			){
		String fileName;
		
		try {
		
			fileName = this.fileService.uploadImage(path, image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<FileResponse>(new FileResponse(null, "Image is not uploaded due to error in server."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<FileResponse>(new FileResponse(fileName, "Image is successfully uploaded."), HttpStatus.OK);
	}
	
	//method to serve file
	
	/*
	 * 
	 * Generally the output of a controller is in form of a response entity but when the output can't be a response entity such as in case of a binary file or a ZIP 
	 * file then we have to use a more fine way to return response to the client this method is by passing HttpServletResponse object in the controller.
	 * 
	 * 
	 * EXPLANATION TO WHY WE ARE PASSING HTTPSERVLETRESPONSE IN THE DOWNLOADFILE CONTROLLER METHOD
	 * 
	 * 

Spring boot (and in particular Spring MVC which is a part of spring boot in this case) provides an abstraction over low-level HttpResponse which is a part of the servlet specification.

In a nutshell, this abstraction saves you from thinking in terms of Http Protocol and allows concentrating on a business logic which is a good thing.

So if you can avoid HttpServletResponse - do it by all means (and this is what you'll usually do in your applications). You can create objects (and spring will convert them for you if its REST), you can return ResponseEntity which is "status" + "body" - spring will do all the conversions.

So consider these techniques first.

Now, sometimes you have to manipulate the response at the low level, well in this case you have to work with HttpServletResponse object.

Example of this if you want to return a binary data that cannot be easily converted. For instance, if you want to prepare a zip file and send it back as a result of HTTP request, you'll have to get a more fine-grain low-level control, in this case, it's better to start off with HttpServletResponse param passed into controller method.
	 * 
	 */
	
	

	
	@GetMapping("/download/{fileName}")
	public void fileDownload(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException{
		
		
		InputStream inputStream =this.fileService.downloadFile(path, fileName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		//This last line is an important line. IUt should be remembered
		StreamUtils.copy(inputStream, response.getOutputStream());
		
	}
	
}
