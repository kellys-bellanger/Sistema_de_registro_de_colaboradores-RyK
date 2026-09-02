module ni.edu.uam.registro_de_colaboradores {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.registro_de_colaboradores to javafx.fxml;
    exports ni.edu.uam.registro_de_colaboradores;
}