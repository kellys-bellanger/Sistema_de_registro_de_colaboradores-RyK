package ni.edu.uam.registro_de_colaboradores;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DistribuidoraApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carga el archivo FXML desde la carpeta de recursos
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registro-view.fxml"));

        // Crea la escena con el tamaño adecuado para el formulario y la tabla
        Scene scene = new Scene(fxmlLoader.load(), 1163, 650);

        // Configuración de la ventana principal
        stage.setTitle("Distribuidora El Güegüense - Registro de Colaboradores");
        stage.setScene(scene);
        stage.setResizable(true); // Permite ajustar el tamaño si es necesario
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}