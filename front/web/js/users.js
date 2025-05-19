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

const tabla = document.getElementById("tabla-usuarios"); 
const totalPaginas = document.getElementById("paginas-totales"); 
const botonAvanzar = document.getElementById("boton-avanzar");
const botonRetroceder = document.getElementById("boton-retroceder");
const paginaActualSpan = document.getElementById("pagina-actual");
const barraBusqueda = document.getElementById("barra-busqueda");
const lupa = document.getElementById("lupa");

let pagina_actual = 1;
let total_products = 0;
paginaActualSpan.innerText = pagina_actual;

async function fillTable(){
    let data;

    tabla.innerHTML = `
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Rol</th>
            <th>Acciones</th>
        </tr>
    `;

    if(barraBusqueda.value == ""){
        const response = await fetch("http://localhost:8080/aracne/employees/group/" + pagina_actual);

        if(response.status === 404){
            pagina_actual = 0;
            paginaActualSpan.innerText = pagina_actual;
            return;
        }
        else{
            paginaActualSpan.innerText = pagina_actual;
        }

        data = await response.json();
    }
    else{
        const response = await fetch("http://localhost:8080/aracne/employees/search/" + pagina_actual + "/" + barraBusqueda.value);

        if(response.status === 404){
            pagina_actual = 0;
            paginaActualSpan.innerText = pagina_actual;
            return;
        }
        else{
            paginaActualSpan.innerText = pagina_actual;
        }

        data = await response.json();
    }

    for (let index = 0; index < data.length; index++) {
        const empleado = data[index];
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td class="id">${empleado.id_empleado}</td>
                <td>${empleado.name}</td>
                <td>${empleado.email}</td>
                <td>${empleado.role}</td>
                <td class="acciones">
                <button class="boton-editar" id_empleado="${empleado.id_empleado}">Editar</button>
                <button class="boton-eliminar" id_empleado="${empleado.id_empleado}">Eliminar</button>
            </td>
        `;
        tabla.appendChild(fila);
    }

    const botonesEliminar =  document.querySelectorAll(".boton-eliminar");

    for (let index = 0; index < botonesEliminar.length; index++) {
        botonesEliminar[index].addEventListener("click", () => {
            let confirmacion = confirm("Esta acción eliminará al empleado permanentemente. ¿Está seguro/a de que desea continuar?");
            if(confirmacion){
                eliminarProducto(botonesEliminar[index]);
            }
        });
    }

    const botonesEditar =  document.querySelectorAll(".boton-editar");

    for (let index = 0; index < botonesEditar.length; index++) {
        botonesEditar[index].addEventListener("click", () => {
            irFormularioEditar(botonesEditar[index]);
        });
    }
}

fillTable();

async function getTotalEmployees(){
    let data;

    if(barraBusqueda.value == ""){
        const response = await fetch("http://localhost:8080/aracne/employees");

        if(response.status === 404){
            pagina_actual = 0;
            paginaActualSpan.innerText = pagina_actual;
            return;
        }

        data = await response.json();
    }
    else{
        const response = await fetch("http://localhost:8080/aracne/employees/total/" + barraBusqueda.value);

        if(response.status === 404){
            pagina_actual = 0;
            paginaActualSpan.innerText = pagina_actual;
            return;
        }

        data = await response.json();
    }

    total_products = data["total"];

    if(Number.isInteger(total_products / 8)){
        totalPaginas.innerText = (total_products / 8);
    }
    else{
        totalPaginas.innerText = ((total_products / 8) + 1).toString().replace(/\..*/, "");
    }

    if(pagina_actual.toString() === totalPaginas.innerText){
        botonAvanzar.disabled = true;
    }
    else{
        botonAvanzar.disabled = false;
    }
    
    if(pagina_actual === 1 || pagina_actual === 0){
        botonRetroceder.disabled = true;
    }
    else{
        botonRetroceder.disabled = false;
    }
}

getTotalEmployees();

botonAvanzar.addEventListener("click", () => {
    pagina_actual += 1;
    fillTable();

    if(pagina_actual.toString() === totalPaginas.innerText){
        botonAvanzar.disabled = true;
    }
    else{
        botonAvanzar.disabled = false;
    }

    if(pagina_actual === 1 || pagina_actual === 0){
        botonRetroceder.disabled = true;
    }
    else{
        botonRetroceder.disabled = false;
    }
});

botonRetroceder.addEventListener("click", () => {
    pagina_actual -= 1;
    fillTable();

    if(pagina_actual.toString() === totalPaginas.innerText){
        botonAvanzar.disabled = true;
    }
    else{
        botonAvanzar.disabled = false;
    }

    if(pagina_actual === 1 || pagina_actual === 0){
        botonRetroceder.disabled = true;
    }
    else{
        botonRetroceder.disabled = false;
    }
});

async function eliminarProducto(botonEliminar){
    let id = botonEliminar.getAttribute("id_empleado");

    const response = await fetch("http://localhost:8080/aracne/employees/delete/" + id, {
        method: "DELETE",
        headers: { "Content-Type": "application/json" }
    });

    // Tomamos la respuesta
    const data = await response.json();
    let exito = data["success"];
    
    if(exito){
        alert("El empleado se ha eliminado correctamente.");
        location.reload();
    }
    else{
        alert("No se ha podido eliminar el empleado.");
    }
}

function irFormularioEditar(botonEditar){
    let id = botonEditar.getAttribute("id_empleado");
    window.location.href = 'modify-users.html?id=' + id;
}


barraBusqueda.addEventListener("input", () => {
    if(barraBusqueda.value == ""){
        pagina_actual = 1;
        getTotalEmployees();
        fillTable();
    }
});

lupa.addEventListener("click", () => {
    pagina_actual = 1;
    getTotalEmployees();
    fillTable();
})