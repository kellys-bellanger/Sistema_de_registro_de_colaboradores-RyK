package ni.edu.uam.registro_de_colaboradores.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ni.edu.uam.registro_de_colaboradores.models.Colaborador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorController {

    // Componentes FXML
    @FXML private TextField txtNames, txtLastNames, txtUser;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cbCargo;
    @FXML private ListView<String> lvArea;
    @FXML private DatePicker dpFechaContratacion;
    @FXML private RadioButton rbPermanente, rbTemporal;
    @FXML private ToggleGroup tgTipoContrato;
    @FXML private CheckBox chkSeguro, chkViaticos, chkBono;

    // Tabla y Columnas
    @FXML private TableView<Colaborador> tableColaboradores;
    @FXML private TableColumn<Colaborador, String> colNombre, colArea, colCargo, colFecha, colContrato, colBeneficios;

    // Menús y ToolBar
    @FXML private MenuItem itemNuevo, itemSalir, itemAcercaDe, ctxEditar, ctxEliminar;
    @FXML private Button btnTbGuardar, btnTbLimpiar, btnTbEliminar;
    @FXML private Button btnGuardar, btnActualizar, btnLimpiar, btnEliminar;

    private final ObservableList<Colaborador> listaColaboradores = FXCollections.observableArrayList();
    private Colaborador colaboradorSeleccionado = null;

    @FXML
    public void initialize() {
        // Cargar Opciones
        cbCargo.setItems(FXCollections.observableArrayList("Gerente", "Vendedor", "Contador", "Bodeguero", "Distribuidor"));
        lvArea.setItems(FXCollections.observableArrayList("Administración", "Ventas", "Logística", "Finanzas"));

        // Mapear Columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colContrato.setCellValueFactory(new PropertyValueFactory<>("tipoContrato"));
        colBeneficios.setCellValueFactory(new PropertyValueFactory<>("beneficios"));

        tableColaboradores.setItems(listaColaboradores);

        // Asignar Eventos ActionEvent
        btnGuardar.setOnAction(this::handleGuardar);
        if (btnTbGuardar != null) btnTbGuardar.setOnAction(this::handleGuardar);

        btnActualizar.setOnAction(this::handleActualizar);
        if (ctxEditar != null) ctxEditar.setOnAction(this::handleActualizar);

        btnLimpiar.setOnAction(e -> limpiarFormulario());
        if (btnTbLimpiar != null) btnTbLimpiar.setOnAction(e -> limpiarFormulario());
        if (itemNuevo != null) itemNuevo.setOnAction(e -> limpiarFormulario());

        btnEliminar.setOnAction(this::handleEliminar);
        if (btnTbEliminar != null) btnTbEliminar.setOnAction(this::handleEliminar);
        if (ctxEliminar != null) ctxEliminar.setOnAction(this::handleEliminar);

        if (itemSalir != null) itemSalir.setOnAction(e -> System.exit(0));
        if (itemAcercaDe != null) itemAcercaDe.setOnAction(e -> mostrarAlerta("Acerca de", "Sistema de Registro de Colaboradores\nDistribuidora El Güegüense", Alert.AlertType.INFORMATION));

        // Evento MouseEvent: Doble Clic
        tableColaboradores.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                cargarColaboradorSeleccionado();
            }
        });

        // Evento KeyEvent: Enter / Escape
        javafx.application.Platform.runLater(() -> {
            if (btnGuardar.getScene() != null) {
                btnGuardar.getScene().setOnKeyPressed((KeyEvent event) -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        if (colaboradorSeleccionado == null) handleGuardar(null);
                        else handleActualizar(null);
                    } else if (event.getCode() == KeyCode.ESCAPE) {
                        limpiarFormulario();
                    }
                });
            }
        });
    }

    private void handleGuardar(ActionEvent event) {
        if (!validarFormulario()) return;
        listaColaboradores.add(crearObjetoDesdeFormulario());
        limpiarFormulario();
        mostrarAlerta("Éxito", "Colaborador registrado correctamente.", Alert.AlertType.INFORMATION);
    }

    private void handleActualizar(ActionEvent event) {
        if (colaboradorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un colaborador con doble clic para actualizar.", Alert.AlertType.WARNING);
            return;
        }
        if (!validarFormulario()) return;

        colaboradorSeleccionado.setNombres(txtNames.getText().trim());
        colaboradorSeleccionado.setApellidos(txtLastNames.getText().trim());
        colaboradorSeleccionado.setUsuario(txtUser.getText().trim());
        colaboradorSeleccionado.setPassword(txtPassword.getText());
        colaboradorSeleccionado.setCargo(cbCargo.getValue());
        colaboradorSeleccionado.setArea(lvArea.getSelectionModel().getSelectedItem());
        colaboradorSeleccionado.setFechaContratacion(dpFechaContratacion.getValue());
        colaboradorSeleccionado.setTipoContrato(rbPermanente.isSelected() ? "Permanente" : "Temporal");
        colaboradorSeleccionado.setBeneficios(obtenerBeneficiosSeleccionados());

        tableColaboradores.refresh();
        limpiarFormulario();
        mostrarAlerta("Éxito", "Datos actualizados correctamente.", Alert.AlertType.INFORMATION);
    }

    private void handleEliminar(ActionEvent event) {
        Colaborador seleccionado = tableColaboradores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaColaboradores.remove(seleccionado);
            limpiarFormulario();
            mostrarAlerta("Eliminado", "Registro eliminado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Advertencia", "Seleccione un registro para eliminar.", Alert.AlertType.WARNING);
        }
    }

    private boolean validarFormulario() {
        if (txtNames.getText().trim().isEmpty() || txtLastNames.getText().trim().isEmpty() ||
                txtUser.getText().trim().isEmpty() || txtPassword.getText().isEmpty() ||
                cbCargo.getValue() == null || lvArea.getSelectionModel().getSelectedItem() == null ||
                dpFechaContratacion.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return false;
        }

        if (txtUser.getText().trim().length() < 5) {
            mostrarAlerta("Error", "El usuario debe tener al menos 5 caracteres.", Alert.AlertType.ERROR);
            return false;
        }

        if (txtPassword.getText().length() < 8) {
            mostrarAlerta("Error", "La contraseña debe tener al menos 8 caracteres.", Alert.AlertType.ERROR);
            return false;
        }

        if (dpFechaContratacion.getValue().isAfter(LocalDate.now())) {
            mostrarAlerta("Error", "La fecha de contratación no puede ser futura.", Alert.AlertType.ERROR);
            return false;
        }

        if (!chkSeguro.isSelected() && !chkViaticos.isSelected() && !chkBono.isSelected()) {
            mostrarAlerta("Error", "Debe seleccionar al menos un beneficio.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private Colaborador crearObjetoDesdeFormulario() {
        String tipoContrato = rbPermanente.isSelected() ? "Permanente" : "Temporal";
        return new Colaborador(
                txtNames.getText().trim(),
                txtLastNames.getText().trim(),
                txtUser.getText().trim(),
                txtPassword.getText(),
                cbCargo.getValue(),
                lvArea.getSelectionModel().getSelectedItem(),
                dpFechaContratacion.getValue(),
                tipoContrato,
                obtenerBeneficiosSeleccionados()
        );
    }

    private void cargarColaboradorSeleccionado() {
        colaboradorSeleccionado = tableColaboradores.getSelectionModel().getSelectedItem();
        if (colaboradorSeleccionado != null) {
            txtNames.setText(colaboradorSeleccionado.getNombres());
            txtLastNames.setText(colaboradorSeleccionado.getApellidos());
            txtUser.setText(colaboradorSeleccionado.getUsuario());
            txtPassword.setText(colaboradorSeleccionado.getPassword());
            cbCargo.setValue(colaboradorSeleccionado.getCargo());
            lvArea.getSelectionModel().select(colaboradorSeleccionado.getArea());
            dpFechaContratacion.setValue(colaboradorSeleccionado.getFechaContratacion());

            if ("Permanente".equalsIgnoreCase(colaboradorSeleccionado.getTipoContrato())) {
                rbPermanente.setSelected(true);
            } else {
                rbTemporal.setSelected(true);
            }

            String ben = colaboradorSeleccionado.getBeneficios();
            chkSeguro.setSelected(ben.contains("Seguro Médico"));
            chkViaticos.setSelected(ben.contains("Viáticos"));
            chkBono.setSelected(ben.contains("Bono Anual"));
        }
    }

    private String obtenerBeneficiosSeleccionados() {
        List<String> b = new ArrayList<>();
        if (chkSeguro.isSelected()) b.add("Seguro Médico");
        if (chkViaticos.isSelected()) b.add("Viáticos");
        if (chkBono.isSelected()) b.add("Bono Anual");
        return String.join(", ", b);
    }

    private void limpiarFormulario() {
        txtNames.clear();
        txtLastNames.clear();
        txtUser.clear();
        txtPassword.clear();
        cbCargo.getSelectionModel().clearSelection();
        lvArea.getSelectionModel().clearSelection();
        dpFechaContratacion.setValue(null);
        rbPermanente.setSelected(true);
        chkSeguro.setSelected(false);
        chkViaticos.setSelected(false);
        chkBono.setSelected(false);
        colaboradorSeleccionado = null;
        tableColaboradores.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}