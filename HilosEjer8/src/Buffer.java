import java.util.Random;

public class Buffer {
	private static Random r=new Random();
	private int cont;
	private int size;

	//al buffer le llega la cantidad de productos que puede almacenar al mismo tiempo
	public Buffer(int size) {
		this.size=size;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public synchronized int get() {
		while (cont<1 && Cliente.accesos > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("Ha ocurrido un error en el Buffer\n" + e.getMessage());
			}
		}
		notifyAll();
		if(cont<0)cont=0;
		int aux=1+r.nextInt(cont);
		cont=cont-aux;
		return aux;
	}

	public synchronized void putInBuffer(int value) {
		if(value+cont > size) value = size-cont;
		while (cont>=size) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("Ha ocurrido un error al añadir productos en el Buffer\n: " + e.getMessage());
			}
		}
		cont += value;
		notify();
	}
}
