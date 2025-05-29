const idProducto = new URLSearchParams((location.href).split("?")[1]).get("id");
const nombreProducto = document.getElementById("nombre");
const precioProducto = document.getElementById("precio");
const imagenProducto = document.getElementById("imagen");
const nombreProductoSpan = document.getElementById("span-nombre-producto");
const precioProductoSpan = document.getElementById("span-precio-producto");
const imagenProductoSpan = document.getElementById("span-imagen-producto");
const tallaSProducto = document.getElementById("tallaS");
const tallaMProducto = document.getElementById("tallaM");
const tallaLProducto = document.getElementById("tallaL");
const tallaXLProducto = document.getElementById("tallaXL");
const tallaSInput = document.getElementById("tallaSInput");
const tallaMInput = document.getElementById("tallaMInput");
const tallaLInput = document.getElementById("tallaLInput");
const tallaXLInput = document.getElementById("tallaXLInput");
const botonEditar = document.getElementById("boton-productos");
const role = localStorage.getItem('role');
let nombreOriginal;

function toggleNumberInput(checkbox) {
    const container = checkbox.closest(".talla-item");
    const numberInput = container.querySelector("input[type='number']");
    numberInput.style.display = checkbox.checked ? "inline-block" : "none";
    numberInput.required = checkbox.checked ? true : false;  
    numberInput.value = 0;
}

async function fillForm(){
    const response = await fetch("http://localhost:8080/aracne/inventory/id/" + idProducto);
    const data = await response.json();

    nombreProducto.value = data.name;
    nombreOriginal = data.name;
    precioProducto.value = data.price;
    imagenProducto.value = data.image;
    if(data.s > 0){
        tallaSProducto.checked = true;
        tallaSInput.style.display = "inline-block";
        tallaSInput.value = data.s;
    }

    if(data.m > 0){
        tallaMProducto.checked = true;
        tallaMInput.style.display = "inline-block";
        tallaMInput.value = data.m;
    }

    if(data.l > 0){
        tallaLProducto.checked = true;
        tallaLInput.style.display = "inline-block";
        tallaLInput.value = data.l;
    }

    if(data.xl > 0){
        tallaXLProducto.checked = true;
        tallaXLInput.style.display = "inline-block";
        tallaXLInput.value = data.xl;
    }
}

fillForm();

async function checkProductName(name){
    const response = await fetch("http://localhost:8080/aracne/inventory/name/" + name);
    const data = await response.json();
    return data["exists"];
}

async function editarProducto(event){
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
    if(existeNombre && (nombreProducto.value != nombreOriginal)){
        nombreProductoSpan.innerText = "El nombre indicado ya existe en la base de datos.";
    }
    else{
        nombreProductoSpan.innerText = "";
        existeNombre = false;
    }

    if(nombreProducto.value != "" && precioProducto.value != 0 && imagenProducto.value != "" && !existeNombre){
        const producto = {
            id_producto: idProducto,
            name: nombreProducto.value,
            price: precioProducto.value,
            s: tallaSInput.value,
            m: tallaMInput.value,
            l: tallaLInput.value,
            xl: tallaXLInput.value,
            image: imagenProducto.value
        };

        console.log(typeof precioProducto.value)

        const response = await fetch("http://localhost:8080/aracne/inventory/update", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(producto)
        });
    
        const data = await response.json();
        let exito = data["success"];
        
        if(exito){
            alert("El producto se ha modificado correctamente.");
            if (role == "admin") {
                window.location.href = 'products.html';
            } 
            else if (role == "employee") {
                window.location.href = 'products-employee.html';
            }
        }
    }
}

botonEditar.addEventListener("click", (event) => {
    editarProducto(event);
})