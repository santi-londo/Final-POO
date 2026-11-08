package biblioteca;


public enum EstadoDisponibilidad {
    DISPONIBLE("Disponible"),
    PRESTADO("Prestado");

    private final String descripcion;

    EstadoDisponibilidad(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}