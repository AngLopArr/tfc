# PROYECTO INTEGRADOR

## **GESTOR DEL INVENTARIO DE MATERIALES DEL LABORATORIO DE ASIR**

Ángela López Arregui  
Dong Hyok Seo Lee  
Mario Vázquez Iniesta  
Pablo Dávila González  
Roberto Esteban García  

---

## ÍNDICE
- [Pantalla de acceso](#pantalla-de-acceso)
- [Pantallas correspondientes a alumnos y profesores sin permisos](#pantallas-correspondientes-a-alumnos-y-profesores-sin-permisos)
  - [Apartado de préstamos](#apartado-de-préstamos)
  - [Apartado de materiales](#apartado-de-materiales)
  - [Apartado de notificaciones](#apartado-de-notificaciones)
  - [Apartado para realizar un préstamo](#apartado-para-realizar-un-préstamo)
- [Pantallas competentes a coordinadores y profesores con permisos](#pantallas-competentes-a-coordinadores-y-profesores-con-permisos)
  - [Apartado de materiales](#apartado-de-materiales-para-administradores)
  - [Apartado de incidencias](#apartado-de-incidencias)
  - [Apartado para crear un informe](#apartado-para-crear-un-informe)
- [Vista general del flujo entre pantallas](#vista-general-del-flujo-entre-pantallas)

---

## Pantalla de acceso

Esta pantalla permite a un usuario iniciar sesión. Esta cuenta con un campo de texto para que el usuario introduzca su correo, es decir, su identificador, otro campo para que introduzca su contraseña y un botón para realizar el acceso.

<p align="center">
  <img src="IMG_readme/Imagen1.png" alt="Pantalla de acceso">
</p>

---

## Pantallas correspondientes a alumnos y profesores sin permisos

Hemos decidido que estos dos tipos de usuario se vean englobados en el mismo dentro de la aplicación, ya que su experiencia dentro de la misma es idéntica.

### Apartado de préstamos

Estos usuarios, tras identificarse y acceder a la aplicación, se verán redirigidos a la pantalla que les permitirá visualizar sus préstamos activos.

<p align="center">
  <img src="IMG_readme/Imagen23.png" alt="Apartado de préstamos">
</p>

Cada préstamo presentará un botón que permitirá registrar la devolución del material correspondiente. Tras realizar una devolución, el usuario deberá clicar el botón del préstamo correspondiente al material que acabe de depositar. Entonces, la cámara de su móvil se abrirá, permitiéndole escanear el código QR del material, registrando la reincorporación del mismo al laboratorio en la base de datos.

<p align="center">
  <img src="IMG_readme/Imagen4.png" alt="Categorías de materiales">
</p>

Además, cada material contará con un botón de incidencias, representado por un icono de exclamación, que abrirá una ventana donde el usuario podrá reportar cualquier problema o incidencia relacionada con el material que haya tomado prestado. Esta ventana incluirá un botón para enviar la incidencia registrada.

### Apartado de materiales

Si el usuario clica sobre el icono correspondiente situado en la barra de navegación, será redirigido al apartado de materiales, es decir, a la sección correspondiente al inventario.

<p align="center">
  <img src="IMG_readme/Imagen5.png" alt="Categorías de materiales">
</p>

La primera pantalla que se le presentará al usuario al acceder a esta sección muestra las diferentes categorías de materiales existentes en el inventario. Al clicar en cada una de las categorías se desplegará la lista de materiales pertenecientes a la misma. 

Para obtener más detalles sobre un material específico, el usuario podrá pulsar sobre este, lo cual abrirá una ventana que mostrará todas sus características. En esta ventana se mostrará: el nombre del material, el estado, la fecha de adquisición, el fabricante y la cantidad disponible, si el material es un conjunto y no una entidad individual.

### Apartado de notificaciones

El usuario dispondrá de un icono de una campana en la parte superior derecha de su pantalla en todo momento.

<p align="center">
  <img src="IMG_readme/Imagen6.png" alt="Notificaciones">
</p>

Si clica sobre este, se desplegará un listado donde se verán todas las notificaciones dirigidas al mismo enviadas por los administradores. El símbolo de la campana presentará con un contador que reflejará el número de notificaciones que tiene actualmente el usuario.

### Apartado para realizar un préstamo

Para solicitar un préstamo, el usuario deberá pulsar el botón de acción flotante, ubicado en la parte inferior de todas y cada una de las pantallas del usuario.

<p align="center">
  <img src="IMG_readme/Imagen7.png" alt="Realización de préstamo">
</p>

Al hacerlo, se abrirá la cámara de su móvil, lo que permitirá al usuario escanear el código QR del material. Una vez escaneado el código, se abrirá una ventana de préstamo en la que se mostrarán las características del material a tomar. El usuario deberá seleccionar la asignatura correspondiente a la clase en la que vaya a utilizar dicho material y, finalmente, pulsar el botón de ‘Realizar préstamo’ para completar la solicitud.

---

## Pantallas competentes a coordinadores y profesores con permisos

Hemos decidido que estos dos tipos de usuario se vean englobados en el mismo dentro de la aplicación, este sería el usuario administrador, ya que su experiencia dentro de la misma es idéntica.

### Apartado de materiales para administradores

Los usuarios categorizados como administradores, tras identificarse y acceder a la aplicación, se verán redirigidos al apartado de materiales, en concreto a la pantalla de categorías.

<p align="center">
  <img src="IMG_readme/Imagen9.png" alt="Categorías para administradores">
</p>

Esta primera pantalla presenta las diferentes categorías a las que puede pertenecer un material presente en el inventario. 

Cuando un administrador clica sobre cada una de estas, es redirigido a otra pantalla donde se muestran todos los materiales pertenecientes a la misma.

A su vez, al clicar sobre uno de los materiales, el administrador será enviado a otra pantalla que le presentará todos los detalles del mismo. 

En esta, asimismo, este tendrá la opción de, a través de un desplegable que contiene todos los estados que puede presentar un material, modificar el estado de aquel que esté visualizando.

### Apartado de incidencias

Si el administrador clica sobre el icono correspondiente situado en la barra de navegación, será redirigido al apartado de incidencias.

<p align="center">
  <img src="IMG_readme/Imagen10.png" alt="Incidencias">
</p>

En esta pantalla se mostrarán todas las incidencias registradas, tanto por los usuarios como automáticamente, acerca de los materiales presentes en el inventario. Cada una de las incidencias presenta un botón que permite descartarla una vez solucionada.

### Apartado para crear un informe

Para acceder a esta ventana hemos de clicar sobre el botón de acción flotante presente en cada una de las pantallas de la sección de materiales. Este no se encuentra presente en la sección de incidencias.

<p align="center">
  <img src="IMG_readme/Imagen11.png" alt="Creación de informes">
</p>

Esta ventana contiene un desplegable que permite al administrador elegir el tipo de informe que desea generar. A su vez, contiene un botón que, al clicarlo, presenta una serie de filtros que el usuario podrá aplicar sobre este.

Una vez ha establecido todas las características del informe que desea crear, podrá pulsar el botón ‘Descargar informe’, completando así la generación del mismo y procediendo a su descarga.
