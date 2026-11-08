package biblioteca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistroPrestamos {

    private static final RegistroPrestamos INSTANCIA = new RegistroPrestamos();

    private final List<Prestamo> historial = new ArrayList<>();

    private RegistroPrestamos() { }

    public static RegistroPrestamos getInstancia() {
        return INSTANCIA;
    }

    public void registrar(Prestamo prestamo) {
        historial.add(prestamo);
    }

    public List<Prestamo> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}