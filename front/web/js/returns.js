const maindiv = document.getElementById("devoluciones"); 
const fechaInicio = document.getElementById("fechaInicio");
const fechaFin = document.getElementById("fechaFin");
const reset = document.getElementById("reset-devoluciones");
const totalPaginas = document.getElementById("paginas-totales"); 
const botonAvanzar = document.getElementById("boton-avanzar");
const botonRetroceder = document.getElementById("boton-retroceder");
const paginaActualSpan = document.getElementById("pagina-actual");

let pagina_actual = 1;
let total_products = 0;
paginaActualSpan.innerText = pagina_actual;

function toggleDetalle(id) {
    let detalle = document.getElementById(id);
    if (detalle) {
        detalle.style.display = detalle.style.display === 'block' ? 'none' : 'block';
    }
}

async function fillPage(){
    let data;
    maindiv.innerHTML = "";

    if(fechaInicio.value != "" && fechaFin.value != ""){
        if(fechaFin.value > fechaInicio.value){
            const fechas = {
                fechaInicio: fechaInicio.value + "T00:00:00",
                fechaFin: fechaFin.value + "T23:59:59"
            };

            const response = await fetch("http://localhost:8080/aracne/devoluciones/search/" + pagina_actual, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(fechas)
            });

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
    }
    else{
        const response = await fetch("http://localhost:8080/aracne/devoluciones/group/" + pagina_actual);

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
        const devolucion = data[index];
        maindiv.innerHTML += `
            <div class="devolucion" onclick="toggleDetalle(${devolucion.id_devolucion})">
                <h3>Devolución realizada ${devolucion.fechaDevolucion.replace("T", " ").substring(0, 16)}</h3>
                <p><span class="estado_devolucion">${devolucion.estado}</span> - ${Object.keys(devolucion.productosDevueltos).length} artículos</p>
            </div>
            <div id="${devolucion.id_devolucion}" class="devolucion-detalle"></div>
        `;
        const detalle = document.getElementById(`${devolucion.id_devolucion}`);
        for (let index = 0; index < devolucion.productosDevueltos.length; index++) {
            detalle.innerHTML += `
                <div class="producto_devolucion">
                    <img src="${devolucion.productosDevueltos[index].producto.image}" class="img_prod_devolucion">
                    <p class="detalles_prod_devolucion">${devolucion.productosDevueltos[index].producto.name} x${devolucion.productosDevueltos[index].cantidadDevuelta} <br><b>Talla ${devolucion.productosDevueltos[index].talla}</b> - ${devolucion.productosDevueltos[index].producto.price} €</p>
                </div>
                <br>
            `
        }
        if(devolucion.estado == "procesando"){
            detalle.innerHTML += `
                <button class="accept-btn" id_devolucion="${devolucion.id_devolucion}">Aceptar</button>
                <button class="reject-btn" id_devolucion="${devolucion.id_devolucion}">Rechazar</button>
            `
        }
    }
}

fillPage();

async function getTotalDevoluciones(){
    let data;

    if(fechaInicio.value != "" && fechaFin.value != ""){
        if(fechaFin.value > fechaInicio.value){
            const fechas = {
                fechaInicio: fechaInicio.value + "T00:00:00",
                fechaFin: fechaFin.value + "T23:59:59"
            };

            const response = await fetch("http://localhost:8080/aracne/devoluciones/total", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(fechas)
            });

            data = await response.json();
        }
    }
    else{
        const response = await fetch("http://localhost:8080/aracne/devoluciones");

        data = await response.json();
    }

    total_products = data["total"];

    if(Number.isInteger(total_products / 5)){
        totalPaginas.innerText = (total_products / 5);
    }
    else{
        totalPaginas.innerText = ((total_products / 5) + 1).toString().replace(/\..*/, "");
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

getTotalDevoluciones();

botonAvanzar.addEventListener("click", () => {
    pagina_actual += 1;
    paginaActualSpan.innerText = pagina_actual;
    fillPage();

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
    paginaActualSpan.innerText = pagina_actual;
    fillPage();

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

reset.addEventListener("click", async () => {
    fechaInicio.value = "";
    fechaFin.value = "";
    pagina_actual = 1;
    await fillPage();
    getTotalDevoluciones();
});

fechaInicio.addEventListener("change", async () => {
    if(fechaInicio.value != "" && fechaFin.value != ""){
        if(fechaFin.value > fechaInicio.value){
            pagina_actual = 1;
            await fillPage();
            getTotalDevoluciones();
        }
        else {
            alert("La fecha de inicio no puede ser mayor a la fecha de fin.");
            fechaInicio.value = "";
            fechaFin.value = "";
            pagina_actual = 1;
            await fillPage();
            getTotalDevoluciones();
        }
    }
});

fechaFin.addEventListener("change", async () => {
    if(fechaInicio.value != "" && fechaFin.value != ""){
        if(fechaFin.value > fechaInicio.value){
            pagina_actual = 1;
            await fillPage();
            getTotalDevoluciones();
        }
        else {
            alert("La fecha de inicio no puede ser mayor a la fecha de fin.");
            fechaInicio.value = "";
            fechaFin.value = "";
            pagina_actual = 1;
            await fillPage();
            getTotalDevoluciones();
        }
    }
});