// Al cargar la página
window.onload = function() {
    if(localStorage.getItem('role') !== 'admin'){
        window.location.href = 'login.html';
    }
};