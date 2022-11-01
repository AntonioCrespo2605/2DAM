import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.io.RandomAccessFile; 
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Main2 { 
	public static void main(String[] args) { 
		Scanner sc = new Scanner(System.in); 
		RandomAccessFile fichero = null; 
		String palabra, cadena; 
		StringBuilder auxbuilder; 
		long pos = 0; 
		int indice; 
		try { 
			String ruta = null;
			boolean seleccionado;
			//se abre el fichero para lectura/escritura 
			Scanner entrada = null;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(fileChooser);
			try{
				ruta =fileChooser.getSelectedFile().getAbsolutePath();
				seleccionado=true;
			}catch(NullPointerException e){
				System.err.println("No se ha seleccionado ning�n fichero");
			}catch(Exception e){
				System.err.println(e.getMessage());
			}finally{
				if(entrada !=null){
					entrada.close();
				}
			}
			fichero = new RandomAccessFile(ruta, "rw");
			//Se pide la palabra a buscar 
			System.out.print("Introduce palabra: "); 
			palabra = sc.nextLine(); 
			//lectura del fichero
			//leemos la primera l�nea
			cadena = fichero.readLine(); 
			 
			while(cadena!=null){ //mientras no lleguemos al final del fichero 
				indice = cadena.indexOf(palabra); //buscamos la palabra en la l�nea le�da 
				while(indice!=-1){ //mientras la l�nea contenga esa palabra (por si est� repetida) 
					//paso la l�nea a un StringBuilder para modificarlo 
					auxbuilder = new StringBuilder(cadena); 
					auxbuilder.replace(indice, indice+palabra.length(), palabra.toUpperCase()); 
					cadena = auxbuilder.toString(); 
					//nos posicionamos al principio de la l�nea actual y se sobrescribe la 
					//l�nea completa 
					//La posici�n donde empieza la l�nea actual la estoy guardando 
					//en la variable pos 
					fichero.seek(pos);
					fichero.writeBytes(cadena); 
					//compruebo si se repite la misma palabra en la l�nea 
					indice = cadena.indexOf(palabra); 
					pos = fichero.getFilePointer(); 
					//posici�n de la l�nea actual que voy a leer 
					cadena = fichero.readLine(); 
					//lectura de la l�nea
				}
			}
		}catch (FileNotFoundException ex) { 
			System.err.println(ex.getMessage()); 
		}catch (IOException ex) { 
			System.err.println(ex.getMessage()); 
		}finally { 
			if(fichero!=null) {
				try {
					fichero.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
				
			}
		}
		
	}
		
}

			
		
		
	

 
