import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class VehiculoController {
	private ArrayList<Vehiculo>vehiculos;
	private ArrayList<Vehiculo>entradas;
	private ArrayList<Vehiculo>salidas;
	private String folder;
	
	/**Constructor,funciones de lectura y comprobador de ficheros**/
	/********************************************************************************************/
	
	//constructor al que se le ha de mandar la carpeta de gestión
	public VehiculoController(String folder) throws IOException {
		this.folder=folder;
		buildFolder();
		readVehiculo();
	}
	
	//constructor copia	
	public VehiculoController(VehiculoController vc) {
		this.folder=vc.getFolder();
		this.vehiculos=vc.getVehiculos();
		this.entradas=vc.getEntradas();
		this.salidas=vc.getSalidas();
	}

	//comprueba que exista la carpeta y los ficheros y sino los crea
	private void buildFolder() throws IOException {
		File f=new File(folder);
		if(!f.exists())f.mkdir();
		
		f=new File(folder+"\\vehiculos.txt");
		if(!f.exists())f.createNewFile();
		
		f=new File(folder+"\\entradas.txt");
		if(!f.exists())f.createNewFile();
		
		f=new File(folder+"\\salidas.txt");
		if(!f.exists())f.createNewFile();
	}

	//inicializa los 3 arrayList leyendo los 3 ficheros del directorio
	private void readVehiculo() throws FileNotFoundException{
		this.vehiculos=readFromFile(this.folder+"\\vehiculos.txt");
		this.entradas=readFromFile(this.folder+"\\entradas.txt");
		this.salidas=readFromFile(this.folder+"\\salidas.txt");
	}
	
	//lee un fichero y lo devuelve traducido al arrayList
	private ArrayList<Vehiculo> readFromFile(String file) throws FileNotFoundException{
		ArrayList<Vehiculo> v=new ArrayList<Vehiculo>();
		File f=new File(file);
		Scanner myReader = new Scanner(f);
	    
		int cont=0;
		String mat, marca, color, fecha;
		mat="";
		marca="";
		color="";
		fecha="";
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			data=filterLine(data);
	        switch(cont) {
	        	case 0:
	        		mat=data;
	        		cont++;
	        		break;
	        	case 1:
	        		marca=data;
	        		cont++;
	        		break;
	        	case 2:
	        		color=data;
	        		cont++;
	        		break;
	        	case 3:
	        		fecha=data;
	        		cont++;
	        		break;
	        	case 4:
	        		v.add(new Vehiculo(mat, marca, color, fecha));
	        		cont=0;
	        		break;
	        }
		}
		myReader.close();
		return v;
	}
	
	//devuelve el texto del fichero detrás del signo =
	private String filterLine(String line) {
		String toret="";
		boolean desp=false;
		
		for(int i=0;i<line.length();i++) {
			if(desp) {
				toret+=line.charAt(i);
			}else {
				if(line.charAt(i)=='=')desp=true;
			}
		}
		return toret;
	}
	
	
	/**getters y setters**/
	/********************************************************************************************/
	public ArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}

	public ArrayList<Vehiculo> getEntradas() {
		return entradas;
	}

	public void setEntradas(ArrayList<Vehiculo> entradas) {
		this.entradas = entradas;
	}

	public ArrayList<Vehiculo> getSalidas() {
		return salidas;
	}

	public void setSalidas(ArrayList<Vehiculo> salidas) {
		this.salidas = salidas;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	
	/**añadir entrada salida y vehiculo**/
	/********************************************************************************************/
	
	//comprueba una nueva entrada y si es viable la crea
	//devuelve un pequeño mensaje con la acción realizada para usar desde fuera
	public String checkAddEntrada(Vehiculo newV) throws IOException, ParseException {
		//si no existe aun lo añade a vehiculos y a entradas
		if(!vehiculoExists(newV)) {
			this.vehiculos.add(newV);
			this.entradas.add(newV);
			updateVehiculosFile(folder+"\\vehiculos.txt", entradas);
			updateVehiculosFile(folder+"\\entradas.txt", entradas);
			return "vehiculo nuevo";
		}
		
		//si ya existe una entrada igual en matricula y fecha lo notifica
		for(Vehiculo v : this.entradas) {
			if(v.getMatricula().equals(newV.getMatricula())&&v.getFecha_mov().equals(newV.getFecha_mov()))return "entrada duplicada";
		}
		
		if(isInside(newV))return "ya esta dentro";
		else {
			this.entradas.add(newV);
			updateVehiculosFile(folder+"\\entradas.txt", entradas);
			newDateOnVehiculo(newV);
			return "hecho";
		}
	}
	
	//comprueba si existe un vehiculo en el registro de vehiculos
	private boolean vehiculoExists(Vehiculo vCheck) {
		for(Vehiculo v : vehiculos) {
			if(v.getMatricula().equals(vCheck.getMatricula()))return true;
		}
		return false;
	}
	
	//comprueba si existe un vehiculo en el registro de vehiculos a partir de su matricula
	public boolean vehiculoExists(String vCheck) {
		for(Vehiculo v : vehiculos) {
			if(v.getMatricula().equals(vCheck))return true;
		}
		return false;
	}
	
	//devuelve true si el vehiculo esta dentro del aparcamiento y false si está fuera
	public boolean isInside(Vehiculo v) throws ParseException {
		boolean encontradoSalida=false;
		for(Vehiculo v2 : salidas) {
			if(v2.getMatricula().equals(v.getMatricula())) {
				encontradoSalida=true;
				break;
			}
		}
		boolean encontradoEntrada=false;
		for(Vehiculo v2 : entradas) {
			if(v2.getMatricula().equals(v.getMatricula())) {
				encontradoEntrada=true;
				break;
			}
		}
		
		if(!encontradoSalida && !encontradoEntrada)return false;
		else if(encontradoEntrada && !encontradoSalida)return true;
		else if(encontradoSalida && !encontradoEntrada)return false;
		else {
			String ultimaFecEntrada="";
			for(Vehiculo v2 : entradas) {
				if(v2.getMatricula().equals(v.getMatricula())) {
					if(ultimaFecEntrada.equals("")||dateIsLater(v2.getFecha_mov(), ultimaFecEntrada))ultimaFecEntrada=v2.getFecha_mov();
				}
			}
			String ultimaFecSalida="";
			for(Vehiculo v2 : salidas) {
				if(v2.getMatricula().equals(v.getMatricula())) {
					if(ultimaFecSalida.equals("")||dateIsLater(v2.getFecha_mov(), ultimaFecSalida))ultimaFecSalida=v2.getFecha_mov();
				}
			}
			
			if(ultimaFecSalida.equals("")&&ultimaFecSalida.equals(""))return false;
			else if(ultimaFecSalida.equals(""))return true;
			else if(ultimaFecEntrada.equals(""))return false;
			else if(dateIsLater(ultimaFecEntrada, ultimaFecSalida))return true;
			else return false;
		}
		
	}
	
	//devuelve true si la primera fecha es mas tarde que la segunda
	private boolean dateIsLater(String dateNew, String dateOld) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		Date dateN = formato.parse(dateNew);
		Date dateO = formato.parse(dateOld);
		
		if(dateN.after(dateO))return true;
		else return false;
	}
	
	//cambia la fecha de una matricula del listado de vehiculos si es mayor que la existente
	private void newDateOnVehiculo(Vehiculo v) throws ParseException, IOException {
		for(int i=0;i<vehiculos.size();i++) {
			Vehiculo v2=vehiculos.get(i);
			if(v2.getMatricula().equals(v.getMatricula())) {
				if(dateIsLater(v.getFecha_mov(), v2.getFecha_mov())) {
					vehiculos.get(i).setFecha_mov(v.getFecha_mov());
					updateVehiculosFile(folder+"\\vehiculos.txt", this.vehiculos);
				}
				break;
			}
		}
	}
	
	//comprueba una nueva salida y si es viable la crea
	//devuelve un pequeño mensaje con la acción realizada para usar desde fuera
	public String checkAddSalida(Vehiculo newV) throws ParseException, IOException {
		if(!vehiculoExists(newV))return "no encontrado";
		if(isInside(newV)) {
			this.salidas.add(newV);
			updateVehiculosFile(folder+"//salidas.txt", salidas);
			newDateOnVehiculo(newV);
			return "hecho";
		}else return "esta fuera";
	}

	//comprueba un nuevo vehiculo y si es viable lo crea
	//devuelve true si no existe y se consigue crear y false si ya existe
	public boolean chechAddVehiculo(Vehiculo newV) throws IOException {
		if(vehiculoExists(newV))return false;
		else {
			this.vehiculos.add(newV);
			updateVehiculosFile(folder+"//vehiculos.txt", vehiculos);
			return true;
		}
	}
	
	
	/********************************************************************************************/
	//pasa el arrayList de vehiculos al listado de vehiculos.txt, el de salidas a salidas.txt o el de entradas a entradas.txt
	private void updateVehiculosFile(String filename, ArrayList<Vehiculo>vs) throws IOException {
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File(filename)));
		for(Vehiculo v : vs) {
			bw.write(v.toStringInFile());
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
	
	
	//borrar vehiculo, entrada y salida
	/********************************************************************************************/
	
	//borra un vehiculo del registro dada su matricula, por lo que también se borra de las entradas y las salidas
	public void deleteVehiculo(String matricula) throws IOException {
		for(int i=0;i<vehiculos.size();i++) {
			if(vehiculos.get(i).getMatricula().equals(matricula))vehiculos.remove(i);
		}
		
		for(int i=0;i<entradas.size();i++) {
			if(entradas.get(i).getMatricula().equals(matricula))entradas.remove(i);
		}
		
		for(int i=0;i<salidas.size();i++) {
			if(salidas.get(i).getMatricula().equals(matricula))salidas.remove(i);
		}
		
		updateVehiculosFile(folder+"\\vehiculos.txt", vehiculos);
		updateVehiculosFile(folder+"\\entradas.txt", entradas);
		updateVehiculosFile(folder+"\\salidas.txt", salidas);
	}
	
	//borra un vehiculo del registro dada su posición, por lo que también se borra de las entradas y las salidas
	public void deleteVehiculo(int pos) throws IOException {
		String matricula=vehiculos.get(pos).getMatricula();
		deleteVehiculo(matricula);
	}
	
	//borra un vehiculo del registro de entradas dada su posición en el array
	public void deleteEntrada(int pos) throws IOException {
		entradas.remove(pos);
		updateVehiculosFile(folder+"\\entradas.txt", entradas);
	}
	
	//borra un vehiculo del registro de entradas dada su posición en el array
	public void deleteSalida(int pos) throws IOException {
		salidas.remove(pos);
		updateVehiculosFile(folder+"\\salidas.txt", salidas);
	}
	
	
	//modificar vehiculo, entrada y salida
	/********************************************************************************************/
	
	//modifica un vehículo dada su posición y un vehiculo nuevo. Además modifica el modelo y la matrícula del vehículo en las entradas y salidas
	public void updateVehiculo(int pos, Vehiculo v) throws IOException {
		vehiculos.set(pos, v);
		updateVehiculosFile(folder+"\\vehiculos.txt", vehiculos);
		
		for(int i=0;i<entradas.size();i++) {
			if(entradas.get(i).getMatricula().equals(v.getMatricula())) {
				Vehiculo v2=new Vehiculo(v);
				v2.setColor(entradas.get(i).getColor());
				v2.setFecha_mov(entradas.get(i).getFecha_mov());
				entradas.set(i, v2);
			}
		}
		updateVehiculosFile(folder+"\\entradas.txt", entradas);
		
		for(int i=0;i<salidas.size();i++) {
			if(salidas.get(i).getMatricula().equals(v.getMatricula())) {
				Vehiculo v2=new Vehiculo(v);
				v2.setColor(salidas.get(i).getColor());
				v2.setFecha_mov(salidas.get(i).getFecha_mov());
				salidas.set(i, v2);
			}
		}
		updateVehiculosFile(folder+"\\salidas.txt", salidas);
		
	}
	
	//modifica una entrada dada su posición y una entrada nueva
	public void updateEntrada(int pos, Vehiculo v) throws IOException {
		entradas.set(pos, v);
		updateVehiculosFile(folder+"\\entradas.txt", vehiculos);
	}
	
	//modifica una salida dada su posición y una salida nueva
	public void updateSalida(int pos, Vehiculo v) throws IOException {
		salidas.set(pos, v);
		updateVehiculosFile(folder+"\\salidas.txt", vehiculos);
	}	

	
	//mostrar vehiculos, entradas y salidas
	/********************************************************************************************/
	
	//muestra todos los vehiculos registrados
	public String showAllVehiculos() {
		if(vehiculos.size()==0)return "No se han encontrado vehiculos registrados";
		String toret="";
		int cont=0;
		for(Vehiculo v : vehiculos) {
			toret+="Posición vehículo: "+cont+"\n";
			toret+=v.toString()+"\n";
			toret+="Ultimo movimiento: "+v.getFecha_mov()+"\n";
			toret+="------------------------------------------------------------\n";
			cont++;
		}
		return toret;
	}
	
	//muestra todas las entradas registradas
	public String showAllEntradas() {
		if(entradas.size()==0)return "No se han encontrado registros de entradas";
		String toret="";
		int cont=0;
		for(Vehiculo v : entradas) {
			toret+="Posición entrada: "+cont+"\n";
			toret+=v.toString()+"\n";
			toret+="Entrada: "+v.getFecha_mov()+"\n";
			toret+="------------------------------------------------------------\n";
			cont++;
		}
		return toret;
	}
	
	//muestra todas las salidas registradas
	public String showAllSalidas() {
		if(salidas.size()==0)return "No se han encontrado registros de salidas";
		String toret="";
		int cont=0;
		for(Vehiculo v : salidas) {
			toret+="Posición salida: "+cont+"\n";
			toret+=v.toString()+"\n";
			toret+="Salida: "+v.getFecha_mov()+"\n";
			toret+="------------------------------------------------------------\n";
			cont++;
		}
		return toret;
	}
	
	public ArrayList<String> showAllMatriculasVehiculos() {
		ArrayList<String>toret=new ArrayList<String>();
		for(Vehiculo v : vehiculos) {
			toret.add(v.getMatricula());
		}
		return toret;
	}
	
	public ArrayList<String> showAllMatriculasFechaSalidas() {
		ArrayList<String>toret=new ArrayList<String>();
		for(Vehiculo v : salidas) {
			toret.add(v.getMatricula()+" "+v.getFecha_mov());
		}
		return toret;
	}
	
	public ArrayList<String> showAllMatriculasFechaEntradas() {
		ArrayList<String>toret=new ArrayList<String>();
		for(Vehiculo v : entradas) {
			toret.add(v.getMatricula()+" "+v.getFecha_mov());
		}
		return toret;
	}
	
	//conseguir un vehiculo a partir de su matrícula
	/********************************************************************************************/
	public Vehiculo getVehiculo(String matricula) {
		for(Vehiculo v : vehiculos) {
			if(v.getMatricula().equals(matricula))return new Vehiculo(v);
		}
		return null;
	}
	
}
