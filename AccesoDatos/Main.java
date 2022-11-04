import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {

	
	public static void main(String[] args) {
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
		
		RandomAccessFile fichero=null;
		
		
		if(seleccionado) {
			try {
				fichero=new RandomAccessFile(ruta,"rw");
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
			}
			
		}
		
		boolean repeat=false;
		do {
		repeat=true;
		int seleccion = JOptionPane.showOptionDialog(null,"Seleccione opcion", "Selector de opciones",
		JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, 
		new Object[] { "añadir double al principio", "añadir double al final", "mostrar fichero", "sustituir un nº por otro", "salir" },"opcion 1");
		switch(seleccion) {
		case 0:doublePrincipio(fichero);
			break;
		case 1:doubleFinal(fichero);
			break;
		case 2:mostrarFichero(fichero);
			break;
		case 3:sustituirNum(fichero);
			break;
		default:repeat=false;
			break;
		}
		}while(repeat);
	}
	
	private static void doublePrincipio(RandomAccessFile fichero) {
		boolean repeat=false;
		double d1=0;
		
		do {
		repeat=false;
		String seleccion = JOptionPane.showInputDialog(null,"Escribe un numero double",JOptionPane.QUESTION_MESSAGE);
		try {
			d1=Double.parseDouble(seleccion);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Eso no es un double desgraciado");
			repeat=true;
		}
		}while(repeat);
		
		try {
			fichero.seek(0);
			String aux=d1+"";
			fichero.writeBytes(aux);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}	
	}
	
	private static void doubleFinal(RandomAccessFile fichero) {
		boolean repeat=false;
		double d1=0;
		
		do {
		repeat=false;
		String seleccion = JOptionPane.showInputDialog(null,"Escribe un numero double",JOptionPane.QUESTION_MESSAGE);
		try {
			d1=Double.parseDouble(seleccion);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Eso no es un double desgraciado");
			repeat=true;
		}
		}while(repeat);
		
		try {
			fichero.seek(fichero.length());
			String aux=d1+"";
			fichero.writeBytes(aux);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private static void mostrarFichero(RandomAccessFile fichero) {
		String show="";
		try {
			fichero.seek(0);
			String linea=fichero.readLine();
			while (linea!=null) {
				show+=linea+"\n";
				linea=fichero.readLine();
			}
		}catch(IOException e) {
			System.err.println(e.getMessage());
		};
		JOptionPane.showMessageDialog(null,show);
	}
	
	private static void sustituirNum(RandomAccessFile fichero) {
		boolean repeat;
		String numSus, numNew = null;
		
		try {
			fichero.seek(0);
		} catch (IOException e1) {
			System.err.println(e1.getMessage());
		}
		
		do {
			repeat=true;
			numSus=JOptionPane.showInputDialog("Escribe el número que deseas cambiar");
			if(numSus==null)repeat=false;
			else if(isInteger(numSus)||isDouble(numSus))repeat=false;
			else JOptionPane.showMessageDialog(null, "Eso no es un número");
		}while(repeat);
		
		if(numSus!=null) {
			do {
				repeat=true;
				numNew=JOptionPane.showInputDialog("Escribe el nuevo número");
				if(numNew==null)repeat=false;
				else if(isInteger(numNew)||isDouble(numNew))repeat=false;
				else JOptionPane.showMessageDialog(null, "Eso no es un número");
			}while(repeat);
		}
		
		if(numSus != null&&numNew !=null) {
			if(isInteger(numSus))numSus=""+Integer.parseInt(numSus);
			if(isInteger(numNew))numNew=""+Integer.parseInt(numNew);
			StringBuilder auxBuilder;
			long pos=0;
			String linea;
			int indice;
			try {
				linea=fichero.readLine();
				while(linea!=null) {
					indice = linea.indexOf(numSus); 
	                while(indice!=-1){
	                	System.out.println("indice:"+indice);
	                    auxBuilder = new StringBuilder(linea); 
	                    if(numSus.length()<=numNew.length())auxBuilder.replace(indice, indice+numSus.length(), numNew);
	                    else {
	                    	System.out.println("poraqeui");
	                    	auxBuilder.replace(indice, indice+numSus.length(), numNew); 
	                    	auxBuilder.delete(indice+numNew.length(),numSus.length());
	                    }
	                    linea = auxBuilder.toString();
	                    fichero.seek(pos);
	                    fichero.writeBytes(linea);
	                   
	                    indice = linea.indexOf(numSus);
	                }
	                pos = fichero.getFilePointer();
	                linea=fichero.readLine();
				}
				        
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		}
		
	}
	
	private static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	private static boolean isDouble(String s) {
		try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}

}

