package dao;

import modelo.FrecuenciaDiaria;
import java.sql.SQLException;

public interface FrecuenciaDiariaDAO {
    int crear(FrecuenciaDiaria frecuenciaDiaria) throws SQLException;
}
