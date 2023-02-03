
public class Cliente extends Thread{
	private Buffer b;
	private final int id;
	private static final int MAX_ACCESOS = 5;
	static int accesos = MAX_ACCESOS;
	
	//para crear
	public Cliente(Buffer b, int id) {
		this.b = b;
		this.id = id;
	}
	
	//en caso de querer volver a ejecutar el programa se usaría este método
	public static void resetAccesos() {
		accesos = MAX_ACCESOS;
	}
	
	//iniciar cliente
	@Override
	public void run() {
		System.out.println("Inicializando el cliente " + id);
		while(accesos > 0) {
			System.out.println("Quedan " + accesos + " accesos.");
			int cant = b.get();
			if(accesos <= 0) break;
			System.out.println("El cliente "+id+" ha comprado "+cant+" productos");
			accesos--;
		}
		System.out.println("Finalizado cliente " + id);
	}
}
