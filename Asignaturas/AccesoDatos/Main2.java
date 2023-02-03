package wordsearcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main2 {

	public static void main(String[]args){
		boolean seleccionado=false;
		String ruta="";
		//Con el fileChooser obtenemos la ruta del fichero a leer
		Scanner entrada = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(fileChooser);
		try{
			ruta =fileChooser.getSelectedFile().getAbsolutePath();
			seleccionado=true;
		}catch(NullPointerException e){
			System.err.println("No se ha seleccionado ningún fichero");
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally{
			if(entrada !=null){
				entrada.close();
			}
		}
		
		if(seleccionado) {
			long posicionPuntero=0;
			String palabra="";
			palabra = JOptionPane.showInputDialog("Escribe la palabra a poner en mayúsculas");
			if(palabra.length()!=0) {	
				try {
					RandomAccessFile raf = new RandomAccessFile(ruta, "rw");
					String linea=raf.readLine();
					while(linea!=null) {//leer mientras haya líneas en el fichero
						raf.seek(posicionPuntero);
						raf.writeBytes(changeWordUpperCase(linea, palabra));
						posicionPuntero=raf.getFilePointer();
						linea=raf.readLine();
					}
					
					raf.close();
				} catch (FileNotFoundException e) {
					System.err.println(e.getMessage());
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
			
		}
		
	}
	
	
	/*this method change a word of a String toUpperCase*/
	public static String changeWordUpperCase(String linea, String palabra) {
		palabra=palabra.toUpperCase();
		String lineaAux="";
		String mismaLongAux;
		for(int i=0;i<linea.length()-(palabra.length()-1);i++) {//recorrer la línea
			mismaLongAux="";
			for(int j=0;j<palabra.length();j++) {
				mismaLongAux+=linea.charAt(i+j);
			}
			if(mismaLongAux.toUpperCase().equals(palabra)) {
				lineaAux+=palabra;
				i=i+(palabra.length()-1);
			}else lineaAux+=linea.charAt(i);
		}
		
		for(int i=lineaAux.length();i<linea.length();i++) {
			lineaAux+=linea.charAt(i);
		}
		
		return lineaAux;
	}
	
}

