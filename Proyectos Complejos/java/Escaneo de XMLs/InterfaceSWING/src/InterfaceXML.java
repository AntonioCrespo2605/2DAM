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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
	private ArrayList<Objeto>objetos;
 
	
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
        crearNodo=new JMenuItem("Crear xml");
        mostrarNodo=new JMenuItem("Mostrar xml");
        anhadirNodo=new JMenuItem("Añadir Nodo");
        dom.add(crearNodo);
        dom.add(mostrarNodo);
        dom.add(anhadirNodo);
        
        anhadirNodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				crearNodoDOM(evt);
			}
		});
        
        crearNodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                crearEtiquetasDOM(evt);
            }
        });
        
        mostrarNodo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarNuevo(e);
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
		limpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				info.setText("");
			}
		});
		
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
	//crea el fichero
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
						objetos=new ArrayList<>();
						f.delete();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,"Ha ocurrido un error al crear el fichero. \nComprueba que los caracteres sean los permitidos por Windows");
					}
				}
			}
		}
	}
	
	//crea el modelo a partir de las preferencias del usuario
	private void crearEtiquetas() {
		boolean repeat=true;
		String seleccion=null;
		do {
			repeat=false;
			seleccion = JOptionPane.showInputDialog(this,"¿Qué tipo de objeto deseas almacenar?",JOptionPane.QUESTION_MESSAGE);
			seleccion=seleccion.trim();
			if(seleccion!=null) {
				if(alfabetico(seleccion)) {
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
					}else if(!alfabetico(seleccion)) {
						JOptionPane.showMessageDialog(null,"El nombre del atributo debe ser alfanumérico");
						repeat=true;
					}else if(repe(seleccion)) {
						JOptionPane.showMessageDialog(null,"El nombre de la etiqueta esta repetida");
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
	
	//metodo auxiliar para crear etiquetas
	private void addEtiqueta() {
		boolean repeat=true;
		do {
			repeat=false;
			String seleccion = JOptionPane.showInputDialog(this,"Escribe el nombre de un atributo",JOptionPane.QUESTION_MESSAGE);
			if(seleccion!=null) {
				seleccion=seleccion.trim();
				if(seleccion.equals("")) {
					JOptionPane.showMessageDialog(null,"El nombre del atributo no puede estar vacío");
				}else if(!alfabetico(seleccion)) {
					JOptionPane.showMessageDialog(null,"El nombre del atributo debe ser alfanumérico");
				}else if(repe(seleccion)) {	
					JOptionPane.showMessageDialog(null,"Etiqueta repetida");
				}else {
					modelo.addAtributo(seleccion);
				}
			}
		}while(repeat);
	}
	
	//metodo auxiliar para crear etiquetas
	private void crearXML() {
		try {
			ficherofocus.createNewFile();
			trabajable=true;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	//comprueba que una cadena sea alfabetica
	private static boolean alfabetico(String cadena) {
	    return cadena.matches("[a-zA-Z]+");
	}
	
	private boolean repe(String s) {
		if(modelo.getNombre()!=null) {
			if(modelo.getNombre().equals(s))return true;
		}
		if(modelo.getEtNieto()!=null) {
			for(int i=0;i<modelo.getCantNietos();i++) {
				if(modelo.getEtNieto().get(i).equals(s))return false;
			}
		}
		
		return false;
	}
	//AÑADIR NODO
	private void crearNodoDOM(ActionEvent evt) {
		if(trabajable) {
			aux=new Objeto(modelo);
			ArrayList <String> rel=new ArrayList<String>();
			for(int i=0;i<modelo.getEtNieto().size();i++) {
				String seleccion = JOptionPane.showInputDialog(this,"Rellena el/la "+modelo.getEtNieto().get(i),JOptionPane.QUESTION_MESSAGE);
				if(seleccion==null)seleccion="";
				rel.add(seleccion);
			}
			aux.setRelleno(rel);
			objetos.add(aux);
			saveOnFile();
			mostrarInfo();
		}else {
			JOptionPane.showMessageDialog(null,"Abre un xml existente o crea uno nuevo");
		}
	}
	
	//muestra la informacion leyendo del fichero y parseando con ayuda del modelo
	private void mostrarInfo() {
		String mostrar="";
		try {
			Document domDocument =DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ficherofocus.getAbsolutePath());
			NodeList obHijo=domDocument.getElementsByTagName(modelo.getNombre());
			
			for(int i=0;i<obHijo.getLength();i++) {
				Node n=obHijo.item(i);
				if(n.getNodeType()==Node.ELEMENT_NODE) {
					Element element=(Element)n;
					mostrar+="\n------------------\n"+modelo.getNombre();
					for(int j=0;j<modelo.getCantNietos();j++) {
						mostrar+="\n"+modelo.getEtNieto().get(j)+": "+element.getElementsByTagName(modelo.getEtNieto().get(j)).item(0).getTextContent();
					}
				}
			}
			info.setText(mostrar);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	//pasa la informacion del array auxiliar al fichero
	private void saveOnFile() {
			Document document;
			try {
				document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				document.setXmlVersion("1.0");
				var etg=document.createElement(modelo.getNombre()+"s");
				document.appendChild(etg);
				for(int j=0;j<objetos.size();j++) {
					Element child=document.createElement(modelo.getNombre());
					etg.appendChild(child);
					for(int i=0;i<aux.getCantNietos();i++) {
						Element e=document.createElement(objetos.get(j).getEtNieto().get(i));
						e.appendChild(document.createTextNode(objetos.get(j).getRelleno().get(i)));
						child.appendChild(e);
					}
				}
				
				
				try {
					DOMSource domSource =new DOMSource(document);
					StreamResult result=new StreamResult(ficherofocus.getAbsolutePath());
					Transformer transformer;
					
					transformer = TransformerFactory.newInstance().newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.transform(domSource, result);
				} catch (TransformerConfigurationException e) {
					e.printStackTrace();
				} catch (TransformerFactoryConfigurationError e) {
					e.printStackTrace();
				} catch (TransformerException e) {
					e.printStackTrace();
				}
				
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
	
	//MOSTRAR
	//---------------------------------------------------------------------------------------------
	private void mostrarNuevo(ActionEvent evt) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
		     "xml files (*.xml)", "xml");
		chooser.setDialogTitle("Elije tu xml");
		chooser.setFileFilter(xmlfilter);
		chooser.showSaveDialog(this);
		
		File f=chooser.getSelectedFile();
		if(f!=null) {
			ficherofocus=f;
			estado.setText(ficherofocus.getName()+"                             ");
			leerInfo();
			mostrarInfo();
		}
		
	}
	
	//parsea la informacion del fichero a un objeto modelo
	private void leerInfo() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		ArrayList <String> etiquetas=new ArrayList<String>();
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(ficherofocus);
			Node padre=doc.getDocumentElement();
			String aux="";
			for(int i=0;i<padre.getNodeName().length()-1;i++) {
				aux+=padre.getNodeName().charAt(i);
			}
			Node hijo=doc.getElementsByTagName(aux).item(0);
			modelo=new Objeto(aux);
			
			/*No funciona*/
			NodeList nietos=hijo.getChildNodes();
			ArrayList<String>et=new ArrayList<String>();
			for(int i=0;i<nietos.getLength();i++) {
				if(i%2!=0)et.add(nietos.item(i).getNodeName());
			}
			
			modelo.setEtNieto(et);
			trabajable=true;
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}catch(SAXException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
