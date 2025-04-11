document.addEventListener("DOMContentLoaded", function() {
    function toggleDetalle(id) {
        let detalle = document.getElementById(id);
        if (detalle) {
            detalle.style.display = detalle.style.display === 'none' ? 'block' : 'none';
        }
    }
    
    window.toggleDetalle = toggleDetalle;
});