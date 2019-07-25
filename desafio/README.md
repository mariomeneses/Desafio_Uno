Api Rest que consume la Api "Generador Datos Desafio" y entrega como resultado:

- id: Indentificador
- fechaCreacion: Fecha Inicio
- fechaFin: Fecha Fin
- fechas: Lista de fechas que están en el rango de la fecha que se encuentra en "fechaCreacion" 
hasta la fecha "fechaFin" 
- fechasFaltantes: Lista de fechas que no se encuentran dentro "fechas" pero corresponden al 
rango entre "fechaCreacion" a"fechaFin".

Detalle de los sistemas
Java 8 Spring-Boot 2.1.6.RELEASE gradle-5.4.1

Documentacion:
http://localhost:8084/swagger-ui.html#!/

Compilar:
Ingresar al proyecto mediante terminal y posicionarse en la ruta "desafio" y ejecutar:
gradlew clean build

Ejecutar:
dentro de la misma ruta que se compiló, ejecutar siguiente comando:
java -jar build\libs\desafio-0.0.1-SNAPSHOT.jar

Request URL:
http://localhost:8084/api/desafio

Request Headers:
 "Accept": "application/json"


