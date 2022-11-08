package com.project.demo.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.demo.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		//file name
//		System.out.println(path + File.separator+ file.getOriginalFilename());
		String name  = file.getOriginalFilename();
		
		//generate random name for file 
		String uuid = UUID.randomUUID().toString();
		String extension = name.substring(name.lastIndexOf('.'));
//		System.out.println(uuid + extension);		
		
		//full path
//		String filePath = path + File.separator + name;
		String filePath = path + File.separator + uuid+extension;
		System.out.println(filePath);
		
		//create folder if not created
		File f =  new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
		//file copy
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return uuid+extension;
	}

	@Override
	public InputStream downloadFile(String path, String fileName) {
		// TODO Auto-generated method stub
		
		String fullPath = path + File.separator + fileName;
		
		try{
			
			//db logic to retrieve file from the database
			
			FileInputStream fileInputStream = new FileInputStream(fullPath);
			return fileInputStream;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
