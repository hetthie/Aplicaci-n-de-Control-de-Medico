package dao;

import modelo.MedicamentoAsignado;
import java.sql.SQLException;
import java.util.List;

public interface MedicamentoAsignadoDAO {
    int crear(MedicamentoAsignado medicamentoAsignado, int idFrecuencia) throws SQLException;
    List<MedicamentoAsignado> listarPorPerfil(int idPerfil) throws SQLException;
    void actualizarCantidad(int id, int nuevaCantidad, int cantidadCargada) throws SQLException;
}
