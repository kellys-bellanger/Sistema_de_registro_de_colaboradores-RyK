Sistema de Registro de Colaboradores - Distribuidora El Güegüense
Aplicación de escritorio desarrollada con JavaFX para la gestión y control del expediente digital de colaboradores de la empresa Distribuidora El Güegüense. Permite realizar el registro, consulta, modificación y eliminación de personal mediante una interfaz gráfica construida en FXML y respaldada por la arquitectura MVC (Modelo-Vista-Controlador).

Integrantes
Kellys Bellanger

Raúl Valverde

Requisitos de Ejecución
Java Development Kit (JDK) 17 o superior.

JavaFX SDK 17+.
Funciones de la Interfaz
Operaciones CRUD:

Guardar: Valida y agrega un nuevo registro a la tabla.

Actualizar: Modifica la información del colaborador seleccionado.

Eliminar: Remueve el registro activo de la lista.

Limpiar / Nuevo: Restablece todos los campos del formulario a su estado inicial.

Interacción y Eventos:

Doble Clic: Al hacer doble clic en cualquier fila del TableView, los datos del colaborador se cargan en los campos correspondientes para su edición.

Clic Derecho (Context Menu): Permite ejecutar las acciones de Editar y Eliminar directamente desde la tabla.

Atajos de Teclado: Se capturan las pulsaciones de la tecla ENTER para enviar/actualizar datos y ESCAPE para vaciar el formulario.

Validaciones del Formulario
Antes de procesar cualquier inserción o cambio, el sistema verifica el cumplimiento de las siguientes condiciones:

Ningún campo del formulario puede estar vacío.

El nombre de usuario debe contener 5 caracteres como mínimo.

La contraseña debe tener una longitud mínima de 8 caracteres.

La fecha de contratación seleccionada debe ser menor o igual a la fecha del día en curso.

Se debe marcar al menos una opción en la casilla de beneficios.

Guía de Compilación y Uso
Importar el proyecto como un proyecto Maven existente en su IDE.

Sincronizar las dependencias para asegurar la carga de los módulos de JavaFX y Lombok.

Ejecutar el archivo Launcher.java.
