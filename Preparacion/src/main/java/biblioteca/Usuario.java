package biblioteca;

import java.util.Objects;

public class Usuario {
    private static int contador = 0;

    private final String id;
    private final String nombre;

    public Usuario(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio.");
        }
        this.id = "U" + (++contador);
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        return id.equals(((Usuario) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return nombre + " (" + id + ")";
    }
}
