function toggleNumberInput(checkbox) {
    const container = checkbox.closest(".talla-item");
    const numberInput = container.querySelector("input[type='number']");
    numberInput.style.display = checkbox.checked ? "inline-block" : "none";
    numberInput.required = checkbox.checked ? true : false;
}

let nombreProducto = document.getElementById("nombre");
let precioProducto = document.getElementById("precio");
let imagenFile = document.getElementById("imagen");
let tallaS = document.getElementById("tallaSInput");
let tallaM = document.getElementById("tallaMInput");
let tallaL = document.getElementById("tallaLInput");
let tallaXL = document.getElementById("tallaXLInput");
let nombreProductoSpan = document.getElementById("span-nombre-producto");
let precioProductoSpan = document.getElementById("span-precio-producto");
let imagenFileSpan = document.getElementById("span-imagen-producto");
let imagen = "";

imagenFile.addEventListener("change", e => {
    let file = imagenFile.files[0];
    let reader = new FileReader();
    reader.addEventListener("load", e => {
        imagen = reader.result;
    });

    reader.readAsDataURL(file);
})

let boton = document.getElementById("boton-productos");
boton.addEventListener("click", e => creation(e));

async function creation(event) {
    event.preventDefault();
    if(nombreProducto.value == ""){
        nombreProductoSpan.innerText = "Este campo es obligatorio.";
    }
    else{
        nombreProductoSpan.innerText = "";
    }

    if(precioProducto.value == 0){
        precioProductoSpan.innerText = "Este campo es obligatorio y no puede ser igual a 0.";
    }
    else{
        precioProductoSpan.innerText = "";
    }

    if(imagenFile.value == ""){
        imagenFileSpan.innerText = "Este campo es obligatorio.";
    }
    else{
        imagenFileSpan.innerText = "";
    }

    if(nombreProducto.value != "" && precioProducto.value != 0 && imagenFile.value != ""){
        const producto = {
            name: nombreProducto.value,
            price: precioProducto.value,
            s: tallaS.value,
            m: tallaM.value,
            l: tallaL.value,
            xl: tallaXL.value,
            image: imagen
        };

    
        // Se realiza la petición de login a la API pasándole el usuario
        const response = await fetch("http://localhost:8080/aracne/inventory/create", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(producto)
        });
    
        // Tomamos la respuesta
        const data = await response.json();
        let exito = data["success"];
        console.log(exito);
    }
}
