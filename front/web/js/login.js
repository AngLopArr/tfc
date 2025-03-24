// Creo un evento de click para el botón del formulario de login
let botonLogin = document.getElementById('boton-login');
botonLogin.addEventListener('click', async function(e) {
    // Evito el evento, para que no se recargue la página
    e.preventDefault();    
    // Empleo la función 'login'
    login();
});

// Función que permite a un usuario hacer login
async function login(){
    // Se toma el contenido de los campos de texto del formulario
    let email = document.getElementById('login-email').value;
    let password = document.getElementById('login-password').value;

    // Se crea el usuario con el que vamos a intentar hacer login
    const empleado = {
        email: email,
        password: password
    };

    // Se realiza la petición de login a la API pasándole el usuario
    const response = await fetch("http://localhost:8080/aracne/employees/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(empleado)
    });

    // Tomamos la respuesta
    const data = await response.json();
    let exito = data["success"];
    let role = data["role"];

    console.log(exito);

    // Si la respuesta es positiva
    if (exito) {
        // Se almacena en el local storage que el estado de acceso es true
        localStorage.setItem('loggedIn', 'true');
        // Se almacena en el local storage el nombre del usuario logueado
        localStorage.setItem('role', role);
        // Se redirige al usuario a la página principal del chat
        window.location.href = 'products.html';
    } else {
        // Si la respuesta del login es negativa, se indica que las credenciales deben ser incorrectas
        alert('Usuario o contraseña incorrectos.');
    }
}