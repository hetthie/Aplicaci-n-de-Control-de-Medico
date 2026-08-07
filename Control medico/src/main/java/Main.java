import conexion.ConexionBD;
import dao.MedicamentoDAO;
import dao.MedicamentoDAOImpl;
import modelo.Medicamento;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConexionBD conexionBD = new ConexionBD();
        MedicamentoDAO medicamentoDAO = new MedicamentoDAOImpl(conexionBD);

        try {
            // Crear un medicamento nuevo
            Medicamento medicamento = new Medicamento();
            medicamento.setNombre("Molarex");
            medicamento.setCantidadDisponible(20);
            medicamentoDAO.crear(medicamento);
            System.out.println("Medicamento creado.");

            // Listar todos los medicamentos guardados
            List<Medicamento> medicamentos = medicamentoDAO.listarTodos();
            for (Medicamento m : medicamentos) {
                System.out.println(m.getIdMedicamento() + " - " + m);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}