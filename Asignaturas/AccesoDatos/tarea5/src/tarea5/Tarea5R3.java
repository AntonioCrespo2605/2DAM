package tarea5;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class Tarea5R3 {

	public static void main(String[] args) {
		
		//crear fichero
		File f=new File("quitarEspacios.txt");
		
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(f));
			pw.print("Esto es una prueba");
			pw.flush();
			pw.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		
		boolean repeat;
		// menu jOptionPane
		do {
			repeat = true;
			int seleccion = JOptionPane.showOptionDialog(null, "Seleccione opcion", "Selector de opciones",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new Object[] {"mostrar fichero sin espacios","mostrar fichero con espacios", "salir del progama" }, "opcion 1");
			switch (seleccion) {
			case 0:
				mostrarArchivo();
				break;
			case 1:
				mostrarArchivoConEspacios();
				break;
			default:
				repeat = false;
				break;
			}
		} while (repeat);
	}

	//mostrar archivo con espacios
	private static void mostrarArchivoConEspacios() {
		String aux="";
		try{
			FileReader fileReader = new FileReader("quitarEspacios.txt");
	        int caracterLeido = fileReader.read();
	        while(caracterLeido!= -1) {
	            char caracter = (char)caracterLeido;
	            aux+=caracter;
	            caracterLeido = fileReader.read();//salto al siguiente caracter
	        }
	        
	        JOptionPane.showMessageDialog(null, aux);
	        
	        fileReader.close();
	    }catch(Exception e){
	        System.err.println(e.getMessage());
	    }
	}

	//mostrar archivo
	private static void mostrarArchivo() {
		String aux="";
		try{
			FileReader fileReader = new FileReader("quitarEspacios.txt");
	        int caracterLeido = fileReader.read();
	        while(caracterLeido!= -1) {
	            char caracter = (char)caracterLeido;
	            if(caracter!=' ')aux+=caracter;//si el caracter es un espacio no se concatena al String
	            caracterLeido = fileReader.read();
	        }
	        
	        JOptionPane.showMessageDialog(null, aux);
	        
	        fileReader.close();
	    }catch(Exception e){
	        System.err.println(e.getMessage());
	    }
	}

}
