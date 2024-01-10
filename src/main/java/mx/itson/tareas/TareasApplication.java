package mx.itson.tareas;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import mx.itson.tareas.ui.SistemaTareaFx;


@SpringBootApplication
@EntityScan(basePackages = "mx/itson/tareas/model")
public class TareasApplication {

    public static void main(String[] args) {
        //SpringApplication.run(TareasApplication.class, args);
        Application.launch(SistemaTareaFx.class, args);

    }

}
