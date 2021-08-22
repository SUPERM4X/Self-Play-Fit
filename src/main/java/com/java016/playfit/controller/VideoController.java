package com.java016.playfit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VideoController {
	
	@GetMapping("/video/{id}/{filename}")
	public ResponseEntity <InputStreamResource> 
	retrieveResource(@PathVariable(value = "id")final String id,
					@PathVariable(value = "filename")final String filename) throws Exception {
		
		String path = "C:\\Users\\MSIK\\Videos\\" + filename + ".mp4";
		File file = new File(path);
		InputStream inputStream = new FileInputStream(path);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept-Ranges", "bytes");
		headers.set("Content-Type", "video/mp4");
		//headers.set("Content-Range", "bytes 50-1025/17839845");
		headers.set("Content-Range", "bytes 50-1025/178398");
		headers.set("Content-Length", String.valueOf(file.length()));
		System.out.println("Video Controller Processed");
		System.out.println("Video Id = " + id);
		return new ResponseEntity<> (new InputStreamResource(inputStream), headers, HttpStatus.OK);

	}
}
