/**
 * 
 */
package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.property.FileStorageProperties;

/**
 * @author tech
 *
 */
@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	private FileStorageProperties FileStorageProperties;

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String storeFile(MultipartFile file) throws Exception {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new Exception();
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return targetLocation.toString();
		} catch (IOException ex) {
			throw new Exception();
		}
	}
	
	public List<String> storeFile(MultipartFile[] multipartFile) throws Exception {
		// Normalize file name
		List<String> filePaths = new ArrayList<>();
		for (MultipartFile file : multipartFile) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

			try {
				// Check if the file's name contains invalid characters
				if (fileName.contains("..")) {
					throw new Exception();
				}

				// Copy file to the target location (Replacing existing file with the same name)
				Path targetLocation = this.fileStorageLocation.resolve(fileName);
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
				filePaths.add(targetLocation.toString());
			} catch (IOException ex) {
				throw new Exception();
			}
		}
		return filePaths;
	}
}
