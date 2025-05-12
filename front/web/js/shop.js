window.onload = function() {
    if (localStorage.getItem('loggedIn') !== 'true') {
        window.location.href = 'login.html';
    }

    document.getElementById('logout').addEventListener('click', function() {
        localStorage.removeItem('loggedIn');
        
        localStorage.removeItem('user');
        
        window.location.href = 'login.html';
    });
};