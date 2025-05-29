let nombreProducto = document.getElementById("nombre");
let precioProducto = document.getElementById("precio");
let imagenProducto = document.getElementById("imagen");
let tallaS = document.getElementById("tallaSInput");
let tallaM = document.getElementById("tallaMInput");
let tallaL = document.getElementById("tallaLInput");
let tallaXL = document.getElementById("tallaXLInput");
let nombreProductoSpan = document.getElementById("span-nombre-producto");
let precioProductoSpan = document.getElementById("span-precio-producto");
let imagenProductoSpan = document.getElementById("span-imagen-producto");

function toggleNumberInput(checkbox) {
    const container = checkbox.closest(".talla-item");
    const numberInput = container.querySelector("input[type='number']");
    numberInput.style.display = checkbox.checked ? "inline-block" : "none";
    numberInput.required = checkbox.checked ? true : false;    
    numberInput.value = 0;
}

let boton = document.getElementById("boton-productos");
boton.addEventListener("click", e => creation(e));

async function checkProductName(name){
    const response = await fetch("http://localhost:8080/aracne/inventory/name/" + name);
    const data = await response.json();
    return data["exists"];
}

async function creation(event) {
    event.preventDefault();
    if(nombreProducto.value == ""){
        nombreProductoSpan.innerText = "Este campo es obligatorio.";
    }
    else{
        nombreProductoSpan.innerText = "";
    }

    if(precioProducto.value <= 0){
        precioProductoSpan.innerText = "Este campo es obligatorio y no puede ser igual o menor que 0.";
    }
    else{
        precioProductoSpan.innerText = "";
    }

    if(imagenProducto.value == ""){
        imagenProductoSpan.innerText = "Este campo es obligatorio.";
    }
    else{
        imagenProductoSpan.innerText = "";
    }

    let existeNombre = await checkProductName(nombreProducto.value);
    if(existeNombre){
        nombreProductoSpan.innerText = "El nombre indicado ya existe en la base de datos.";
    }
    else{
        nombreProductoSpan.innerText = "";
    }

    if(nombreProducto.value != "" && precioProducto.value != 0 && imagenProducto.value != "" && !existeNombre){
        const producto = {
            name: nombreProducto.value,
            price: precioProducto.value,
            s: tallaS.value,
            m: tallaM.value,
            l: tallaL.value,
            xl: tallaXL.value,
            image: imagenProducto.value
        };

        const response = await fetch("http://localhost:8080/aracne/inventory/create", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(producto)
        });
    
        const data = await response.json();
        let exito = data["success"];
        
        if(exito){
            document.getElementById("form-create-products").reset();
            toggleNumberInput(document.getElementById("tallaS"));
            toggleNumberInput(document.getElementById("tallaM"));
            toggleNumberInput(document.getElementById("tallaL"));
            toggleNumberInput(document.getElementById("tallaXL"));
            alert("El producto se ha creado correctamente.");
        }
    }
}