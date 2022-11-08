package com.project.demo.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.demo.payload.ApiResponse;

/*@ControllerAdvice*/	
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResouceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResouceNotFoundException ex){
		
		String message = ex.getMessage();
		
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
		Map<String, String> responseMap = new HashMap<String, String>();
		
		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
		
		
		for(ObjectError er:errorList) {
			String fieldName = ((FieldError)er).getField();
			String message = er.getDefaultMessage();
			
			responseMap.put(fieldName, message);
		}
		
		return new ResponseEntity<Map<String,String>>(responseMap, HttpStatus.BAD_REQUEST);
		
	}
	
}
