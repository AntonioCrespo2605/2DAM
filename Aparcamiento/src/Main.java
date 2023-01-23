import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class Main {
	
	//controlador 
	static VehiculoController vc;
	
	public static void main(String[] args){
		
		try {
			vc=new VehiculoController("registro");
		} catch (IOException e) {
			showError(e.getMessage());
			e.printStackTrace();
		}
			
		boolean repeat=true;
		
		//bucle del menú principal
		while(repeat) {
			int seleccion=menu(new Object[] { "Borrar registro", "Modificar registro", "Añadir registro", "Mostrar registro", "Gestiones avanzadas", "Salir" }, "Elije una gestión", "Menú");
		
			switch(seleccion) {
				case -1:
				case 5:
					repeat=false;
					break;
				case 0:borrar();
					break;
				case 1:modificar();//sin hacer
					break;
				case 2:anhadir();
					break;			
				case 3:mostrar();
					break;
				case 4:avanzadas();//sin hacer
					break;
			}

		}
		
	}
	
	//metodo de menu para mostrar
	private static void mostrar() {
		boolean repeat=true;
		
		while(repeat) {
			
			int seleccion=menu(new Object[] { "Mostrar vehículos registrados", "Mostrar todas las entradas", "Mostrar todas las salidas", "Cancelar" },"Elije que registro mostrar", "Mostrar");
		
			switch(seleccion) {
				case -1:
				case 3:
					repeat=false;
					break;
				case 0:JOptionPane.showMessageDialog(null,vc.showAllVehiculos());
					break;
				case 1:JOptionPane.showMessageDialog(null,vc.showAllEntradas());
					break;
				case 2:JOptionPane.showMessageDialog(null,vc.showAllSalidas());
					break;			
			}

		}	
	}
	
	//menu de añadir
	private static void anhadir() {
		boolean repeat=true;
		
			while(repeat) {
				repeat=true;
				int seleccion=menu(new Object[]{"registrar nuevo vehículo","registrar entrada","registrar salida", "salir"}, "Elije que registro deseas añadir", "Añadir");
				switch(seleccion) {
				case -1:
				case 3:
					repeat=false;
					break;
				case 0:anhadirVehiculo();
					break;
				case 1:anhadirEntrada();
					break;
				case 2:anhadirSalida();
					break;			
			}
		}
	}

	//añadir vehiculo
	private static void anhadirVehiculo() {
		boolean existe=true;
		String matr="";
		while(existe) {
			existe=false;
			matr=formulary("Escribe la matrícula del nuevo vehículo");
			if(matr!=null) {
				matr=matr.toUpperCase();
				if(!isAlfanumerico(matr)) {
					showError("La matricula solo puede contener caracteres alfanuméricos");
					existe=true;
				}
				if(vc.vehiculoExists(matr)) {
					existe=true;
					showError("El vehículo con la matrícula "+matr+" ya existe. "
							+ "\nSi desea cambiar algún parámetro hágalo desde el menú de modificar");
				}
			}
		}
		
		
		if(matr!=null) {
			boolean repeat=true;
			String marca="";
			while(repeat) {
				repeat=false;
				marca=formulary("Escribe la marca");
				if(marca!=null) {
					if(marca.equals("")) {
						repeat=true;
						showError("La marca no puede estar vacía");
					}else if(!isAlfanumerico(marca)) {
						repeat=true;
						showError("La marca solo puede contener caracteres alfanuméricos");
					}
				}
			}
			
			if(marca!=null) {
				String color="";
				repeat=true;
				while(repeat) {
					repeat=false;
					color=formulary("Escribe el color");
					if(color!=null) {
						if(color.equals("")) {
							repeat=true;
							showError("El color no puede estar vacío");
						}else if(!isAlfanumerico(color)) {
							showError("El color solo puede contener caracteres alfanumericos");
							repeat=true;
						}
					}
				}
				
				if(color!=null) {
					try {
						vc.chechAddVehiculo(new Vehiculo(matr, marca, color, fechaActual()));
					} catch (IOException e) {
						showError("Ha ocurrido un error al añadir el vehículo\n"+e.getMessage());
					}
				}
			}
			
		}
		
	}
	
	//añadir entrada
	private static void anhadirEntrada() {
		
		boolean repeat=true;
		
		do {
			String mat=formulary("Matricula");
			if(mat!=null){
				if(isAlfanumerico(mat)){
					Vehiculo v=new Vehiculo();
					if(vc.vehiculoExists(mat)) {
						v=new Vehiculo(vc.getVehiculo(mat));
						try {
							if(vc.isInside(v)) {
								showError("El vehículo ya está dentro del aparcamiento, no puede registrarse una nueva entrada");
							}else {
								v.setFecha_mov(fechaActual());
								try {
									vc.checkAddEntrada(v);
									repeat=false;
									showError("Entrada registrada con éxito");
								} catch (IOException e) {
									showError("Ha ocurrido un error al introducir el vehículo en el registro de entradas\n"+e.getMessage());
								}
							}
						}catch(ParseException e) {
							showError("Ha ocurrido un error al comprobar si el vehículo está dentro\n"+e.getMessage());
						}
					}else {
						v.setMatricula(mat);
						showError("Ya que el vehículo no está registrado rellene los siguientes campos");
						boolean repeat2=true;
						String marca="";
						while(repeat2) {
							repeat2=false;
							marca=formulary("Escribe la marca");
							if(marca!=null) {
								if(marca.equals("")) {
									repeat2=true;
									showError("La marca no puede estar vacía");
								}else if(!isAlfanumerico(marca)) {
									repeat2=true;
									showError("La marca solo puede contener caracteres alfanuméricos");
								}
							}
						}
						
						if(marca!=null) {
							v.setMarca(marca);
							String color="";
							repeat2=true;
							while(repeat2) {
								repeat2=false;
								color=formulary("Escribe el color");
								if(color!=null) {
									if(color.equals("")) {
										repeat2=true;
										showError("El color no puede estar vacío");
									}else if(!isAlfanumerico(color)) {
										showError("El color solo puede contener caracteres alfanumericos");
										repeat2=true;
									}
								}
							}
							
							if(color!=null) {
								v.setColor(color);
								try {
									v.setFecha_mov(fechaActual());
									vc.checkAddEntrada(v);
									repeat=false;
								} catch (ParseException e) {
									showError("Ha ocurrido un error al añadir el vehículo\n"+e.getMessage());
								}catch(IOException e) {
									showError("Ha ocurrido un error al añadir el vehículo\n"+e.getMessage());
								}
							}else repeat=false;
						}else repeat=false;
						//
						
					}
					
					
				}else {
					showError("La matrícula debe ser alfanumérica");
				}
			}else repeat=false;
		}while(repeat);
		
	}
	
	//añadir salida
	private static void anhadirSalida() {
		
	}

	//
	private static void modificar() {
		
	}

	//menu general de borrar
	private static void borrar() {
		boolean repeat=true;
		
		while(repeat) {
			
			int seleccion=menu(new Object[] { "Borrar vehículo registrado", "Borrar entrada", "Borrar salida", "Cancelar" }, "Elije que tipo registro borrar", "Borrar");
		
			switch(seleccion) {
				case -1:
				case 3:
					repeat=false;
					break;
				case 0:borrarVehiculo();
					break;
				case 1:borrarEntrada();;
					break;
				case 2:borrarSalida();
					break;			
			}

		}
	}
	
	//menu de borrar vehiculo
	private static void borrarVehiculo() {
		ArrayList<String>vehiculos=vc.showAllMatriculasVehiculos();
		Object[] options = vehiculos.toArray();
    	
    	Object seleccion = JOptionPane.showInputDialog(
    			   null, "Selecciona matricula", "Borrar",
    			   JOptionPane.QUESTION_MESSAGE,null, 
    			   options, 
    			   "opcion 1");

    	if(vehiculos.indexOf(seleccion)!=-1) {
    		try {
				vc.deleteVehiculo(vehiculos.indexOf(seleccion));
			} catch (IOException e) {
				showError("Ha ocurrido un error al borrar el vehiculo "+seleccion+"\n"+ e.getMessage());
			}
    	}
    	
	}
	
	//menu de borrar entrada
	private static void borrarEntrada() {
		ArrayList<String>entradas=vc.showAllMatriculasFechaEntradas();
		Object[] options = entradas.toArray();
    	
    	Object seleccion = JOptionPane.showInputDialog(
    			   null, "Selecciona entrada", "Borrar",
    			   JOptionPane.QUESTION_MESSAGE,null, 
    			   options, 
    			   "opcion 1");

    	if(entradas.indexOf(seleccion)!=-1) {
    		try {
				vc.deleteEntrada(entradas.indexOf(seleccion));
			} catch (IOException e) {
				showError("Ha ocurrido un error al borrar la entrada "+seleccion+"\n"+ e.getMessage());
			}
    	}
    	
	}
	
	//borrar salida
	private static void borrarSalida() {
		ArrayList<String>salidas=vc.showAllMatriculasFechaSalidas();
		Object[] options = salidas.toArray();
    	
    	Object seleccion = JOptionPane.showInputDialog(
    			   null, "Selecciona salida", "Borrar",
    			   JOptionPane.QUESTION_MESSAGE,null, 
    			   options, 
    			   "opcion 1");

    	if(salidas.indexOf(seleccion)!=-1) {
    		try {
				vc.deleteSalida(salidas.indexOf(seleccion));
			} catch (IOException e) {
				showError("Ha ocurrido un error al borrar la salida "+seleccion+"\n"+ e.getMessage());
			}
    	}
    	
	}
	
	//opciones de gestión avanzadas
	private static void avanzadas() {
		
	}

	
	/*Funciones para checkeos y jOptions*/
	
	//muestra un dialogo de error
	private static void showError(String s) {
		JOptionPane.showMessageDialog(null,s);
	}
	
	//devuelve el campo rellenado por el formulario
	private static String formulary(String titulo) {
		String seleccion = JOptionPane.showInputDialog(null, titulo, JOptionPane.QUESTION_MESSAGE);	        
		return seleccion;
	}
	
	//recibe un objeto con las opciones y devuelve el botón seleccionado
	private static int menu(Object[]o, String orden, String titulo) {
		int seleccion = JOptionPane.showOptionDialog(null, orden, 
				titulo, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, o, "Añadir registro");
		
		return seleccion;	
	}
	
	//comprueba si una string tiene formato de letras y numeros
	private static boolean isAlfanumerico(String s) {
		if (s == null) return false;
	    s=s.trim();
	    if(s.equals(""))return false;
	    int len = s.length();
	    for (int i = 0; i < len; i++) {
	         if ((!Character.isLetterOrDigit(s.charAt(i)))) return false;
	    }
	    
	    return true;
	}
	
	//fecha actual
	private static String fechaActual() {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());
 
        return date;
    }

}
