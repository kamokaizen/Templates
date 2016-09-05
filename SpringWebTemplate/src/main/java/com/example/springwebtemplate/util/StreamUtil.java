package com.example.springwebtemplate.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * Utility functions for streams.
 * 
 * @author hayyildiz
 */
public class StreamUtil {
	public static InputStream getStream(String name) throws Exception {
		return getStream(name, true);
	}

	public static InputStream getStream(String name,
			boolean createFileIfNotExists) throws Exception {
		InputStream result = null;
		try {
			result = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(name);

			if (result == null) {
				File file = getFile(name, createFileIfNotExists);
				if (file != null && file.exists()) {
					result = new FileInputStream(file);
				}
			}
		} catch (Throwable e) {
			throw new Exception("Cannot find file: " + name);
		}

		return result;
	}

	public static URL getResource(String name) throws Exception {
		try {
			URL url = Thread.currentThread().getContextClassLoader()
					.getResource(name);
			if (url == null) {
				URI uri = new File(name).toURI();
				if (uri != null) {
					url = uri.toURL();
				}
			}
			return url;
		} catch (Throwable e) {
			throw new Exception("Cannot find file: " + name);
		}
	}

	public static File getFile(String fileName, boolean createFileIfNotExists)
			throws IOException {
		File outputFile = new File(fileName);
		File parent = outputFile.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (!outputFile.exists() && createFileIfNotExists) {
			outputFile.createNewFile();
		}
		return outputFile;
	}

	public static File getFolder(String folderName) throws IOException {
		File outputFile = new File(folderName);
		if (!outputFile.exists()) {
			outputFile.mkdirs();
		}
		return outputFile;
	}

	public static void closeStream(java.io.Closeable closable) {
		try {
			if (closable != null) {
				closable.close();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
