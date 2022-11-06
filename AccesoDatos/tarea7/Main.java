import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {
	
		public static void main(String[] args) {
			String origen="", destino="";
			boolean seleccionado=false;;
			
			//Con el fileChooser obtenemos la ruta del fichero a leer
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Selecciona el fichero a copiar");
			fileChooser.showOpenDialog(fileChooser);
			try{
				origen = fileChooser.getSelectedFile().getAbsolutePath();
				seleccionado=true;
			}catch(NullPointerException e){
				System.err.println("No se ha seleccionado ningún fichero");
			}catch(Exception e){
				System.err.println(e.getMessage());
			}
			
			//cambiamos el fileChooser para buscar directorios
			fileChooser.setCurrentDirectory(new java.io.File("."));
			fileChooser.setDialogTitle("Selecciona la carpeta de destino");
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setAcceptAllFileFilterUsed(false);
			
			//pedimos la ruta del destino
			if(seleccionado) {
				seleccionado=false;
				fileChooser.showOpenDialog(fileChooser);
				try{
					destino = fileChooser.getSelectedFile().getAbsolutePath();
					seleccionado=true;
				}catch(NullPointerException e){
					System.err.println("No se ha seleccionado ningún directorio");
				}catch(Exception e){
					System.err.println(e.getMessage());
				}
			}
	 
			if(seleccionado)moverFichero(origen, destino);
	 
	    }
		
		
		//mueve el fichero origen al directorio destino
	    public static void moverFichero (String origen, String destino){
	 
	    	FileInputStream fis=null;
	    	FileOutputStream fos=null;
	    	
	    	File f=new File(origen);
	    	String name=f.getName();//nombre del fichero
	    	destino+="\\"+name;//el destino ahora será el directorio concatenado al nombre del fichero para copiar ahí el contenido
	    	
	    	f=new File(destino);
	    	
			try {
				if(!f.exists())f.createNewFile();//si no exite el fichero se crea vacío
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
	    	
	        try {
	        	fis=new FileInputStream(origen);
	            fos=new FileOutputStream(destino);
	 
	            byte contenido[]=new byte[fis.available()];//creamos un array de bytes con la longitud del contenido del fichero origen
	            fis.read(contenido);//leemos el fichero origen y guardamos el contenido el array de bytes
	            fos.write(contenido);//escribimos en el destino el contenido del array
	 
	        }catch(IOException e){
	            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            e.printStackTrace();
	        }finally {
				try {
					if(fis!=null)fis.close();
					if(fos!=null)fos.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
	        }
	    }
		

}
