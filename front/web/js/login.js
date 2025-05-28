let botonLogin = document.getElementById('boton-login');
botonLogin.addEventListener('click', async function(e) {
    e.preventDefault();    
    login();
});

let showPassword = document.getElementById("password-eye");
showPassword.addEventListener("click", () => {
    let type = document.getElementById('login-password').getAttribute("type");
    if(type === "password"){
        showPassword.src = "imagenes/closed_eye.png";
        document.getElementById('login-password').type ="text";
    }
    else{
        showPassword.src = "imagenes/eye.png";
        document.getElementById('login-password').type ="password";
    }
});

async function login(){
    let email = document.getElementById('login-email').value;
    let password = document.getElementById('login-password').value;

    const empleado = {
        email: email,
        password: password
    };

    const response = await fetch("http://localhost:8080/aracne/employees/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(empleado)
    });

    const data = await response.json();
    let exito = data["success"];
    let role = data["role"];

    console.log(exito);

    if (exito && role == "admin") {
        localStorage.setItem('loggedIn', 'true');
        localStorage.setItem('role', role);
        window.location.href = 'products.html';
    } 
    else if (exito && role == "employee") {
        localStorage.setItem('loggedIn', 'true');
        localStorage.setItem('role', role);
        window.location.href = 'products-employee.html';
    }
    else{
        alert('Usuario o contraseña incorrectos.');
    }
}