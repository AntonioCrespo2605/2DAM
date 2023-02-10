
package libros;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//clase libro
@XmlRootElement(name = "libro")
@XmlAccessorType (XmlAccessType.FIELD)
public class Libro {
    private int id, anho;
    private String titulo, autor;
    
    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    //toString
    @Override
    public String toString() {
        return "Id: " + id + "\nAño: " + anho + "\nNombre: " + titulo + "\nAutor: " + autor;
    }
    
}
