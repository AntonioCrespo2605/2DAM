package tarea5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tarea5R4Ahorcado { 
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		String palabra="";
		int intentos=5;
		ArrayList<Character>usados=new ArrayList<Character>();
		
		try {
			BufferedReader br=new BufferedReader(new FileReader("palabra.txt"));
			palabra=br.readLine();//solo nos interesa leer la primera palabra para un ahorcado sencillo
			br.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		//palabra con la misma dimensión de la palabra original
		String oculto="";
		for(int i=0;i<palabra.length();i++) {
			oculto+="*";
		}
		boolean victory=true;
		boolean encontrado;
		boolean repeat;
		String aux;
		String aux4;
		char aux2;
		char aux3;
		
		do {
			repeat=true;
			System.out.println(oculto);
			System.out.println("Escribe una letra. Si escribes más de una se tomará la primera;");
			aux=sc.nextLine();
			aux=aux.toLowerCase();//ponemos en minúsculas y quitamos espacios del principio
			aux=aux.trim();
			aux2=aux.charAt(0);
			
			//vamos a quitar las tildes
			if(aux2=='á')aux2='a';
			else if(aux2=='é')aux2='e';
			else if(aux2=='í')aux2='i';
			else if(aux2=='ó')aux2='o';
			else if(aux2=='ú')aux2='u';
			
			//en ascii la a es el 49 y la z el 122. Si no está en ese rango no es una letra permitida. La ñ es la 164
			if(((int)aux2<49 || (int)aux2>122)&&(int)aux2==164) {
				System.err.println("El primer caracter introducido no se corresponde con una letra permitida[a-z]");
			}else {
				//si ya a sido usado no lo comprobamos ni contamos como error
				if(usados.contains(aux2)){
					System.err.println("El caracter ya ha sido usado; Porfavor, inténtelo de nuevo;");
				}else {
					
					aux4="";
					usados.add(aux2);
					encontrado=false;
					
					//comprobar si la palabra contiene la letra y quitar asteriscos si la tiene
					for(int i=0;i<palabra.length();i++) {
						aux3=palabra.charAt(i);
						if(aux2==aux3) {
							encontrado=true;
							aux4+=aux2;
						}else aux4+=oculto.charAt(i);
					}
					oculto=aux4;
					
					if(!encontrado) {
						intentos--;
						System.out.println("No se ha encontrado la letra; Intentos:"+intentos);
						
						//si llega a 0 intentos sale del bucle y se pierde la partida
						if(intentos==0) {
							repeat=false;
							victory=false;
						}
					}else {
						System.out.println("Se ha encontrado la letra");
						//si la palabra ocula es igual a la palabra a resolver sale del bucle y se gana la partida
						if(oculto.equals(palabra)) {
							repeat=false;
							victory=true;;
						}
					}
				}
			}
		
		}while(repeat);
		
		if(victory)System.out.println("Victoria");
		else System.out.println("Derrora");
		
		sc.close();
	}

}
