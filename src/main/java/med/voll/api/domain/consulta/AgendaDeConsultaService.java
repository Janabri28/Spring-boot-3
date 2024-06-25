package med.voll.api.domain.consulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidationDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;

public class AgendaDeConsultaService {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired                                      //Si olvidasemos colocar la anotacion autowired, al llamar al repositorio obtendriamos null
    private ConsultaRepository consultaRepository;

    public void agendar(DatosAgendarConsulta datos){ //Recordar que DatosAgendarConsulta es un DTO que,
        //captura los datos de insomnia.
        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
             throw new ValidationDeIntegridad("No se encontró este id para paciente");
        }

        if(datos.idMedico()!=null && medicoRepository.existsById(datos.idMedico())){
            throw new ValidationDeIntegridad("No se encontró este id en Medicos");
        }

        var paciente= pacienteRepository.findById(datos.idPaciente()).get();

//        var medico= medicoRepository.findById(datos.idMedico()).get(); // Paso 2

        var medico= seleccionarMedico(datos);

//        var consulta= new Consulta(null,new Medico(), new Paciente(), datos.fecha()); //creamos un registro
        //del tipo Consulta.

        var consulta= new Consulta(null,medico, paciente, datos.fecha());

        consultaRepository.save(consulta);  //salvamos la consulta.

    }

    //OJO!!: Este método fue creado automaticamente por IntelliJ, al notar que no existía previamente, pero
    //lo declara como null, arriba en var consulta= new Consulta(null,medico, paciente, datos.fecha()); el
    //parametro medico, aparecía marcado en rojo, entonces para corregir ese error, hay que hacer que este
    //método retorne un valor del tipo Medico, dejaré comentada la instrucción que creo IntelliJ.
    // y dejaré el que corrige el instructor.
//    private void seleccionarMedico(DatosAgendarConsulta datos) {
      private Medico seleccionarMedico(DatosAgendarConsulta datos) {  //Paso 4
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidationDeIntegridad("debe seleccionarse una especialidad para el medico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}
