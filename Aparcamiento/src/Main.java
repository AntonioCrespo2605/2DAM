import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		try {
			VehiculoController vc=new VehiculoController("registro");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
