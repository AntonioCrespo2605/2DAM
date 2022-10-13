package cuentaVocales;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class VocalCounter {

	public static void main(String[] args) {
		if(!counter(args[0],args[1].charAt(0),args[2]))System.err.println("Ha ocurrido algún error al contar o escribir el resultado;");
	}

	private static boolean counter(String fileName, char vowel, String fileResultName){
		File f=new File(fileResultName);
		
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.err.println(e.getMessage());
				return false;
			}
		}
		
		try {
			FileReader fr=FileProperties.getFileReader(fileName);
			int cont=0;
			String aux;
			int valor=fr.read();
            while(valor!=-1){
            	aux=(char)valor+"";
                if(aux.toLowerCase().charAt(0)==vowel)cont++;
                valor=fr.read();
            }
            PrintWriter pw=FileProperties.getPrintWriter(fileResultName);
            pw.println(cont);
            pw.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}
	
}
