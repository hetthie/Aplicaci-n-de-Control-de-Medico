package modelo;

/**
 * Especialidades médicas disponibles en el sistema. El orden importa:
 * se usa (ordinal + 1) para mapear cada valor a su fila correspondiente
 * en la tabla especialidad de la base de datos.
 */
public enum Especialidad {
    PEDIATRA,
    MEDICO_GENERAL,
    CARDIOLOGO,
    DERMATOLOGO,
    TRAUMATOLOGO,
    GINECOLOGO,
    NEUROLOGO,
    OTORRINOLARINGOLOGO,
    OFTALMOLOGO,
    PSIQUIATRA
}
