# apiusuario
Creacion api usuario - prueba tecnica BCI

Para ingresar a visualizar el swagger seguir la siguiente ruta: http://localhost:8080/swagger-ui/index.html

Para ingresar a la consola de h2 seguir la siguiente ruta: http://localhost:8080/h2-console
Autenticacion para h2 console: 
    - jdbcUrl: jdbc:h2:mem:testdb
    - usuario: sa
    - password: password
    Dichos datos están específicados en el archivo application.properties

La aplicacion está configurada para que al iniciarse se lean las entidades y se creen las tablas correspondientes en la base de datos. 


