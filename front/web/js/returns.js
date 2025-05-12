document.addEventListener("DOMContentLoaded", function() {
    function toggleDetalle(id) {
        let detalle = document.getElementById(id);
        if (detalle) {
            detalle.style.display = detalle.style.display === 'block' ? 'none' : 'block';
        }
    }
    
    window.toggleDetalle = toggleDetalle;
});