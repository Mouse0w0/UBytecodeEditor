package com.github.mouse0w0.ube;

import java.io.IOException;
import java.nio.file.Paths;

import com.github.mouse0w0.ube.util.JarUtils;

public class JarUtilsTest {
	
	public static void main(String[] args) throws IOException {
		JarUtils.unjar(Paths.get("test.jar"), Paths.get(".temp"));
		JarUtils.jar(Paths.get(".temp"), Paths.get("test1.jar"));
	}

}
