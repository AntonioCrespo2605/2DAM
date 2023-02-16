import java.util.ArrayList;

public class Objeto {
	private String nombre;
	private ArrayList<String> etNieto;
	private ArrayList<String> relleno;
	
	public Objeto(String nombre) {
		this.nombre=nombre;
		this.etNieto=new ArrayList<String>();
		this.relleno=new ArrayList<String>();
	}
	
	public Objeto(String nombre, ArrayList<String>etNieto, ArrayList<String>relleno) {
		this.nombre=nombre;
		this.etNieto=etNieto;
		this.relleno=relleno;
	}
	
	public Objeto(String nombre, ArrayList<String>etNieto) {
		this.nombre=nombre;
		this.etNieto=etNieto;
	}
	
	public Objeto(Objeto o) {
		this.nombre=o.getNombre();
		this.etNieto=o.getEtNieto();
		this.relleno=o.getRelleno();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<String> getEtNieto() {
		return etNieto;
	}

	public void setEtNieto(ArrayList<String> etNieto) {
		this.etNieto = etNieto;
	}

	public ArrayList<String> getRelleno() {
		return relleno;
	}

	public void setRelleno(ArrayList<String> relleno) {
		this.relleno = relleno;
	}
	
	public int getCantNietos() {
		return this.etNieto.size();
	}
	
	public void addAtributo(String atr) {
		etNieto.add(atr);
	}
	
	public void addRelleno(String rel) {
		relleno.add(rel);
	}
	
	
	@Override
	public String toString() {
		String toret="";
		for(int i=0;i<etNieto.size();i++) {
			toret+=etNieto.get(i)+": "+relleno.get(i)+"\n";
		}
		
		return toret;
	}
}
