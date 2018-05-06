package com.github.mouse0w0.ube.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public final class JarUtils {
	
	public static void unjar(Path jar, Path targetPath) throws IOException {
		if(!Files.exists(targetPath))
			return;
		try(JarFile jarFile = new JarFile(jar.toFile())) {
			Enumeration<JarEntry> entries = jarFile.entries();
			while(entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if(entry.isDirectory()) {
					Files.createDirectory(targetPath.resolve(entry.getName()));
				} else {
					try(InputStream entryIs = jarFile.getInputStream(entry)){
						Files.copy(entryIs, targetPath.resolve(entry.getName()));
					}
				}
			}
		}
	}
	
	public static void jar(Path sourcePath, Path jar) throws IOException {
		if(!Files.exists(jar))
			Files.createFile(jar);
		try (JarOutputStream jarOs = new JarOutputStream(Files.newOutputStream(jar))) {
			Iterator<Path> iterator = Files.walk(sourcePath).iterator();
			while (iterator.hasNext()) {
				Path path = iterator.next();
				if (!Files.isDirectory(path)) {
					JarEntry entry = new JarEntry(sourcePath.relativize(path).toString());
					jarOs.putNextEntry(entry);
					Files.copy(path, jarOs);
				}
			}
		}
	}

}
