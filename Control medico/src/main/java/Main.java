import conexion.ConexionBD;
import dao.*;
import modelo.*;
import servicio.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Punto de entrada de la aplicación. Arma la conexión, los DAOs y los
 * Servicios (inyección manual de dependencias), y maneja el menú de
 * consola descrito en el enunciado.
 *
 * NOTA: esta clase intencionalmente NO contiene SQL ni lógica de
 * negocio propia — solo pide datos por consola y delega en los
 * Servicios. Es la capa de "Vista + Controlador" en este proyecto.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // Servicios (armados una sola vez, se reutilizan en todo el programa)
    private static ServicioMedico servicioMedico;
    private static ServicioCita servicioCita;
    private static ServicioActividad servicioActividad;
    private static ServicioMedicamento servicioMedicamento;
    private static PerfilDAO perfilDAO;

    public static void main(String[] args) {
        inicializarDependencias();

        try {
            Perfil perfilActivo = menuSeleccionarOCrearPerfil();
            if (perfilActivo != null) {
                menuPrincipal(perfilActivo);
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        }

        System.out.println("Programa finalizado.");
    }

    /**
     * Arma manualmente todas las dependencias (Connection -> DAO -> Servicio).
     * Esto es lo que un framework como Spring haría automáticamente;
     * acá se hace a mano a propósito, para entender cada paso.
     */
    private static void inicializarDependencias() {
        ConexionBD conexionBD = new ConexionBD();

        perfilDAO = new PerfilDAOImpl(conexionBD);
        MedicoDAO medicoDAO = new MedicoDAOImpl(conexionBD);
        CitaDAO citaDAO = new CitaDAOImpl(conexionBD);
        ActividadDAO actividadDAO = new ActividadDAOImpl(conexionBD);
        ActividadRegistroDAO actividadRegistroDAO = new ActividadRegistroDAOImpl(conexionBD);
        MedicamentoDAO medicamentoDAO = new MedicamentoDAOImpl(conexionBD);
        FrecuenciaDiariaDAO frecuenciaDiariaDAO = new FrecuenciaDiariaDAOImpl(conexionBD);
        FrecuenciaDAO frecuenciaDAO = new FrecuenciaDAOImpl(conexionBD);
        MedicamentoAsignadoDAO medicamentoAsignadoDAO = new MedicamentoAsignadoDAOImpl(conexionBD);
        RegistroDAO registroDAO = new RegistroDAOImpl(conexionBD);

        servicioMedico = new ServicioMedico(medicoDAO);
        servicioCita = new ServicioCita(citaDAO);
        servicioActividad = new ServicioActividad(actividadDAO, actividadRegistroDAO);
        servicioMedicamento = new ServicioMedicamento(
                medicamentoDAO, frecuenciaDiariaDAO, frecuenciaDAO, medicamentoAsignadoDAO, registroDAO);
    }

    // ==================== SELECCIÓN / CREACIÓN DE PERFIL ====================

    private static Perfil menuSeleccionarOCrearPerfil() throws SQLException {
        while (true) {
            List<Perfil> perfiles = perfilDAO.listarTodos();

            System.out.println("\n=== PERFILES DISPONIBLES ===");
            if (perfiles.isEmpty()) {
                System.out.println("(No hay perfiles creados todavía)");
            } else {
                for (Perfil p : perfiles) {
                    System.out.println(p.getIdPerfil() + ". " + p);
                }
            }

            System.out.println("\na. Crear perfil");
            System.out.println("b. Seleccionar perfil");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "a":
                    crearPerfil();
                    break;
                case "b":
                    System.out.print("Ingrese el id del perfil: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    Perfil seleccionado = perfilDAO.buscarPorId(id);
                    if (seleccionado == null) {
                        System.out.println("No existe un perfil con ese id.");
                    } else {
                        return seleccionado;
                    }
                    break;
                case "0":
                    return null;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void crearPerfil() throws SQLException {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.println("Relación: ");
        for (Relacion r : Relacion.values()) {
            System.out.println((r.ordinal() + 1) + ". " + r);
        }
        System.out.print("Opción: ");
        int opcionRelacion = Integer.parseInt(scanner.nextLine().trim());
        Relacion relacion = Relacion.values()[opcionRelacion - 1];

        System.out.print("Email (puede dejarse en blanco): ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) email = null;

        Perfil perfil = new Perfil(0, nombre, relacion, email);
        perfilDAO.crear(perfil);
        System.out.println("Perfil creado correctamente.");
    }

    // ==================== MENÚ PRINCIPAL ====================

    private static void menuPrincipal(Perfil perfil) throws SQLException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ PRINCIPAL (" + perfil.getNombre() + ") ===");
            System.out.println("1. Administrar medicamentos");
            System.out.println("2. Administrar médicos");
            System.out.println("3. Administrar citas médicas");
            System.out.println("4. Administrar actividad física");
            System.out.println("0. Volver / Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1": menuMedicamentos(perfil); break;
                case "2": menuMedicos(); break;
                case "3": menuCitas(perfil); break;
                case "4": menuActividad(perfil); break;
                case "0": salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    // ==================== 1. MEDICAMENTOS ====================

    private static void menuMedicamentos(Perfil perfil) throws SQLException {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Medicamentos ---");
            System.out.println("1.1 Listar medicamentos activos");
            System.out.println("1.2 Añadir medicina");
            System.out.println("1.4 Registrar toma");
            System.out.println("1.5 Recarga de medicina");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1.1":
                    List<MedicamentoAsignado> lista = servicioMedicamento.listarMedicamentosDePerfil(perfil.getIdPerfil());
                    if (lista.isEmpty()) System.out.println("(Sin medicamentos asignados)");
                    for (MedicamentoAsignado ma : lista) {
                        System.out.println(ma.getIdMedicamentoAsignado() + ". " + ma);
                    }
                    break;
                case "1.2":
                    anadirMedicina(perfil);
                    break;
                case "1.4":
                    registrarToma(perfil);
                    break;
                case "1.5":
                    recargarMedicina(perfil);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void anadirMedicina(Perfil perfil) throws SQLException {
        System.out.print("Nombre del medicamento: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Cantidad disponible (inventario inicial): ");
        int cantidadDisp = Integer.parseInt(scanner.nextLine().trim());

        Medicamento medicamento = new Medicamento(0, nombre, cantidadDisp);
        servicioMedicamento.agregarMedicamentoAlCatalogo(medicamento);

        // El DAO de Medicamento no devuelve el id generado todavía;
        // lo buscamos por nombre entre los recién listados como forma simple.
        Medicamento creado = servicioMedicamento.listarCatalogoMedicamentos().stream()
                .filter(m -> m.getNombre().equals(nombre))
                .reduce((first, second) -> second) // el último insertado
                .orElseThrow();

        System.out.println("Presentación:");
        for (Presentacion p : Presentacion.values()) {
            System.out.println((p.ordinal() + 1) + ". " + p);
        }
        int opcionPres = Integer.parseInt(scanner.nextLine().trim());
        Presentacion presentacion = Presentacion.values()[opcionPres - 1];

        System.out.print("Tipo de frecuencia (ej: 'cada dia', 'cada 2 dias'): ");
        String tipoFrecuencia = scanner.nextLine().trim();
        System.out.print("Frecuencia diaria (ej: 'una vez al dia'): ");
        String tipoFrecuenciaDiaria = scanner.nextLine().trim();

        System.out.print("Cantidad inicial asignada: ");
        int cantidad = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Cantidad mínima (umbral de recordatorio): ");
        int cantidadMinima = Integer.parseInt(scanner.nextLine().trim());

        FrecuenciaDiaria frecuenciaDiaria = new FrecuenciaDiaria(0, tipoFrecuenciaDiaria);
        Frecuencia frecuencia = new Frecuencia(0, tipoFrecuencia, frecuenciaDiaria);

        MedicamentoAsignado medicamentoAsignado = new MedicamentoAsignado(
                0, creado, presentacion, frecuencia, cantidad, 0, cantidadMinima);

        servicioMedicamento.asignarMedicamentoAPerfil(
                perfil.getIdPerfil(), medicamentoAsignado, frecuenciaDiaria, frecuencia);

        System.out.println("Medicamento asignado correctamente.");
    }

    private static void registrarToma(Perfil perfil) throws SQLException {
        List<MedicamentoAsignado> lista = servicioMedicamento.listarMedicamentosDePerfil(perfil.getIdPerfil());
        if (lista.isEmpty()) {
            System.out.println("Este perfil no tiene medicamentos asignados.");
            return;
        }
        for (MedicamentoAsignado ma : lista) {
            System.out.println(ma.getIdMedicamentoAsignado() + ". " + ma);
        }
        System.out.print("Seleccione el medicamento asignado (id): ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        MedicamentoAsignado seleccionado = lista.stream()
                .filter(m -> m.getIdMedicamentoAsignado() == id)
                .findFirst().orElse(null);
        if (seleccionado == null) {
            System.out.println("No válido.");
            return;
        }

        Registro registro = new Registro(0, LocalDate.now(), LocalTime.now());
        servicioMedicamento.registrarToma(seleccionado, registro);
        System.out.println("Toma registrada.");

        if (servicioMedicamento.necesitaRecordatorioDeRecarga(seleccionado)) {
            System.out.println("⚠ Atención: este medicamento está por debajo del umbral mínimo. Considere recargar.");
        }
    }

    private static void recargarMedicina(Perfil perfil) throws SQLException {
        List<MedicamentoAsignado> lista = servicioMedicamento.listarMedicamentosDePerfil(perfil.getIdPerfil());
        if (lista.isEmpty()) {
            System.out.println("Este perfil no tiene medicamentos asignados.");
            return;
        }
        for (MedicamentoAsignado ma : lista) {
            System.out.println(ma.getIdMedicamentoAsignado() + ". " + ma);
        }
        System.out.print("Seleccione el medicamento asignado (id): ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        MedicamentoAsignado seleccionado = lista.stream()
                .filter(m -> m.getIdMedicamentoAsignado() == id)
                .findFirst().orElse(null);
        if (seleccionado == null) {
            System.out.println("No válido.");
            return;
        }
        System.out.print("Cantidad a agregar: ");
        int cantidad = Integer.parseInt(scanner.nextLine().trim());
        servicioMedicamento.recargarMedicamento(seleccionado, cantidad);
        System.out.println("Recarga realizada.");
    }

    // ==================== 2. MÉDICOS ====================

    private static void menuMedicos() throws SQLException {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Médicos ---");
            System.out.println("2.1 Listado de médicos");
            System.out.println("2.2 Añadir médico");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "2.1":
                    List<Medico> medicos = servicioMedico.listarMedicos();
                    if (medicos.isEmpty()) System.out.println("(Sin médicos registrados)");
                    for (Medico m : medicos) {
                        System.out.println(m.getIdMedico() + ". " + m + " - Tel: " + m.getTelefono());
                    }
                    break;
                case "2.2":
                    anadirMedico();
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void anadirMedico() throws SQLException {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.println("Especialidad:");
        for (Especialidad e : Especialidad.values()) {
            System.out.println((e.ordinal() + 1) + ". " + e);
        }
        int opcionEsp = Integer.parseInt(scanner.nextLine().trim());
        Especialidad especialidad = Especialidad.values()[opcionEsp - 1];

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine().trim();

        Medico medico = new Medico(0, nombre, especialidad, telefono, email, direccion);
        servicioMedico.agregarMedico(medico);
        System.out.println("Médico agregado correctamente.");
    }

    // ==================== 3. CITAS ====================

    private static void menuCitas(Perfil perfil) throws SQLException {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Citas médicas ---");
            System.out.println("3.1 Listado de citas");
            System.out.println("3.2 Agregar cita");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "3.1":
                    List<Cita> citas = servicioCita.listarCitasOrdenadas(perfil.getIdPerfil());
                    if (citas.isEmpty()) System.out.println("(Sin citas registradas)");
                    for (Cita c : citas) {
                        System.out.println(c.getIdCita() + ". " + c);
                    }
                    break;
                case "3.2":
                    agregarCita(perfil);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void agregarCita(Perfil perfil) throws SQLException {
        List<Medico> medicos = servicioMedico.listarMedicos();
        if (medicos.isEmpty()) {
            System.out.println("No hay médicos registrados. Agregue uno primero.");
            return;
        }
        System.out.print("Título de la cita: ");
        String titulo = scanner.nextLine().trim();

        System.out.println("Elija un médico:");
        for (Medico m : medicos) {
            System.out.println(m.getIdMedico() + ". " + m);
        }
        int idMedico = Integer.parseInt(scanner.nextLine().trim());
        Medico medicoElegido = medicos.stream()
                .filter(m -> m.getIdMedico() == idMedico)
                .findFirst().orElse(null);
        if (medicoElegido == null) {
            System.out.println("Médico no válido.");
            return;
        }

        System.out.print("Fecha (formato aaaa-mm-dd, ej: 2026-08-15): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());
        System.out.print("Hora (formato HH:mm, 24 horas, ej: 14:30): ");
        LocalTime hora = LocalTime.parse(scanner.nextLine().trim());

        Cita cita = new Cita(0, medicoElegido, titulo, fecha, hora);
        servicioCita.agregarCita(cita, perfil.getIdPerfil());
        System.out.println("Cita agregada correctamente.");
    }

    // ==================== 4. ACTIVIDAD FÍSICA ====================

    private static void menuActividad(Perfil perfil) throws SQLException {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Actividad física ---");
            System.out.println("4.1 Listado de actividades");
            System.out.println("4.2 Registrar actividad física");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "4.1":
                    List<ActividadRegistro> registros = servicioActividad.listarActividadesDePerfil(perfil.getIdPerfil());
                    if (registros.isEmpty()) System.out.println("(Sin actividades registradas)");
                    for (ActividadRegistro ar : registros) {
                        System.out.println(ar);
                    }
                    break;
                case "4.2":
                    registrarActividadFisica(perfil);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void registrarActividadFisica(Perfil perfil) throws SQLException {
        List<Actividad> tipos = servicioActividad.listarTiposDeActividad();
        if (tipos.isEmpty()) {
            System.out.println("No hay tipos de actividad cargados en el sistema todavía.");
            return;
        }

        System.out.print("Fecha (formato aaaa-mm-dd, no puede ser futura): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());

        System.out.println("Tipo de actividad:");
        for (Actividad a : tipos) {
            System.out.println(a.getIdActividad() + ". " + a);
        }
        int idActividad = Integer.parseInt(scanner.nextLine().trim());
        Actividad actividadElegida = tipos.stream()
                .filter(a -> a.getIdActividad() == idActividad)
                .findFirst().orElse(null);
        if (actividadElegida == null) {
            System.out.println("Actividad no válida.");
            return;
        }

        System.out.print("Duración en minutos: ");
        int duracion = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Horario:");
        for (Horario h : Horario.values()) {
            System.out.println((h.ordinal() + 1) + ". " + h);
        }
        int opcionHorario = Integer.parseInt(scanner.nextLine().trim());
        Horario horario = Horario.values()[opcionHorario - 1];

        ActividadRegistro registro = new ActividadRegistro(0, actividadElegida, fecha, duracion, horario);

        try {
            servicioActividad.registrarActividad(registro, perfil.getIdPerfil(), actividadElegida.getIdActividad());
            System.out.println("Actividad registrada correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}