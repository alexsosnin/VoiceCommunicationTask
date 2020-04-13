package org.vc.task.vct01.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceUtil {

	private ResourceUtil() {
	}

	public static String readResource(Class<?> clazz, String fileName, String charsetName) {
		InputStream inputStream = clazz.getResourceAsStream(fileName);
		try {
			BufferedInputStream bis = new BufferedInputStream(inputStream);
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result = bis.read();
			while ( result != -1 ) {
				buf.write((byte)result);
				result = bis.read();
			}
			String s = buf.toString(charsetName);
			return s;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
