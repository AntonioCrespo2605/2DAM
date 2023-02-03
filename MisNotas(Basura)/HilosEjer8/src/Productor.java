import java.util.Random;

public class Productor extends Thread{
	private final Random random = new Random();
	private final Buffer b;
	private final int id;
	private int time;
	private Thread[] clientes;
	private int prod_vez;

	//para crear un productor se le manda el buffer, un id, los clientes y el tiempo que tarda en producir 
	public Productor(Buffer b, int id, Thread[] clientes, int time, int prod_vez) {
		this.b = b;
		this.id = id;
		this.clientes = clientes;
		this.time=time;
		this.prod_vez=prod_vez;
	}

	
	@Override
	public void run() {
		System.out.println("Iniciando productor");
		while(clientesAlive()) {//comprueba que siguen en ejecución los hilos de clientes 
			int producido = 1+random.nextInt(prod_vez);//genera una cantidad de productos.
			System.out.println("El productor " + id + " ha fabricado " +producido+" productos");
			b.putInBuffer(producido);//añadiendo al buffer los productos
			try {
				Thread.sleep(time);//tiempo que tarda en volver a producir
			} catch (InterruptedException e) {
				System.err.println("Ha ocurrido un error en el productor\n" + e.getMessage());
			}
		}
		System.out.println("Terminando productor...");
	}
	
	//comprueba que siguen en ejecución los hilos de clientes 
	public boolean clientesAlive() {
		for(Thread c: this.clientes) {
			if(!c.isAlive()) return false; 
		}
		return true;
	}
}
