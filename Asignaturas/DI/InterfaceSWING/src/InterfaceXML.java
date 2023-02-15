import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InterfaceXML extends JFrame implements MouseListener{
	private static final long serialVersionUID = 1L;
	
	private JButton limpiar;
	private JLabel estado, label;
	private JTextArea info;
	private JScrollPane scroll;
	private JMenuBar opciones;
	private JMenu dom, sax, stax, validacion, jaxb;
	private JMenuItem crearNodo, mostrarNodo, anhadirNodo;
	private JMenuItem crearSAX, mostrarSAX, eventosSAX;
	private JMenuItem crearStAX, mostrarStAX, APICursorStAX, APIEventsStAX;
	private JMenuItem dtd, xsd;
	private JPanel panelSuperior;
	
	private Objeto modelo=null;
	private Objeto aux=null;
	private File ficherofocus;
	private boolean trabajable=false;
	
	//private 
	
	public InterfaceXML(){
		super("gestor xml");
		ventana();
		menu();
		panel1();
		area();
		
		setVisible(true);
		File f=new File("almacenXMLs");
		if(!f.exists())f.mkdir();
	}
	
	private void ventana() {
		setSize(670,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBackground(Color.BLACK);
		setIconImage(new ImageIcon("image/icono.png").getImage());
		
	}
	
	//menu de las opciones dom, sax, stax, jaxb y validacion
	private void menu() {
		
		//menubar
        opciones=new JMenuBar();
        setJMenuBar(opciones);

        //jmenus dom sax stax jaxb y validacion
        dom=new JMenu("Gestionar DOM");
        sax=new JMenu("Gestionar SAX");
        stax=new JMenu("Gestionar StAX");
        jaxb=new JMenu("Gestionar JAXB");
        validacion=new JMenu("Validar");
        
        opciones.add(dom);
        opciones.add(sax);
        opciones.add(stax);
        opciones.add(jaxb);
        opciones.add(validacion);

        //submenus de dom
        crearNodo=new JMenuItem("Crear");
        mostrarNodo=new JMenuItem("Mostrar");
        anhadirNodo=new JMenuItem("Añadir Nodo");
        dom.add(crearNodo);
        dom.add(mostrarNodo);
        dom.add(anhadirNodo);
        
        crearNodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                crearEtiquetasDOM(evt);
            }
        });
        
        //submeenus de sax
        crearSAX=new JMenuItem("Crear");
        mostrarSAX=new JMenuItem("Mostrar");
        eventosSAX=new JMenuItem("Eventos");
        sax.add(crearSAX);
        sax.add(mostrarSAX);
        sax.add(eventosSAX);
        
        //submenus de stax
        crearStAX=new JMenuItem("Crear");
        mostrarStAX=new JMenuItem("Mostrar");
        APICursorStAX=new JMenuItem("APICursor");
        APIEventsStAX=new JMenuItem("APIEvents");
        stax.add(crearStAX);
        stax.add(mostrarStAX);
        stax.add(APICursorStAX);
        stax.add(APIEventsStAX);
        
        //submenus de validar
        dtd=new JMenuItem("DTD");
        xsd=new JMenuItem("XSD");
        validacion.add(dtd);
        validacion.add(xsd);
       
        //estilos
        menuStyle();
	}
	
	private void menuStyle() {
		//color del menu
		opciones.setBackground(Color.BLACK);
		
		//f para opciones del menu y f2 para los subitems
		Font f=dom.getFont();
		Font f2=f.deriveFont(15f);
		f=f.deriveFont(18f);
		
		Color darkGray=new Color(25,25,25);
		//fondo, color de letras y tamaño de fuente de las opciones principales
		dom.setForeground(Color.GREEN);
		dom.setBackground(Color.BLACK);
		dom.setOpaque(true);
		dom.addMouseListener(this);
		
		sax.setForeground(Color.GREEN);
		sax.setBackground(darkGray);
		sax.setOpaque(true);
		sax.addMouseListener(this);
		
		stax.setForeground(Color.GREEN);
		stax.setBackground(Color.BLACK);
		stax.setOpaque(true);
		stax.addMouseListener(this);
		
		jaxb.setForeground(Color.GREEN);
		jaxb.setBackground(darkGray);
		jaxb.setOpaque(true);
		jaxb.addMouseListener(this);
		
		validacion.setForeground(Color.GREEN);
		validacion.setBackground(Color.BLACK);
		validacion.setOpaque(true);
		validacion.addMouseListener(this);
		
		dom.setFont(f);
		sax.setFont(f);
		stax.setFont(f);
		jaxb.setFont(f);
		validacion.setFont(f);
		
		//fondo, color de letras y tamaño de fuente de los subitems de dom
		crearNodo.setForeground(Color.GREEN);
		mostrarNodo.setForeground(Color.GREEN);
		anhadirNodo.setForeground(Color.GREEN);
		crearNodo.setBackground(Color.BLACK);
		mostrarNodo.setBackground(darkGray);
		anhadirNodo.setBackground(Color.BLACK);
		crearNodo.setFont(f2);
		mostrarNodo.setFont(f2);
		anhadirNodo.setFont(f2);
		crearNodo.addMouseListener(this);
		mostrarNodo.addMouseListener(this);
		anhadirNodo.addMouseListener(this);
		
		//fondo, color de letras y tamaño de fuente de los subitems de sax
		crearSAX.setForeground(Color.GREEN);
		mostrarSAX.setForeground(Color.GREEN);
		eventosSAX.setForeground(Color.GREEN);
		crearSAX.setBackground(Color.BLACK);
		mostrarSAX.setBackground(darkGray);
		eventosSAX.setBackground(Color.BLACK);
		crearSAX.setFont(f2);
		mostrarSAX.setFont(f2);
		eventosSAX.setFont(f2);
		crearSAX.addMouseListener(this);
		mostrarSAX.addMouseListener(this);
		eventosSAX.addMouseListener(this);
		
		//fondo, color de letras y tamaño de fuente de los subitems de stax
		crearStAX.setForeground(Color.GREEN);
		mostrarStAX.setForeground(Color.GREEN);
		APICursorStAX.setForeground(Color.GREEN);
		APIEventsStAX.setForeground(Color.GREEN);
		crearStAX.setBackground(Color.BLACK);
		mostrarStAX.setBackground(darkGray);
		APICursorStAX.setBackground(Color.BLACK);
		APIEventsStAX.setBackground(darkGray);
		crearStAX.setFont(f2);
		mostrarStAX.setFont(f2);
		APICursorStAX.setFont(f2);
		APIEventsStAX.setFont(f2);
		crearStAX.addMouseListener(this);
		mostrarStAX.addMouseListener(this);
		APICursorStAX.addMouseListener(this);
		APIEventsStAX.addMouseListener(this);
		
		//fondo, color de letras y tamaño de fuente de los subitems de validar
		dtd.setForeground(Color.GREEN);
		xsd.setForeground(Color.GREEN);
		dtd.setBackground(Color.BLACK);
		xsd.setBackground(darkGray);
		dtd.setFont(f2);
		xsd.setFont(f2);
		dtd.addMouseListener(this);
		xsd.addMouseListener(this);
	}

	//panel con boton
	private void panel1() {
		panelSuperior = new JPanel ();
        panelSuperior.setLayout(new FlowLayout());
        
        //labels de texto
		label=new JLabel("ESTADO: ");
		estado=new JLabel("Archivo no seleccionado                              ");
		
		//boton
		limpiar=new JButton("Limpiar");
		
		//estilo del panel
		panel1style();
		
		panelSuperior.add(label);
		panelSuperior.add(estado);
		panelSuperior.add(limpiar);
		add(panelSuperior, BorderLayout.NORTH);
	}
	
	private void panel1style(){
		//cambiar color del panel
		panelSuperior.setBackground(Color.BLACK);
		
		Font f=label.getFont();
		f=f.deriveFont(18f);
		
		//cambiar fondo, color y tamaño de fuente del boton
		limpiar.setBackground(Color.GREEN);
		limpiar.setForeground(Color.BLACK);
		limpiar.setFont(f);
		limpiar.addMouseListener(this);
		
		//cambiar fondo, color y tamaño de fuente de los labels
		label.setForeground(Color.WHITE);
		label.setFont(f);
		
		estado.setForeground(Color.GREEN);
		estado.setFont(f);
	}

	//area de texto con scroll
	private void area() {
		info=new JTextArea();
		scroll=new JScrollPane(info);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		Border b=BorderFactory.createMatteBorder(10,40,40,40,Color.BLACK);
		
		scroll.setBorder(b);
		
		//estilo del area
		areaStyle();
		add(scroll);
	}
	
	private void areaStyle() {
		//color del cursor
		info.setCaretColor(Color.WHITE);
		
		//color de fondo, letras y tamaño del texto de dentro del area
		info.setForeground(Color.GREEN);
		Color darkGray=new Color(18,18,18);
		info.setBackground(darkGray);
		Font f=info.getFont();
		f=f.deriveFont(20f);
		info.setFont(f);
	}
	
	
	 @Override
	 public void mouseEntered(MouseEvent e) {
		 if(e.getSource().equals(limpiar)) {
			 JButton b=(JButton)e.getSource();
			 b.setBackground(Color.RED);
		 }else if(e.getSource().equals(dom)||e.getSource().equals(validacion)||e.getSource().equals(stax)||e.getSource().equals(sax)||e.getSource().equals(jaxb)) {
			 JMenu jm=(JMenu)e.getSource();
			 jm.setBackground(Color.WHITE);
		 }else if(e.getSource() instanceof JMenuItem) {
			 JMenuItem jmi =(JMenuItem)e.getSource();
			 jmi.setBackground(Color.WHITE);
		 }
	 }
	 
	 @Override
	 public void mouseExited(MouseEvent e) {
		 Color darkGray=new Color(25,25,25);
		 
		 if(e.getSource().equals(limpiar)) {
			 JButton b=(JButton)e.getSource();
			 b.setBackground(Color.GREEN);
		 }else if(e.getSource().equals(dom)||e.getSource().equals(validacion)||e.getSource().equals(stax)) {
			 JMenu jm=(JMenu)e.getSource();
			 jm.setBackground(Color.BLACK);
		 }else if(e.getSource().equals(sax)||e.getSource().equals(jaxb)) {
			 JMenu jm=(JMenu)e.getSource();
			 jm.setBackground(darkGray);
		 }else if(e.getSource().equals(mostrarNodo)||e.getSource().equals(mostrarSAX)||e.getSource().equals(mostrarStAX)||e.getSource().equals(APIEventsStAX)||e.getSource().equals(xsd)) {
			 JMenuItem jmi=(JMenuItem)e.getSource();
			 jmi.setBackground(darkGray);
		 }else if(e.getSource() instanceof JMenuItem) {
			 JMenuItem jmi=(JMenuItem)e.getSource();
			 jmi.setBackground(Color.BLACK);
		 }
	 }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	
	//CREAR DOM
	//---------------------------------------------------------------------------------------------
	//acciones
	private void crearEtiquetasDOM(ActionEvent evt) {
		String seleccion = JOptionPane.showInputDialog(this,"Escribe el nombre del fichero a crear",JOptionPane.QUESTION_MESSAGE);
		if(seleccion!=null) {
			seleccion=seleccion.trim();
			if(seleccion=="") {
				JOptionPane.showMessageDialog(null,"El nombre del fichero no puede estar vacío");
			}else {
				File f=new File("almacenXMLs\\"+seleccion+".xml");
				if(f.exists())JOptionPane.showMessageDialog(null,"Ya existe este fichero");
				else {
					try {
						f.createNewFile();
						ficherofocus=f;
						crearEtiquetas();
						f.delete();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,"Ha ocurrido un error al crear el fichero. \nComprueba que los caracteres sean los permitidos por Windows");
					}
				}
			}
		}
	}
	
	private void crearEtiquetas() {
		boolean repeat=true;
		String seleccion=null;
		do {
			repeat=false;
			seleccion = JOptionPane.showInputDialog(this,"¿Qué tipo de objeto deseas almacenar?",JOptionPane.QUESTION_MESSAGE);
			seleccion=seleccion.trim();
			if(seleccion!=null) {
				if(esAlfanumerico(seleccion)) {
					modelo=new Objeto(seleccion);
				}else repeat=true;
			}
		}while(repeat);
		
		if(seleccion!=null) {
			repeat=true;
			do {
				repeat=false;
				seleccion = JOptionPane.showInputDialog(this,"Escribe el nombre de un atributo",JOptionPane.QUESTION_MESSAGE);
				if(seleccion!=null) {
					seleccion=seleccion.trim();
					if(seleccion.equals("")) {
						JOptionPane.showMessageDialog(null,"El nombre del atributo no puede estar vacío");
						repeat=true;
					}else if(!esAlfanumerico(seleccion)) {
						JOptionPane.showMessageDialog(null,"El nombre del atributo debe ser alfanumérico");
						repeat=true;
					}else {
						modelo.addAtributo(seleccion);
					}
				}
			}while(repeat);
			
			if(seleccion!=null) {
				repeat=true;
				do {
					int confirmado = JOptionPane.showConfirmDialog(this, "¿Deseas añadir otro atributo?");
					switch(confirmado) {
					case JOptionPane.OK_OPTION:
						addEtiqueta();
						break;
					default:
						crearXML();
						repeat=false;
						break;
					}
				}while(repeat);
				estado.setText(ficherofocus.getName()+"                             ");
			}
		}
	}
	
	private void addEtiqueta() {
		boolean repeat=true;
		do {
			repeat=false;
			String seleccion = JOptionPane.showInputDialog(this,"Escribe el nombre de un atributo",JOptionPane.QUESTION_MESSAGE);
			if(seleccion!=null) {
				seleccion=seleccion.trim();
				if(seleccion.equals("")) {
					JOptionPane.showMessageDialog(null,"El nombre del atributo no puede estar vacío");
				}else if(esAlfanumerico(seleccion)) {
					JOptionPane.showMessageDialog(null,"El nombre del atributo debe ser alfanumérico");
				}else {
					modelo.addAtributo(seleccion);
				}
			}
		}while(repeat);
	}
	
	private void crearXML() {
		try {
			ficherofocus.createNewFile();
			trabajable=true;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private static boolean esAlfanumerico(String cadena) {
	    return cadena.matches("[a-zA-Z0-9]+");
	}
	
	//AÑADIR
	private void crearNodoDOM(ActionEvent evt) {
		if(trabajable) {
			aux=new Objeto(modelo);
			for(int i=0;i<modelo.getEtNieto().size();i++) {
				String seleccion = JOptionPane.showInputDialog(this,"Rellena el/la"+modelo.getEtNieto().get(i),JOptionPane.QUESTION_MESSAGE);
				if(seleccion==null)seleccion="";
				aux.addRelleno(seleccion);
			}
			saveOnFile();
			mostrarInfo();
		}else {
			JOptionPane.showMessageDialog(null,"Abre un xml existente o crea uno nuevo");
		}
	}
	
	private void saveOnFile() {
		Document document;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			document.setXmlVersion("1.0");
			var etg=document.createElement(modelo.getNombre()+"s");
			document.appendChild(etg);
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	private void mostrarInfo() {
		try {
			Document domDocument =DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ficherofocus.getAbsolutePath());
			modelo=new Objeto(domDocument.getDocumentElement().getTagName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}


}
