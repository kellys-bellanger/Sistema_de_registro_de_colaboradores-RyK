package ni.edu.uam.registro_de_colaboradores.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Colaborador {
    private String nombres;
    private String apellidos;
    private String usuario;
    private String password;
    private String cargo;
    private String area;
    private LocalDate fechaContratacion;
    private String tipoContrato;
    private String beneficios;

    // Propiedad calculada personalizada
    public String getNombreCompleto() {
        return (nombres != null ? nombres : "") + " " + (apellidos != null ? apellidos : "");
    }
}