
//Vehiculo Objeto
public class Vehiculo {
	//atributos
	private String matricula;
	private String marca;
	private String color;
	private String fecha_mov;
	
	//constructor
	public Vehiculo(String matricula, String marca, String color, String fecha_mov) {
		this.matricula = matricula;
		this.marca = marca;
		this.color = color;
		this.fecha_mov = fecha_mov;
	}
	
	//constructor copia
	public Vehiculo(Vehiculo v) {
		this.matricula=v.getMatricula();
		this.marca=v.getMarca();
		this.color=v.getColor();
		this.fecha_mov=v.getFecha_mov();
	}

	//getters y setters
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFecha_mov() {
		return fecha_mov;
	}

	public void setFecha_mov(String fecha_mov) {
		this.fecha_mov = fecha_mov;
	}

	//toString
	@Override
	public String toString() {
		return "Matricula:"+getMatricula()+"\n"
				+ "Marca:"+getMarca()+"\n"
				+ "Color:"+getColor();
	}
	
	public String toStringInFile() {
		return "matricula="+getMatricula()+"\n"
				+ "marca="+getMarca()+"\n"
				+ "color="+getColor()+"\n"
				+ "fecha_mov="+getFecha_mov()+"\n"
				+ "------------------------------";
	}
	
	
}
