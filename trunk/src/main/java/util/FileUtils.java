package util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	static public String load(String path) throws IOException {
		FileReader fileReader = new FileReader(path);
		StringBuilder result = new StringBuilder();
		char[] buffer = new char[1024];

		int size;
		while ((size = fileReader.read(buffer)) != -1)
			result.append(buffer, 0, size);
		fileReader.close();
		return result.toString();
	}

	static public void save(String path, String content) throws IOException {
		FileWriter writer = new FileWriter(path);
		writer.write(content);
		writer.close();
	}
}
