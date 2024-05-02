En esta aplicación se pueden visualizar los personajes de Marvel que están en el api de Marvel, me basé en la construcción con la documentcación presentada por el mismo api: https://developer.marvel.com/docs

Componentes de la aplicación:
* La aplicación tiene una arquitectura MVVM.
* Inyección de dependencias con Dagger Hilt.
* Consumo del servicio con retrofit, consumos asíncronos cuando no hay conexión de internet.
* Base de datos local con ROOM tanto para el login o creación de usuario como para guardar los personajes favoritos.
* Vistas nativas con xml en su mayoría con "ConstraintLayout", "FrameLayout" y "NestedScrollView" para el tema de los recycler.
* Navegación entre fragments con safeargs "Navigation Components" tengo un NavGraph para la navegación de los fragments tanto para el login como del home y demás vistas.
* Procesamiento de imágenes con Glide.
* Uso de corrutinas para el consumo del servicio, inserción a la bd y funcionalidades en segundo plano
* Uso de funcionalidades asíncronas para el consumo asíncrono cuando no hay red.

Especificaciones y flujo de la aplicación:

* Primera vista de inducción, con un botón de "Comencemos" para navegar a la siguiente vista.
* 
* Segunda vista, con 2 opciones:
    *Opción 1: Registrar: en esta opción el usuario podrá crear una cuenta con "Email", "Nombre" y "Contraseña", aqui tiene una serie de validaciones: los campos no pueden estar vacíos, validaciones básicas para el "Email" y mínimo 8 caracteres para la contraseña
    *Opción 2: Login: Si el usuario ya tiene una cuenta registrada en la aplicación podrá ingresar con su "Correo" y "Contraseña", si el registro es exitoso puede navegar a la siguiente vista.

* Tercera vista: Es la vista de Home, en la cual tiene un menú en la parte inferior, en esta puede navegar a las vistas de "Home", "Favoritos" y "Perfil":
    Vista de Home:
      En la vista de Home puede ver todos los personajes de Marvel consumidos del api, haciendo un Scroll infinito hasta que se terminen todos los personajes, en esta vista también se valida la conectividad a internet haciendo un consumo asíncrono cuando no hay red.
      En la vista de Home, cada personaje en la parte superior derecha tiene un icono para agregar el personaje a favoritos.
      En la vista de Home cuando selecciona un personaje navega a otra vista dónde puede ver a más detalle: El nombre del personaje, La descripción, Los commits, Las series, Las historias Y los eventos, de igual manera puede regresar a Home con la "X" de la parte superior Izquierda o guardar el personaje a Favoritos con el "Corazón" en la parte superior derecha.

  * Cuarta Vista: Es la vista de Personajes Agregados o "Favoritos":
    En la vista de Favoritos puede observar los personajes que ha marcado como favoritos, puede navegar a esta vista en manera offline "sin conexión".
    En la vista de Favoritos al seleccionar un ítem o un personaje, navegará a una vista parecida a la de Home para detallar el personaje, en esta vista la parte superior izquierda se puede regresar a la vista anterior con la "X" y en la parte superior derecha puede eliminar el personaje o quitarlo de favoritos con el icono de "basura"

   *Quinta vista: Vista de perfil o ajustes:
    En la vista de perfil tiene una única opción que es para cerrar la sesión o salir, al darle click el usuario puede escoger entre cerrar o no sesión, si cierra sesión tendrá que volver a autenticarse.
