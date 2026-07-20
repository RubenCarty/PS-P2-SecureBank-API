const API_URL = "http://localhost:8080";

function getToken() {
    return localStorage.getItem("token");
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "/login";
}
function mostrarFormularioCuenta() {
    const form = document.getElementById("formCrearCuenta");
    form.classList.toggle("d-none");
}
function obtenerPayloadToken() {
    const token = getToken();

    if (!token) {
        return null;
    }

    try {
        const payloadBase64 = token.split(".")[1];
        const payloadJson = atob(payloadBase64);
        return JSON.parse(payloadJson);
    } catch (error) {
        console.error("Token inválido", error);
        return null;
    }
}

function usuarioEsAdmin() {
    const payload = obtenerPayloadToken();

    if (!payload) {
        return false;
    }

    const roles = payload.roles || payload.authorities || payload.role || "";

    if (Array.isArray(roles)) {
        return roles.includes("ROLE_ADMIN") || roles.includes("ADMIN");
    }

    return roles.toString().includes("ROLE_ADMIN") || roles.toString().includes("ADMIN");
}

function inicializarDashboard() {
    cargarCuentas();

    if (usuarioEsAdmin()) {
        document.querySelectorAll(".admin-only").forEach(elemento => {
            elemento.classList.remove("d-none");
        });
    }
}
function togglePassword(inputId, button) {

    const input = document.getElementById(inputId);

    if (input.type === "password") {
        input.type = "text";
        button.innerText = "🙈";
    } else {
        input.type = "password";
        button.innerText = "👁";
    }
}
async function cargarCuentas() {
    const response = await fetch(`/api/cuentas/mis-cuentas`, {
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });

    const tbody = document.getElementById("tablaCuentas");
    tbody.innerHTML = "";

    if (!response.ok) {
        tbody.innerHTML = `
            <tr>
                <td colspan="4" class="text-danger">
                    No se pudieron cargar las cuentas
                </td>
            </tr>
        `;
        return;
    }

    const cuentas = await response.json();

    if (cuentas.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="4" class="text-center">
                    Aún no tienes cuentas registradas
                </td>
            </tr>
        `;
        return;
    }

    cuentas.forEach(c => {
        tbody.innerHTML += `
            <tr>
                <td>${c.numeroCuenta}</td>
                <td>${c.tipoCuenta}</td>
                <td>${c.estado}</td>
                <td>S/ ${c.saldo}</td>
            </tr>
        `;
    });
}
async function login() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const mensaje = document.getElementById("mensaje");

    mensaje.innerText = "";

    if (!username || !password) {
        mensaje.innerText = "Ingrese usuario y contraseña";
        return;
    }

    try {
        const response = await fetch(`/api/auth/login`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({username, password})
        });

        const data = await response.json();
        console.log("Respuesta login:", data);

        if (response.ok) {
            const token = data.token || data.accessToken || data.jwt;

            if (!token) {
                mensaje.innerText = "El servidor no devolvió token";
                return;
            }

            localStorage.setItem("token", token);
            window.location.href = "/dashboard";
        } else {
            mensaje.innerText = data.message || "Usuario o contraseña incorrectos";
        }

    } catch (error) {
        console.error("Error en login:", error);
        mensaje.innerText = "Error de conexión con el servidor";
    }
}

async function crearCuenta() {
    const tipoCuenta = document.getElementById("tipoCuenta").value;

    const response = await fetch(`${API_URL}/api/cuentas/crear`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${getToken()}`
        },
        body: JSON.stringify({tipoCuenta})
    });

    if (response.ok) {
        alert("Cuenta creada correctamente");
        cargarCuentas();
    } else {
        alert("Error al crear cuenta");
    }
}

async function cargarCuentas() {
    const response = await fetch(`${API_URL}/api/cuentas/mis-cuentas`, {
        headers: {"Authorization": `Bearer ${getToken()}`}
    });

    const cuentas = await response.json();
    const tbody = document.getElementById("tablaCuentas");
    tbody.innerHTML = "";

    cuentas.forEach(c => {
        tbody.innerHTML += `
            <tr>
                <td>${c.numeroCuenta}</td>
                <td>${c.tipoCuenta}</td>
                <td>${c.estado}</td>
                <td>S/ ${c.saldo}</td>
            </tr>
        `;
    });
}
async function registrarUsuario() {
    const body = {
        dni: document.getElementById("dni").value,
        nombres: document.getElementById("nombres").value,
        apellidos: document.getElementById("apellidos").value,
        correo: document.getElementById("correo").value,
        celular: document.getElementById("celular").value,
        direccion: document.getElementById("direccion").value,
        username: document.getElementById("usernameRegistro").value,
        password: document.getElementById("passwordRegistro").value
    };

    const response = await fetch(`${API_URL}/api/auth/register`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    });

    if (response.ok) {
        alert("Usuario registrado correctamente");
        window.location.href = "/login";
    } else {
        const error = await response.json();
        document.getElementById("mensajeRegistro").innerText = error.message || "Error al registrar";
    }
}

async function realizarTransferencia() {
    const body = {
        numeroCuentaOrigen: document.getElementById("cuentaOrigen").value,
        numeroCuentaDestino: document.getElementById("cuentaDestino").value,
        monto: parseFloat(document.getElementById("monto").value),
        descripcion: document.getElementById("descripcion").value
    };

    const response = await fetch(`${API_URL}/api/transferencias`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${getToken()}`
        },
        body: JSON.stringify(body)
    });

    if (response.ok) {
        const data = await response.json();
        alert("Transferencia exitosa: " + data.numeroOperacion);
        window.location.href = "/historial";
    } else {
        const error = await response.json();
        document.getElementById("mensajeTransferencia").innerText = error.message || "Error en transferencia";
    }
}

async function cargarHistorial() {
    const response = await fetch(`${API_URL}/api/transferencias/mis-transferencias`, {
        headers: {"Authorization": `Bearer ${getToken()}`}
    });

    const historial = await response.json();
    const tbody = document.getElementById("tablaHistorial");
    tbody.innerHTML = "";

    historial.forEach(t => {
        tbody.innerHTML += `
            <tr>
                <td>${t.numeroOperacion}</td>
                <td>${t.cuentaOrigen}</td>
                <td>${t.cuentaDestino}</td>
                <td>S/ ${t.monto}</td>
                <td>${t.estado}</td>
                <td>${t.fechaTransferencia}</td>
            </tr>
        `;
    });
}
async function cargarAuditoria() {
    const response = await fetch(`${API_URL}/api/admin/auditoria`, {
        headers: {"Authorization": `Bearer ${getToken()}`}
    });

    if (!response.ok) {
        alert("No tiene permisos para ver auditoría");
        window.location.href = "/dashboard";
        return;
    }

    const auditorias = await response.json();
    const tbody = document.getElementById("tablaAuditoria");
    tbody.innerHTML = "";

    auditorias.forEach(a => {
        tbody.innerHTML += `
            <tr>
                <td>${a.id}</td>
                <td>${a.usuario}</td>
                <td>${a.accion}</td>
                <td>${a.descripcion}</td>
                <td>${a.modulo}</td>
                <td>${a.ipAddress}</td>
                <td>${a.exitoso ? "Sí" : "No"}</td>
                <td>${a.fechaEvento}</td>
            </tr>
        `;
    });
}
async function abonarCuentaAdmin() {
    const numeroCuenta = document.getElementById("adminNumeroCuenta").value;
    const monto = parseFloat(document.getElementById("adminMonto").value);
    const mensaje = document.getElementById("mensajeAdminCuenta");

    const response = await fetch(`/api/admin/cuentas/abonar`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${getToken()}`
        },
        body: JSON.stringify({numeroCuenta, monto})
    });

    const data = await response.json();

    if (response.ok) {
        mensaje.innerText = "Abono realizado correctamente. Nuevo saldo: S/ " + data.saldo;
        mensaje.className = "text-success mt-3";
    } else {
        mensaje.innerText = data.message || "Error al abonar";
        mensaje.className = "text-danger mt-3";
    }
}

async function bloquearCuentaAdmin() {
    const numeroCuenta = document.getElementById("adminNumeroCuenta").value;
    const mensaje = document.getElementById("mensajeAdminCuenta");

    const response = await fetch(`/api/admin/cuentas/${numeroCuenta}/bloquear`, {
        method: "PUT",
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });

    const data = await response.json();

    if (response.ok) {
        mensaje.innerText = "Cuenta bloqueada correctamente";
        mensaje.className = "text-success mt-3";
    } else {
        mensaje.innerText = data.message || "Error al bloquear cuenta";
        mensaje.className = "text-danger mt-3";
    }
}

async function activarCuentaAdmin() {
    const numeroCuenta = document.getElementById("adminNumeroCuenta").value;
    const mensaje = document.getElementById("mensajeAdminCuenta");

    const response = await fetch(`/api/admin/cuentas/${numeroCuenta}/activar`, {
        method: "PUT",
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });

    const data = await response.json();

    if (response.ok) {
        mensaje.innerText = "Cuenta activada correctamente";
        mensaje.className = "text-success mt-3";
    } else {
        mensaje.innerText = data.message || "Error al activar cuenta";
        mensaje.className = "text-danger mt-3";
    }
}
async function validarCuentaAdmin() {
    const numeroCuenta = document.getElementById("adminNumeroCuenta").value;
    const datos = document.getElementById("datosCuentaAdmin");
    const mensaje = document.getElementById("mensajeAdminCuenta");

    mensaje.innerText = "";
    datos.className = "alert alert-secondary d-none";
    datos.innerHTML = "";

    const response = await fetch(`/api/admin/cuentas/${numeroCuenta}`, {
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });

    const data = await response.json();

    if (response.ok) {
        datos.className = "alert alert-secondary";
        datos.innerHTML = `
            <strong>Titular:</strong> ${data.titular}<br>
            <strong>DNI:</strong> ${data.dni}<br>
            <strong>Cuenta:</strong> ${data.numeroCuenta}<br>
            <strong>Tipo:</strong> ${data.tipoCuenta}<br>
            <strong>Estado:</strong> ${data.estado}<br>
            <strong>Saldo:</strong> S/ ${data.saldo}
        `;
    } else {
        mensaje.innerText = data.message || "Cuenta no encontrada";
        mensaje.className = "text-danger mt-3";
    }
}
async function buscarUsuarioAdmin() {
    const dni = document.getElementById("adminDniUsuario").value;
    const mensaje = document.getElementById("mensajeAdminUsuario");
    const panel = document.getElementById("panelUsuarioAdmin");

    mensaje.innerText = "";
    panel.classList.add("d-none");

    const response = await fetch(`/api/admin/usuarios/dni/${dni}`, {
        headers: {
            "Authorization": `Bearer ${getToken()}`
        }
    });

    if (response.status === 403) {
        mensaje.innerText = "No tiene permisos de administrador";
        mensaje.className = "text-danger mt-3";
        return;
    }

    if (response.status === 401) {
        mensaje.innerText = "Sesión vencida. Inicie sesión nuevamente.";
        mensaje.className = "text-danger mt-3";
        return;
    }

    const data = await response.json();

    if (response.ok) {
        document.getElementById("usuarioAdminId").value = data.id;
        document.getElementById("usuarioAdminDni").value = data.dni;
        document.getElementById("usuarioAdminUsername").value = data.username;
        document.getElementById("usuarioAdminNombres").value = data.nombres;
        document.getElementById("usuarioAdminApellidos").value = data.apellidos;
        document.getElementById("usuarioAdminCorreo").value = data.correo;
        document.getElementById("usuarioAdminCelular").value = data.celular;
        document.getElementById("usuarioAdminDireccion").value = data.direccion;
        document.getElementById("usuarioAdminRol").value = data.rol;
        document.getElementById("usuarioAdminActivo").value = data.activo ? "Sí" : "No";

        panel.classList.remove("d-none");
    } else {
        mensaje.innerText = data.message || "Usuario no encontrado";
        mensaje.className = "text-danger mt-3";
    }
}

async function actualizarUsuarioAdmin() {
    const id = document.getElementById("usuarioAdminId").value;

    const body = {
        correo: document.getElementById("usuarioAdminCorreo").value,
        celular: document.getElementById("usuarioAdminCelular").value,
        direccion: document.getElementById("usuarioAdminDireccion").value
    };

    const response = await fetch(`/api/admin/usuarios/${id}/actualizar`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${getToken()}`
        },
        body: JSON.stringify(body)
    });

    const data = await response.json();
    const mensaje = document.getElementById("mensajeAdminUsuario");

    if (response.ok) {
        mensaje.innerText = "Usuario actualizado correctamente";
        mensaje.className = "text-success mt-3";
    } else {
        mensaje.innerText = data.message || "Error al actualizar";
        mensaje.className = "text-danger mt-3";
    }
}

async function resetPasswordAdmin() {
    const id = document.getElementById("usuarioAdminId").value;
    const nuevaPassword = document.getElementById("usuarioAdminNuevaPassword").value;
    const mensaje = document.getElementById("mensajeAdminUsuario");

    const response = await fetch(`/api/admin/usuarios/${id}/reset-password`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${getToken()}`
        },
        body: JSON.stringify({nuevaPassword})
    });

    if (response.ok) {
        mensaje.innerText = "Contraseña actualizada correctamente";
        mensaje.className = "text-success mt-3";
        document.getElementById("usuarioAdminNuevaPassword").value = "";
    } else {
        const data = await response.json();
        mensaje.innerText = data.message || "Error al resetear contraseña";
        mensaje.className = "text-danger mt-3";
    }
}
function togglePassword(inputId, button) {
    const input = document.getElementById(inputId);
    const icon = button.querySelector("i");

    const passwordIsVisible = input.type === "text";

    input.type = passwordIsVisible ? "password" : "text";

    icon.classList.toggle("bi-eye", passwordIsVisible);
    icon.classList.toggle("bi-eye-slash", !passwordIsVisible);

    button.setAttribute(
        "aria-label",
        passwordIsVisible
            ? "Mostrar contraseña"
            : "Ocultar contraseña"
    );
}