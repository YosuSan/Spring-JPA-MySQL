package com.bolsaideas.springboot.app.models.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("uploadFileService")
public class UploadFileServiceImp implements IUploadFileService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final String UPLOADS = "uploads";
	private static final Path UPLOADS_FOLDER = Paths.get(UPLOADS);

	@Override
	public Resource load(String filename) throws MalformedURLException {

		Path pathFoto = getPath(filename);
		log.info("Path Foto: " + pathFoto);

		Resource recurso = null;

		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("No se puede carga la imagen: " + pathFoto);
		}

		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFileName = UUID.randomUUID().toString() + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFileName);

		log.info("Root path: " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFileName;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);

		if (rootPath.toFile().exists() && rootPath.toFile().canRead())
			return rootPath.toFile().delete();
		return false;
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(UPLOADS_FOLDER.toFile());

	}

	@Override
	public void init() throws IOException {
		Files.createDirectories(UPLOADS_FOLDER);

	}

	private Path getPath(String filename) {
		return UPLOADS_FOLDER.resolve(filename).toAbsolutePath();
	}
}
