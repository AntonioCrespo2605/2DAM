import java.util.Scanner;

public class Main {

	private static Contenedor container;
	private static Thread productor;
	private static Thread[] consumidores;
	private static final int CANTIDAD_CONSUMIDORES = 5;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		container = new Contenedor();
		consumidores = new Thread[CANTIDAD_CONSUMIDORES];
		// Cambiar a for normal
		
		for (int i = 0; i < consumidores.length; i++) {
			consumidores[i] = new Thread(new Consumidor(container, i));
			consumidores[i].start();
		}
		productor = new Thread(new Productor(container, 1, consumidores));
		productor.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < consumidores.length; i++) {
			if (consumidores[i].isAlive())
				System.out.println("Estoy vivo");
		}
		Consumidor.resetAccesos();
		System.out.println("Fin ejercicio 7.");
		scan.close();
	}
	
}
