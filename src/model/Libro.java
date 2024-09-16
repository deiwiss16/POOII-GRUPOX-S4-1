package model;

public class Libro {
    private String isbn;
    private String titulo;
    private Autor autor;
    private String sinopsis;
    private String genero;
    private boolean leido;
    private boolean loTengo;

    public Libro() {
    }

    public Libro(String isbn, String titulo, Autor autor, String sinopsis, String genero, boolean leido, boolean loTengo) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.leido = leido;
        this.loTengo = loTengo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public boolean isLoTengo() {
        return loTengo;
    }

    public void setLoTengo(boolean loTengo) {
        this.loTengo = loTengo;
    }

    @Override
    public String toString() {
        return "Libro: \nisbn:" + isbn + "\ntitulo:" + titulo + "\nautor:" + autor + "\nsinopsis:" + sinopsis + "\ngenero:" + genero + "\nleido:" + leido + "\nloTengo:" + loTengo;
    }    
}
