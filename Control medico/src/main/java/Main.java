import jdk.swing.interop.SwingInterOpUtils;
import modelo.*;

import javax.sound.midi.spi.SoundbankReader;
import java.sql.SQLOutput;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    Perfil perfil1 = new Perfil(1,"andie", Relacion.HIJO,"ancorreo");
    System.out.println(perfil1);
    Usuario usuario1 = new Usuario(01,"Andie","correo1");
    System.out.println(usuario1);
    usuario1.agregarPerfil(perfil1);
    System.out.println(usuario1.getPerfiles());
    Perfil perfil2 = new Perfil(02,"Matthius",Relacion.PADRE,"Matthius.com");
    /*chequeo de creacion de cita = medico(Especialidad) => cita */
    Medico medico = new Medico(1,"Hetthie",Especialidad.NEUROLOGO,"099999","hetthiecorreo","Mucho Lote");
    Cita cita = new Cita(01,medico, "chequeo",LocalDate.of(2026, 8, 15), LocalTime.of(10, 30));

    System.out.println(medico.toString());
    System.out.println(cita.toString());

    /*chequear actividadRegistro(horario/ actividad)*/
    Actividad actividad = new Actividad(1,"trotar");
    ActividadRegistro actividadregistro = new ActividadRegistro(1,actividad,LocalDate.of(2026, 8, 15),5,Horario.MANANA);
    System.out.println(actividad.toString());
    System.out.println(actividadregistro.toString());

    /*Chequear medicamento asignado => registro => medicamento  => frecuencia   =>medicamento*/
    Registro registro = new Registro(01,LocalDate.of(2026, 8, 15), LocalTime.of(10, 30));
    Medicamento medicamento = new Medicamento(01,"paracetamol",3);
    FrecuenciaDiaria frecuenciaDiaria = new FrecuenciaDiaria(01,"cada hora");
    Frecuencia frecuencia = new Frecuencia(01,"variada",frecuenciaDiaria);

    MedicamentoAsignado medicamentoAsignado = new MedicamentoAsignado(01,medicamento,Presentacion.PASTILLA,frecuencia,2,2,1);
    System.out.println(registro.toString());
    System.out.println(medicamento.toString());
    System.out.println(frecuenciaDiaria.toString());
    System.out.println(frecuencia.toString());
    System.out.printf(medicamentoAsignado.toString());
}