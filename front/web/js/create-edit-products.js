function toggleNumberInput(checkbox) {
    const container = checkbox.closest(".talla-item");
    const numberInput = container.querySelector("input[type='number']");
    numberInput.style.display = checkbox.checked ? "inline-block" : "none";
    numberInput.required = checkbox.checked ? true : false;
}

let nombreProducto = document.getElementById("nombre").value;
let precioProducto = document.getElementById("precio").value;
let imagenFile = document.getElementById("imagen").value;

let boton = document.getElementById("boton-productos");
boton.addEventListener("click", e => {
    e.preventDefault();
    console.log(imagenFile === "");
    /*let file = imagenFile.files[0];

    let reader = new FileReader();
    reader.addEventListener("load", e => {
        console.log(reader.result);
    });

    reader.readAsDataURL(file);*/
});

