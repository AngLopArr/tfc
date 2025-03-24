// Al cargar la página
window.onload = function() {
    // Se comprueba si el usuario está logueado, de no ser así, se le envía a la página de login
    if (localStorage.getItem('loggedIn') !== 'true') {
        window.location.href = 'login.html';
    }

    if(localStorage.getItem('role') !== 'admin'){
        let enlaces = document.getElementsByClassName('admin');
        for (let index = 0; index < enlaces.length; index++) {
            enlaces[index].hidden = true;
        }
    }

    // Se crea un evento de click para el botón de logout
    document.getElementById('logout').addEventListener('click', function() {
        // Se borrará el elemento del local storage que almacena el estado de acceso
        localStorage.removeItem('loggedIn');
        // Se borrará el elemento del local storage que almacena el nombre del usuario logueado
        localStorage.removeItem('user');
        // Se enviará al cliente a la página de login
        window.location.href = 'login.html';
    });
};