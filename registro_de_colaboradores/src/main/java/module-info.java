module ni.edu.uam.registro_de_colaboradores {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    // Permitir a FXML y JavaFX acceder a los controladores y modelos
    opens ni.edu.uam.registro_de_colaboradores to javafx.fxml;
    opens ni.edu.uam.registro_de_colaboradores.controller to javafx.fxml;
    opens ni.edu.uam.registro_de_colaboradores.models to javafx.base, javafx.fxml;

    exports ni.edu.uam.registro_de_colaboradores;
    exports ni.edu.uam.registro_de_colaboradores.controller;
    exports ni.edu.uam.registro_de_colaboradores.models;
}