package tarea5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class Tarea5R2 {

	public static void main(String[] args) {
		boolean repeat;
		// menu jOptionPane
		do {
			repeat = true;
			int seleccion = JOptionPane.showOptionDialog(null, "Seleccione opcion", "Selector de opciones",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new Object[] { "Escribir 100 primeros n�meros pares en fichero", "mostrar fichero", "salir del progama" }, "opcion 1");
			switch (seleccion) {
			case 0:
				crearArchivo();
				break;
			case 1:
				mostrarArchivo();
				break;
			default:
				repeat = false;
				break;
			}
		} while (repeat);
	}

	// mostrar archivo
	private static void mostrarArchivo() {
		File f = new File("numeros.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String aux = leerArchivo(br);
			JOptionPane.showMessageDialog(null, aux);// mensaje del contenido del fichero
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage() + "\nCrea primero el fichero");// aviso de fichero no encontrado(crear primero)
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	// leer archivo
	private static String leerArchivo(BufferedReader br) throws IOException {
		String toret = "";
		String aux;

		while ((aux = br.readLine()) != null) {
			toret += "\n" + aux;
		}
		return toret;
	}

	// crear archivo
	private static void crearArchivo() {
		
		File f = new File("numeros.txt");

		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f);
			PrintWriter pw = new PrintWriter(fw);
			escribirArchivo(pw);
			pw.close();
			fw.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private static void escribirArchivo(PrintWriter pw) {
		int cont=0;
		for(int i=0;i<100;i++) {
			if(i!=99)pw.print(cont+",");
			else pw.print(cont);
			if(i==50)pw.print("\n");
			cont=cont+2;
		}
		pw.close();
	}

}
