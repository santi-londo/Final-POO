package biblioteca;

import java.time.LocalDate;


public record Prestamo(String codigoRecurso, Usuario usuario, LocalDate fecha) { }
