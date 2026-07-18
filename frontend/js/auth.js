// Utilidades de autenticacion compartidas: llamada al login y manejo de la
// sesion guardada en localStorage (token JWT + su expiracion).

const AUTH_STORAGE_KEY = "gestorTareas.sesion";

/**
 * Llama a POST /api/auth/login.
 * Devuelve { token, tipo, expiraEnMs } si las credenciales son validas.
 * Si la API responde con error, lanza un Error cuyo `.detalle` trae el
 * cuerpo JSON de error ({ mensaje, errores, ... }) para poder mostrarlo.
 */
async function login(correo, contrasena) {
  const respuesta = await fetch(`${API_BASE_URL}/api/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ correo, contrasena }),
  });

  const cuerpo = await respuesta.json().catch(() => null);

  if (!respuesta.ok) {
    const error = new Error((cuerpo && cuerpo.mensaje) || "No se pudo iniciar sesion");
    error.detalle = cuerpo;
    error.status = respuesta.status;
    throw error;
  }

  return cuerpo;
}

/** Guarda la sesion (token + correo + expiracion absoluta) en localStorage. */
function guardarSesion(correo, loginResponse) {
  const sesion = {
    correo,
    token: loginResponse.token,
    tipo: loginResponse.tipo,
    expiraEn: Date.now() + loginResponse.expiraEnMs,
  };
  localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(sesion));
}

/** Devuelve la sesion guardada, o null si no existe o ya expiro. */
function obtenerSesion() {
  const crudo = localStorage.getItem(AUTH_STORAGE_KEY);
  if (!crudo) return null;

  const sesion = JSON.parse(crudo);
  if (Date.now() >= sesion.expiraEn) {
    localStorage.removeItem(AUTH_STORAGE_KEY);
    return null;
  }
  return sesion;
}

function cerrarSesion() {
  localStorage.removeItem(AUTH_STORAGE_KEY);
}
