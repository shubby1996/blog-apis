package com.project.demo.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.demo.payload.ApiResponse;
import com.project.demo.payload.FileResponse;
import com.project.demo.payload.PostDto;
import com.project.demo.payload.PostResponse;
import com.project.demo.services.FileService;
import com.project.demo.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId) {
		
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
		
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer userId){
		
		List<PostDto> resList  = this.postService.getAllPostsByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(resList, HttpStatus.OK);
	}
	
	@GetMapping("/postById/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
		
		PostDto postDto  = this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Integer categoryId){
		
		List<PostDto> resList  = this.postService.getAllPostsByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(resList, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<PostDto>> getAllPosts(){
		List<PostDto> resList = this.postService.getAllPost();
		return new ResponseEntity<List<PostDto>>(resList, HttpStatus.OK);
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {
		PostDto createdPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId){
		this.postService.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted", true), HttpStatus.OK);
	}
	
	//Page number starts at zero and pageSize starts at 1
	@GetMapping("/Pages")
	//("/{pageSize}/{pageNumber}")
	public List<PostDto> getAllPostUsingPagnationDtos(
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber
//			@PathVariable("pageSize") Integer pageSize, @PathVariable("pageNumber") Integer pageNumber
			){	
		List<PostDto> allPostUsingPage = this.postService.getAllPostUsingPage(pageSize, pageNumber);
		return allPostUsingPage;	
	}
	
	@GetMapping("/PageApiResponse")
	public PostResponse getAllPostUsingPagnationDtosAndApiResponse(	
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber
			){
		
		PostResponse allPostUsingPage = this.postService.getAllPostUsingPagnationDtosAndApiResponse(pageSize, pageNumber);
		return allPostUsingPage;	
	}
	
	@GetMapping("/PageApiResponse/sorted")	
	public PostResponse getAllPostsByPagingAndSorting(
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy, 
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
			
//		PostResponse allPostUsingPageSorted = this.postService.getAllPostUsingPagnationDtosAndApiResponseAndSorting(pageSize, pageNumber,sortBy);
		PostResponse allPostUsingPageSorted = this.postService.getAllPostUsingPagnationDtosAndApiResponseAndSorting(pageSize, pageNumber,sortBy,sortDir);
		return allPostUsingPageSorted;
	}
	
	//search
	@GetMapping("/search/{keyword}")
	public List<PostDto> searchUsingKeywords(@PathVariable("keyword") String keyword){
		List<PostDto> searchePostDtos = this.postService.searchPostsByTitle(keyword);
		return searchePostDtos;
	}
	
	//uploadFile
	@PostMapping("image/upload/{postId}")
	public ResponseEntity<PostDto> fileUpload(
			@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId
			){
		String fileName;
		
		try {
			
			PostDto postDto = this.postService.getPostById(postId);
			
			fileName = this.fileService.uploadImage(path, image);
			
			postDto.setImageName(fileName);
			PostDto updatedPost = this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			return new ResponseEntity<FileResponse>(new FileResponse(null, "Image is not uploaded due to error in server."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
		
//		return new ResponseEntity<FileResponse>(new FileResponse(fileName, "Image is successfully uploaded."), HttpStatus.OK);
		
	}
	
	//download file
	@GetMapping("image/download/{fileName}")
	public void fileDownload(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException{
		
		
		InputStream inputStream =this.fileService.downloadFile(path, fileName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		//This last line is an important line. IUt should be remembered
		StreamUtils.copy(inputStream, response.getOutputStream());
		
	}
}

