package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/consultas")

public class ConsultaController {

    @Autowired //Para que Spring framework sepa dónde debe inyectar esa clase que está siendo instanciada
    private AgendaDeConsultaService service; //Aqui estamos creando un llamado al servicio que crea los
    //registros de Consultas.

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){
        service.agendar(datos);
   //     System.out.println(datos);  //Aqui revisabamos como llegaban los datos
        return ResponseEntity.ok(new DatosDetalleConsulta(null,null,null,null));
    }
}
