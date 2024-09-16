package model;

public class Autor {
    private String codigo;
    private String nombre;

    public Autor() {
    }
    public Autor(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Autor:" + "\ncodigo: " + codigo + "\nnombre: " + nombre;
    }
    
    
    
}
