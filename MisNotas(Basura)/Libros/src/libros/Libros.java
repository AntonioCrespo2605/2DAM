package libros;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//clase Libros
@XmlRootElement(name="libros")
@XmlAccessorType (XmlAccessType.FIELD)
public class Libros {
    @XmlElement(name = "libro")
    private List<Libro>libros=null;
    
    //getters y setters
    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
