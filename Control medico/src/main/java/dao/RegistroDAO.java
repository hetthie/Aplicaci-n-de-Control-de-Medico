package dao;

import modelo.Registro;
import java.sql.SQLException;
import java.util.List;

public interface RegistroDAO {
    void crear(Registro registro, int idMedicamentoAsignado) throws SQLException;
    List<Registro> listarPorMedicamentoAsignado(int idMedicamentoAsignado) throws SQLException;
}
