package dao;

import modelo.Frecuencia;
import java.sql.SQLException;

public interface FrecuenciaDAO {
    int crear(Frecuencia frecuencia, int idFrecuenciaDiaria) throws SQLException;
}
