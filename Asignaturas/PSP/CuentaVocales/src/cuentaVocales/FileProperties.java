package cuentaVocales;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class FileProperties {
	
	public static BufferedReader getBufferedReader(String fileName) throws FileNotFoundException {
		return new BufferedReader(new FileReader(fileName));
	}
	
	public static PrintWriter getPrintWriter(String fileName) throws FileNotFoundException {
		return new PrintWriter(fileName);
	}
	
	public static FileReader getFileReader(String fileName) throws FileNotFoundException {
		return new FileReader(fileName);
	}
}
