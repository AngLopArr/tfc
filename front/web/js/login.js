// Función que muestra el formulario de registro
function mostrarFormularioRegistro() {
    // Resetea el formulario de login y lo oculta
    document.getElementById('login-form').reset();
    document.querySelector('.container').style.display = 'none';
    // Muestra el formulario de registro
    document.getElementById('register-container').style.display = 'flex';
}

// Función que muestra el formulario de login
function mostrarFormularioLogin() {
    // Resetea el formulario de registro y lo oculta
    document.getElementById('register-form').reset();
    document.getElementById('register-container').style.display = 'none';
    // Muestra el formulario de login
    document.querySelector('.container').style.display = 'flex';
}

// Creo un evento de click para el botón del formulario de registro
let botonRegistro = document.getElementById('boton-registro');
botonRegistro.addEventListener("click", (event) => {
    // Evito el evento, para que no se recargue la página
    event.preventDefault();
    // Empleo la función 'registro'
    registro();
})

// Función que permite comprobar si el contenido del cuadro de texto contraseña y contraseña repetida son el mismo
function comprobarPassword(password, repeticionPassword){
    return password == repeticionPassword;
}

// Función que realiza una petición a la API para comprobar si un nombre de usuario concreto ya está pillado
async function comprobarUsuario(username){
    const response = await fetch("http://localhost:8080/hermes/" + username);
    const data = await response.json();
    // Se retorna la respuesta, true si este existe, y false si no
    return data["exists"];
}

// Función que permite registrar un usuario
async function registro(){
    // Tomamos el contenido de los campos de texto del registro
    let username = document.getElementById('register-username').value;
    let password = document.getElementById('register-password').value;
    let repeticionPassword = document.getElementById('register-confirm-password').value;
    // Comprobamos la existencia de un usuario con el username pasado
    let respuesta = await comprobarUsuario(username)
    // Si este existe, se le indica al cliente para que elija otro username
    if(respuesta){
        alert("Ese usuario ya existe. Inténtelo de nuevo.")
    }
    // Si no existe, continuamos
    else{
        // Se comprueba que la contraseña y su repetición coincidan
        if(comprobarPassword(password, repeticionPassword)){
            // De coincidir, se crea el usuario que le vamos a pasar a la API para que lo registre
            const usuario = {
                username: username,
                password: password
            };

            // Se le pasa a la API el usuario
            const response = await fetch("http://localhost:8080/hermes/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(usuario)
            });

            // Tomamos la respuesta de la API
            const data = await response.json();
            let respuesta = data["success"];

            // Si la respuesta es positiva, se le indica al cliente que el usuario se ha registrado correctamente
            if(respuesta){
                alert("El usuario se ha registrado correctamente.");
            }
            // Si la respuesta es negativa, se le indica al cliente que no se ha podido completar el registro
            else{
                alert("No se ha podido registrar al usuario.")
            }

            // Se resetea el formulario
            document.getElementById('register-form').reset();
        }
        // Si las contraseñas no coinciden, se le indica al cliente
        else{
            alert("Las contraseñas no coinciden.");
        }
    }
}

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
    let username = document.getElementById('login-username').value;
    let password = document.getElementById('login-password').value;

    // Se crea el usuario con el que vamos a intentar hacer login
    const usuario = {
        username: username,
        password: password
    };

    // Se realiza la petición de login a la API pasándole el usuario
    const response = await fetch("http://localhost:8080/hermes/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
    });

    // Tomamos la respuesta
    const data = await response.json();
    let respuesta = data["sucess"]; 

    // Si la respuesta es positiva
    if (respuesta) {
        // Se almacena en el local storage que el estado de acceso es true
        localStorage.setItem('loggedIn', 'true');
        // Se almacena en el local storage el nombre del usuario logueado
        localStorage.setItem('user', username);
        // Se redirige al usuario a la página principal del chat
        window.location.href = 'chat.html';
    } else {
        // Si la respuesta del login es negativa, se indica que las credenciales deben ser incorrectas
        alert('Usuario o contraseña incorrectos.');
    }
}