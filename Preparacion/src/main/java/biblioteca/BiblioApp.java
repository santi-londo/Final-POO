package biblioteca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BiblioApp {

    private final Catalogo<Recurso> catalogo = new Catalogo<>();
    private final ServicioPrestamos servicio = new ServicioPrestamos();

    public static void main(String[] args) {
        BiblioApp app = new BiblioApp();
        try {
            app.cargarRecursos();
            app.demostrarReglaAnio1923();
            app.demostrarPrestamo();
            app.demostrarBusquedas();
            app.demostrarOrdenamiento();
            app.demostrarConsultas();
            app.reportarUso();
        } catch (BibliotecaException e) {
            System.out.println("Error inesperado del sistema: " + e.getMessage());
        }
    }

    /** Registra 6 recursos: 2 libros, 2 revistas y 2 digitales. */
    private void cargarRecursos() throws BibliotecaException {
        catalogo.agregar(RecursoFactory.crearLibro("L001", "Cien años de soledad", 1967, true,
                "Gabriel García Márquez", "978-0307474728"));
        // Publicado en 1605: se registra como NO prestable (solo consulta), por la regla de 1923.
        catalogo.agregar(RecursoFactory.crearLibro("L002", "Don Quijote de la Mancha", 1605, false,
                "Miguel de Cervantes", "978-8420412146"));
        catalogo.agregar(RecursoFactory.crearRevista("R001", "Semana", 2024, true,
                2150, "Publicaciones Semana"));
        catalogo.agregar(RecursoFactory.crearRevista("R002", "National Geographic", 2022, true,
                310, "Editorial Televisa"));
        catalogo.agregar(RecursoFactory.crearDigital("D001", "Repositorio de Tesis Universitarias", 2021,
                "https://biblioteca.example.com/tesis"));
        catalogo.agregar(RecursoFactory.crearDigital("D002", "Archivo Histórico Digital", 2018,
                "https://biblioteca.example.com/archivo"));

        UtilRecursos.imprimir("Catálogo cargado", catalogo.obtenerTodos());
    }

    private void demostrarReglaAnio1923() {
        System.out.println("\n=== Regla: nada anterior a 1923 es prestable ===");
        try {
            RecursoFactory.crearLibro("L999", "Libro antiguo", 1900, true, "Anónimo", "000");
        } catch (BibliotecaException e) {
            System.out.println("Error esperado -> " + e.getMessage());
        }
    }

    /** Préstamo completo: préstamo, segundo préstamo (falla) y devolución. */
    private void demostrarPrestamo() {
        System.out.println("\n=== Préstamo completo ===");
        Usuario ana = new Usuario("Ana Torres");
        Usuario luis = new Usuario("Luis Pérez");

        try {
            RecursoFisico libro = (RecursoFisico) catalogo.buscarPorCodigo("L001");

            servicio.prestar(libro, ana);
            System.out.println("1) Préstamo OK -> " + libro + " a " + libro.getUsuarioPrestamo());

            try {
                servicio.prestar(libro, luis);
            } catch (RecursoYaPrestadoException e) {
                System.out.println("2) Segundo préstamo rechazado -> " + e.getMessage());
            }

            servicio.devolver(libro);
            System.out.println("3) Devolución OK -> " + libro);

            // Se deja una revista prestada para que el reporte y los conteos tengan datos.
            RecursoFisico revista = (RecursoFisico) catalogo.buscarPorCodigo("R001");
            servicio.prestar(revista, luis);
            System.out.println("4) Queda prestada -> " + revista);
        } catch (BibliotecaException e) {
            System.out.println("Error en el préstamo: " + e.getMessage());
        }
    }

    private void demostrarBusquedas() {
        System.out.println("\n=== Búsquedas ===");
        // Búsqueda 1: por fragmento de título (sin distinguir mayúsculas).
        UtilRecursos.imprimir("Título que contiene 'CIEN'", catalogo.buscarPorTitulo("CIEN"));

        // Búsqueda 2: por código, con un caso que existe y otro que no.
        try {
            System.out.println("\nBúsqueda código D001 -> " + catalogo.buscarPorCodigo("D001"));
            System.out.println("Búsqueda código X999 -> " + catalogo.buscarPorCodigo("X999"));
        } catch (RecursoNoEncontradoException e) {
            System.out.println("Error esperado -> " + e.getMessage());
        }
    }

    private void demostrarOrdenamiento() {
        UtilRecursos.imprimir("Ordenados por año DESC y título", catalogo.ordenarPorAnioYTitulo());

        // Mismo catálogo usando arreglos (Arrays.sort + Comparator).
        Recurso[] arreglo = UtilRecursos.aArreglo(catalogo.obtenerTodos());
        Arrays.sort(arreglo, Comparadores.POR_TITULO);
        System.out.println("\n--- Con arreglo ordenado por título ---");
        for (Recurso r : arreglo) {
            System.out.println("  " + r.getCodigo() + " - " + r.getTitulo());
        }
    }

    private void demostrarConsultas() {
        System.out.println("\n=== Consultas con streams ===");
        System.out.println("Títulos de libros disponibles: " + catalogo.titulosLibrosDisponiblesEnMayusculas());
        UtilRecursos.imprimir("Disponibles para préstamo", catalogo.listarDisponibles());
        System.out.println("\nLibros prestados: " + catalogo.contarPrestadosPorTipo(Libro.class));
        System.out.println("Revistas prestadas: " + catalogo.contarPrestadosPorTipo(Revista.class));

        // Demostración de wildcards: copiar solo los libros a una lista de Recurso.
        List<Recurso> soloLibros = new ArrayList<>();
        UtilRecursos.copiarLibros(catalogo.obtenerTodos(), soloLibros);
        System.out.println("Libros copiados con wildcard: " + soloLibros.size());
    }


    public void reportarUso() {
        List<Recurso> todos = catalogo.obtenerTodos();

        Map<String, Long> porTipo = todos.stream()
                .collect(Collectors.groupingBy(Recurso::getTipo, TreeMap::new, Collectors.counting()));

        System.out.println("\n==========================================");
        System.out.println("   REPORTE DE USO - Biblioteca Central");
        System.out.println("==========================================");
        System.out.println("Total de recursos en catálogo : " + catalogo.tamanio());
        System.out.println("Recursos creados (contador)   : " + Recurso.getTotalCreados());
        porTipo.forEach((tipo, cantidad) -> System.out.printf("  - %-8s : %d%n", tipo, cantidad));

        System.out.println("Disponibles para préstamo     : " + catalogo.listarDisponibles().size());

        System.out.println("Prestados actualmente:");
        List<RecursoFisico> prestados = todos.stream()
                .filter(r -> r instanceof RecursoFisico rf && rf.getUsuarioPrestamo() != null)
                .map(r -> (RecursoFisico) r)
                .collect(Collectors.toList());
        if (prestados.isEmpty()) {
            System.out.println("  (ninguno)");
        } else {
            prestados.forEach(r -> System.out.println("  - " + r.getCodigo() + " \"" + r.getTitulo()
                    + "\" -> " + r.getUsuarioPrestamo()));
        }

        List<Prestamo> historial = RegistroPrestamos.getInstancia().getHistorial();
        System.out.println("Préstamos históricos registrados: " + historial.size());
        historial.forEach(p -> System.out.println("  - " + p.fecha() + " | " + p.codigoRecurso()
                + " | " + p.usuario()));
        System.out.println("==========================================");
    }
}
