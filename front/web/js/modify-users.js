window.onload = function() {
    if (localStorage.getItem('loggedIn') !== 'true') {
        window.location.href = 'login.html';
    }

    document.getElementById('logout').addEventListener('click', function() {
        localStorage.removeItem('loggedIn');
        
        localStorage.removeItem('user');
        
        window.location.href = 'login.html';
    });
    
    if(localStorage.getItem('role') !== 'admin'){
        window.location.href = 'login.html';
    }
};

const idEmpleado = new URLSearchParams((location.href).split("?")[1]).get("id");
let nombreEmployee = document.getElementById("nombre");
let emailEmployee = document.getElementById("email");
let passwordEmployee = document.getElementById("password");
let selectorRoles = document.getElementById("select-rols");
let nombreEmployeeSpan = document.getElementById("span-nombre-user");
let emailEmployeeSpan = document.getElementById("span-email-user");
let passwordEmployeeSpan = document.getElementById("span-password-user");
let showPassword = document.getElementById("password-eye");
let emailOriginal;

let boton = document.getElementById("boton-users");
boton.addEventListener("click", e => editarEmpleado(e));

async function getRoles(){
    const response = await fetch("http://localhost:8080/aracne/employees/roles");
    const data = await response.json();

    for (let index = 0; index < data.length; index++) {
        let option = document.createElement("option");
        option.value = data[index];
        const firstLetter = data[index].charAt(0);
        const firstLetterCap = firstLetter.toUpperCase();
        const remainingLetters = data[index].slice(1);
        const capitalizedWord = firstLetterCap + remainingLetters;
        option.innerText = capitalizedWord;
        selectorRoles.appendChild(option);
    }
}

getRoles();

async function fillForm(){
    const response = await fetch("http://localhost:8080/aracne/employees/id/" + idEmpleado);
    const data = await response.json();

    nombreEmployee.value = data.name;
    emailEmployee.value = data.email;
    emailOriginal = data.email;
    selectorRoles.value = data.role;
}

fillForm();

async function checkEmployeeEmail(email){
    const response = await fetch("http://localhost:8080/aracne/employees/email/" + email);
    const data = await response.json();
    // Se retorna la respuesta, true si este existe, y false si no
    return data["exists"];
}

async function editarEmpleado(event) {
    event.preventDefault();
    if(nombreEmployee.value == ""){
        nombreEmployeeSpan.innerText = "Este campo es obligatorio.";
    }
    else{
        nombreEmployeeSpan.innerText = "";
    }

    if(emailEmployee.value == ""){
        emailEmployeeSpan.innerText = "Este campo es obligatorio.";
    }
    else{
        emailEmployeeSpan.innerText = "";
    }

    if(passwordEmployee.value == ""){
        passwordEmployeeSpan.innerText = "Este campo es obligatorio.";
    }
    else{
        passwordEmployeeSpan.innerText = "";
    }

    let existeEmail = await checkEmployeeEmail(emailEmployee.value);

    if(existeEmail && (emailEmployee.value != emailOriginal)){
        emailEmployeeSpan.innerText = "El email indicado ya existe en la base de datos.";
    }
    else{
        emailEmployeeSpan.innerText = "";
        existeEmail = false;
    }

    if(nombreEmployee.value != "" && emailEmployee.value != "" && passwordEmployee.value != "" && !existeEmail && validatePassword(passwordEmployee.value) && validateEmail(emailEmployee.value)){
        const empleado = {
            id_empleado: idEmpleado,
            email: emailEmployee.value,
            name: nombreEmployee.value,
            role: selectorRoles.value,
            password: passwordEmployee.value
        };

    
        // Se realiza la petición de login a la API pasándole el usuario
        const response = await fetch("http://localhost:8080/aracne/employees/update", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(empleado)
        });
    
        // Tomamos la respuesta
        const data = await response.json();
        let exito = data["success"];
        
        if(exito){
            alert("El empleado ha sido modificado con éxito.");
            window.location.href = 'users.html';
        }
    }
}

function validateEmail(email){
    if(!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)){
        emailEmployeeSpan.innerText = "El email no presenta un formato correcto.";
        return false;
    }
    else{
        return true;
    }
}

function validatePassword(password) {
    if (password.length < 8) {
        passwordEmployeeSpan.innerText = "La contraseña debe tener al menos 8 caracteres.";
        return false;
    }

    if (!/[A-Z]/.test(password)) {
        passwordEmployeeSpan.innerText = "La contraseña debe contener al menos una letra mayúscula.";
        return false;
    }

    if (!/\d/.test(password)) {
        passwordEmployeeSpan.innerText = "La contraseña debe contener al menos un número.";
        return false;
    }

    if (!/[!@#$%^&*(),.?":{}|<>]/.test(password)) {
        passwordEmployeeSpan.innerText = "La contraseña debe contener al menos un símbolo especial.";
        return false;
    }

    passwordEmployeeSpan.innerText = "";
    return true;
}

showPassword.addEventListener("click", () => {
    let type = passwordEmployee.getAttribute("type");
    if(type === "password"){
        showPassword.src = "imagenes/closed_eye.png";
        passwordEmployee.type ="text";
    }
    else{
        showPassword.src = "imagenes/eye.png";
        passwordEmployee.type ="password";
    }
});