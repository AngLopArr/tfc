let pagina_actual = 1;
let total_products = 0;
const tabla = document.getElementById("tabla-productos"); 
const totalPaginas = document.getElementById("paginas-totales"); 
const botonAvanzar = document.getElementById("boton-avanzar");
const botonRetroceder = document.getElementById("boton-retroceder");
const paginaActualSpan = document.getElementById("pagina-actual");
paginaActualSpan.innerText = pagina_actual;

async function fillTable(){
    const response = await fetch("http://localhost:8080/aracne/inventory/group/" + pagina_actual);
    const data = await response.json();
    tabla.innerHTML = `
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>S</th>
            <th>M</th>
            <th>L</th>
            <th>XL</th>
            <th>Imagen</th>
            <th>Acciones</th>
        </tr>
    `;

    for (let index = 0; index < data.length; index++) {
        const producto = data[index];
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td class="id">${producto.id_producto}</td>
            <td>${producto.name}</td>
            <td class="precio">${producto.price}</td>
            <td class="talla">${producto.s}</td>
            <td class="talla">${producto.m}</td>
            <td class="talla">${producto.l}</td>
            <td class="talla">${producto.xl}</td>
            <td class="imagen">
                <img src="${producto.image}" alt="${producto.nombre}">
            </td>
            <td class="acciones">
                <button class="edit-btn">Editar</button> 
                <button class="delete-btn">Eliminar</button>
            </td>
        `;
        tabla.appendChild(fila);
    }
}

fillTable();

async function getTotalProducts(){
    const response = await fetch("http://localhost:8080/aracne/inventory");
    const data = await response.json();

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
    
    if(pagina_actual === 1){
        botonRetroceder.disabled = true;
    }
    else{
        botonRetroceder.disabled = false;
    }
}

getTotalProducts();

botonAvanzar.addEventListener("click", () => {
    pagina_actual += 1;
    fillTable();

    if(pagina_actual.toString() === totalPaginas.innerText){
        botonAvanzar.disabled = true;
    }
    else{
        botonAvanzar.disabled = false;
    }

    if(pagina_actual === 1){
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

    if(pagina_actual === 1){
        botonRetroceder.disabled = true;
    }
    else{
        botonRetroceder.disabled = false;
    }
});