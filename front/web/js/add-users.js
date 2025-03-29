let nombreEmployee = document.getElementById("nombre");
let emailEmployee = document.getElementById("email");
let passwordEmployee = document.getElementById("password");
let selectorRoles = document.getElementById("select-rols");
let nombreEmployeeSpan = document.getElementById("span-nombre-user");
let emailEmployeeSpan = document.getElementById("span-email-user");
let passwordEmployeeSpan = document.getElementById("span-password-user");

let boton = document.getElementById("boton-users");
boton.addEventListener("click", e => creation(e));

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

async function checkEmployeeEmail(email){
    const response = await fetch("http://localhost:8080/aracne/employees/email/" + email);
    const data = await response.json();
    // Se retorna la respuesta, true si este existe, y false si no
    return data["exists"];
}

async function creation(event) {
    event.preventDefault();
    if(nombreEmployee.value == ""){
        nombreEmployeeSpan.innerText = "Este campo es obligatorio.";
    }
    else{
        nombreEmployeeSpan.innerText = "";
    }

    if(emailEmployee.value == 0){
        emailEmployeeSpan.innerText = "Este campo es obligatorio y no puede ser igual a 0.";
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

    if(existeEmail){
        emailEmployeeSpan.innerText = "El nombre indicado ya existe en la base de datos.";
    }
    else{
        emailEmployeeSpan.innerText = "";
    }

    if(nombreEmployee.value != "" && emailEmployee.value != "" && passwordEmployee.value != "" && !existeEmail && validatePassword(passwordEmployee.value)){
        const empleado = {
            email: emailEmployee.value,
            name: nombreEmployee.value,
            role: selectorRoles.value,
            password: passwordEmployee.value
        };

    
        // Se realiza la petición de login a la API pasándole el usuario
        const response = await fetch("http://localhost:8080/aracne/employees/create", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(empleado)
        });
    
        // Tomamos la respuesta
        const data = await response.json();
        let exito = data["success"];
        
        if(exito){
            document.getElementById("form-add-users").reset();
            alert("El empleado ha sido añadido con éxito.")
        }
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