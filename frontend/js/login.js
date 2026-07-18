document.addEventListener("DOMContentLoaded", () => {
  // Si ya hay una sesion valida, no tiene sentido mostrar el login de nuevo.
  if (obtenerSesion()) {
    mostrarBienvenida(obtenerSesion().correo);
    return;
  }

  const formulario = document.getElementById("form-login");
  const botonSubmit = document.getElementById("btn-login");
  const mensajeError = document.getElementById("mensaje-error");
  const errorCorreo = document.getElementById("error-correo");
  const errorContrasena = document.getElementById("error-contrasena");

  formulario.addEventListener("submit", async (evento) => {
    evento.preventDefault();
    limpiarErrores();

    const correo = document.getElementById("correo").value.trim();
    const contrasena = document.getElementById("contrasena").value;

    fijarCargando(true);
    try {
      const respuesta = await login(correo, contrasena);
      guardarSesion(correo, respuesta);
      mostrarBienvenida(correo);
    } catch (error) {
      mostrarError(error);
    } finally {
      fijarCargando(false);
    }
  });

  function limpiarErrores() {
    mensajeError.textContent = "";
    mensajeError.hidden = true;
    errorCorreo.textContent = "";
    errorContrasena.textContent = "";
  }

  function mostrarError(error) {
    const detalle = error.detalle;
    if (detalle && detalle.errores) {
      if (detalle.errores.correo) errorCorreo.textContent = detalle.errores.correo;
      if (detalle.errores.contrasena) errorContrasena.textContent = detalle.errores.contrasena;
    }
    mensajeError.textContent = error.message;
    mensajeError.hidden = false;
  }

  function fijarCargando(cargando) {
    botonSubmit.disabled = cargando;
    botonSubmit.textContent = cargando ? "Iniciando sesion..." : "Iniciar sesion";
  }

  function mostrarBienvenida(correo) {
    formulario.hidden = true;
    const bienvenida = document.getElementById("bienvenida");
    document.getElementById("bienvenida-correo").textContent = correo;
    bienvenida.hidden = false;
  }
});

function salir() {
  cerrarSesion();
  window.location.reload();
}
