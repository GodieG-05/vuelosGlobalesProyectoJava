<p style="font-size: 50px;"><b>Vuelos Globales - Sistema de Gestión de Agencia de Viajes</b></p>



<p style="font-size: 25px;"><b>Descripción</b></p>

Vuelos Globales es un sistema desarrollado en Java que gestiona una agencia de viajes especializada en vuelos internacionales. Permite desde la reserva de vuelos hasta la administración completa de la flota de aviones, tripulación y conexiones aéreas. 

El sistema utiliza una base de datos relacional para almacenar y gestionar toda la información necesaria para el funcionamiento de la agencia. Está estructurado siguiendo la arquitectura hexagonal con vertical slicing, lo cual organiza el código en capas específicas para cada entidad y caso de uso. 



<p style="font-size: 25px;"><b>Tecnologías Implementadas</b></p>

**El desarrollo del proyecto se fundamenta en tecnologías modernas que incluyen:**

* **Java**: Lenguaje de programación principal por su robustez, portabilidad y amplio soporte en la comunidad de desarrollo.

* **MySQL**: Sistema de gestión de bases de datos relacional que proporciona almacenamiento eficiente y consulta de datos.

* **Arquitectura Hexagonal**: Implementación de una estructura modular que facilita la integración y extensión del sistema.

* **Vertical Slicing**: Organización del código en capas específicas que mejoran la separación de preocupaciones y la claridad estructural.

* **Herramientas de Desarrollo**: Utilización de herramientas como Git para control de versiones y Maven para gestión y construcción de proyectos, asegurando un flujo de desarrollo eficiente y colaborativo.

  

<p style="font-size: 25px;"><b>Estructura del Proyecto</b></p>

**El proyecto está organizado de la siguiente manera: **

* **domain:** Contiene las clases que representan las entidades del sistema, con constructores, atributos y métodos getters/setters. 

* **infraestructure:** Almacena las interfaces de repositorio que definen operaciones estándar como save, update, delete, etc. 

* **application:** Contiene las clases de servicio que implementan la lógica de negocio utilizando los repositorios definidos. 

* **adapters: in:** Clases que manejan la entrada de datos, como la consola, para casos de uso específicos. 

* **adapters: out:** Implementación de repositorios utilizando JDBC para la persistencia de datos en MySQL. 



<p style="font-size: 25px;"><b>Funcionalidades Principales</b></p>

**El sistema ofrece diversas funcionalidades esenciales para la gestión integral de una agencia de viajes especializada en vuelos internacionales:**

* Registro y gestión detallada de clientes, empleados, aerolíneas, aviones, tripulación y conexiones aéreas.

* Reserva de vuelos y gestión completa de las reservas, incluyendo tarifas y detalles específicos de cada vuelo.

* Mantenimiento de datos geográficos como aeropuertos, ciudades, países y puertas de embarque para una gestión eficiente de conexiones aéreas.

* Seguimiento de revisiones técnicas de aviones para asegurar el cumplimiento de normativas de seguridad y mantenimiento.

* Administración de roles de tripulación y asignación eficiente a conexiones aéreas según los requisitos operativos.

  

<p style="font-size: 25px;"><b>Equipo de Desarrollo</b></p>

* Diego Alejandro Galan Martinez - diegogalanm@gmail.com 

* Samuel Rubiano Orjuela - samuelrubiano0702@gmail.com
