import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		int cant=1;
		String aux;
		boolean repeat=true;
		
		while(repeat){
			repeat=false;
			System.out.println("Escribe el tamaño del buffer:");
			aux=sc.nextLine();
			if(checkNum(aux))cant=Integer.parseInt(aux);
			else repeat=true;
		}
		
		Buffer b=new Buffer(cant);
		
		repeat=true;
		while(repeat){
			repeat=false;
			System.out.println("¿Cuantos clientes tendrá el producto?:");
			aux=sc.nextLine();
			if(checkNum(aux))cant=Integer.parseInt(aux);
			else repeat=true;
		}
		
		Thread[]clientes=new Thread[cant];
		
		for(int i=0;i<cant;i++) {
			clientes[i]=new Cliente(b,i);		
		}
		
		repeat=true;
		while(repeat){
			repeat=false;
			System.out.println("¿Que cantidad fabrica el productor por tandada?:");
			aux=sc.nextLine();
			if(checkNum(aux))cant=Integer.parseInt(aux);
			else repeat=true;
		}
		int cantTan=cant;
		repeat=true;
		while(repeat){
			repeat=false;
			System.out.println("¿Cuantos segundos tarda el productor en sacar una tandada?:");
			aux=sc.nextLine();
			if(checkNum(aux))cant=Integer.parseInt(aux);
			else repeat=true;
		}
		cant=cant*1000;
		Productor p=new Productor(b, 1, clientes, cant, cantTan);
		
		for(Thread c : clientes) {
			c.start();
		}
		p.start();
		
		while(p.isAlive()) {}
		
		Cliente.resetAccesos();
		sc.close();
		
	}
	
	private static boolean checkNum(String num) {
		int i;
		try {
			i=Integer.parseInt(num);
		}catch(Exception e){
			System.err.println("Debe ser un número entero;");
			return false;
		}
		if(i<1) {
			System.err.println("Ha de ser mayor a 0;");
			return false;
		}
		
		return true;
	}
	
	

}
