package pspBatch1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Escribe tu nombre");
		cargarPlano("helloBat.bat "+sc.nextLine());
		sc.close();
	}

	public static void cargarPlano(String comando) {
		try {
			String linea;
			Process p = Runtime.getRuntime().exec(comando);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((linea = input.readLine()) != null) {
				System.out.println(linea);
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
