package mx.itson.tareas.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.itson.tareas.TareasApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class SistemaTareaFx extends Application {

//    public static void main(String[] args) {
//        launch(args);
//    }

    private ConfigurableApplicationContext applicationContext;

    public void init(){
        this.applicationContext =  new SpringApplicationBuilder(TareasApplication.class).run();
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(TareasApplication.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void stop(){
        applicationContext.close();
        Platform.exit();
    }
}

