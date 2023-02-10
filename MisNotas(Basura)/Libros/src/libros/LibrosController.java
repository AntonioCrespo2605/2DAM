package libros;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

//controlador
public class LibrosController {
    private List <Libro> ls;
    private String file;
    
    //constructor al que le llega el nombre del fichero
    public LibrosController(String filename) throws JAXBException{
        this.file=filename;
        JAXBContext jaxbContext = JAXBContext.newInstance(Libros.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Libros libros = (Libros) jaxbUnmarshaller.unmarshal( new File(filename));
        this.ls=libros.getLibros();
    }
    
    //FUNCIONALIDADES
    
        //añade un libro a la lista y al fichero xml
    public boolean anhadirLibro(Libro l) throws JAXBException{
        if(!existeLibro(l)){
            ls.add(l);
            saveOnFile();
            return true;
        }else return false;
    }
    
        //borra un libro de la lista y del fichero xml
    public boolean borrarLibro(Libro l) throws JAXBException{
        if(existeLibro(l)){
            ls.remove(l);
            saveOnFile();
            return true;
        }else return false;
    }
    
        //modifica un libro en la lista y en el fichero
    public String modificarLibro(Libro nuevo, Libro viejo){
        if(existeLibro(viejo)){
            if(!existeLibro(nuevo)){
                ls.set(getPosition(viejo), nuevo);
                return "hecho";
            }else return "libro nuevo repetido";
        }else return "libro viejo no encontrado";
    }
    
    
    // CODIGO AUXILIAR
    
        //guarda la lista de libros en el fichero con formato xml
    private void saveOnFile() throws JAXBException{
        JAXBContext jaxbContext = JAXBContext.newInstance(Libros.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(getLs(), System.out);
        jaxbMarshaller.marshal(getLs(), new File(getFile()));
    }
    
        //devuelve la posicion de un libro
    private int getPosition(Libro l){
        for(int i=0;i<ls.size();i++){
            if(ls.get(i).getId()==l.getId())return i;
        }
        return -1;
    }
    
        //devuelve true si ya hay un libro con ese mismo id
    private boolean existeLibro(Libro l){
        for(Libro l2 : getLs()){
            if(l.getId() == l2.getId())return true;
        }
        return false;
    }

    //GETTERS Y SETTERS
    public List<Libro> getLs() {
        return ls;
    }

    public void setLs(List<Libro> ls) {
        this.ls = ls;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    //TOSTRING
        //muestra todos los libros
    @Override
    public String toString(){
        String toret="-------------------------------------";
        
        for(Libro l : ls){
            toret+="\n"+l.toString();
            toret+="\n-------------------------------------";
        }
        return toret;
    }
    
}
