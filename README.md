# BLP-examples

[Project done in Dec 2020]

El propósito de los sistemas es poder entender de manera práctica las diferencias que
existen en utilizar un sistema sin ningún nivel de seguridad (InsecureSystem) en
comparación con otro sistema con un nivel de seguridad simple (SecureSystem).

También mostrar que pueden existir vulnerabilidades en los sistemas seguros. Para esto se
realizará un canal encubierto para transmitir un mensaje desde un sujeto con un nivel de
autorización superior a otro sujeto con un nivel de autorización inferior
(SecureSystemWithCovertChannel).

## InsecureSystem

El propósito del InsecureSystem es implementar un sistema de seguridad simple, en Java, siguiendo las reglas de seguridad de Bell-LaPadula (BLP), seguridad simple, la propiedad * y la tranquilidad fuerte. El objetivo es que sea un sistema simple no seguro.

Características del sistema:
- Los objetos en este sistema son variables enteras simples.
- Cada objeto tiene un nombre y un valor (inicialmente 0).
- Cada sujeto tiene un nombre y una variable entera TEMP que registra el valor que
leyó más recientemente (también inicialmente 0).
- Los sujetos pueden realizar operaciones READ o WRITE en los objetos.
- Para un READ, el sujeto lee el valor actual del objeto y guarda ese valor en su
variable TEMP (un READ posterior le pasará por arriba).
- Cuando un sujeto hace un WRITE, el valor del objeto se actualiza.
- La entrada a su sistema es un archivo de comandos.
Instrucciones habilitadas del sistema:
- READ nombre_sujeto nombre_objeto
- WRITE nombre_sujeto nombre_objeto valor

Para las instrucciones con error, se generará la siguiente instrucción especial:
- BadInstruction

## SecureSystem

El propósito de SecureSystem es implementar una mejora sobre el sistema de InsecureSystem proveyendo una capa de seguridad asociado a los sujetos y objetos del sistema.

Características del sistema:
- Los sujetos tienen asociados niveles de autorización (HIGH, MEDIUM, LOW)
- Los objetos tienen asociados niveles de sensibilidad (HIGH, MEDIUM, LOW)
- Propiedad de tranquilidad fuerte: Las etiquetas no pueden ser cambiadas luego de su creación.
- Se utiliza Bell-LaPadula (BLP) para evaluar la autorización de las acciones de los sujetos sobre los objetos.
- Si la instrucción es sintácticamente incorrecta, genera una instrucción BadInstruction.
- Si el sujeto no puede realizar una lectura sobre un objeto, se guardará 0 en su variable TEMP.
- Se utilizará la variable TEMP del sujeto para determinar el último valor leído del mismo.

Instrucciones habilitadas del sistema:
- READ nombre_sujeto nombre_objeto
- WRITE nombre_sujeto nombre_objeto valor

Para las instrucciones con error, se generará la siguiente instrucción especial:
- BadInstruction

## SecureSystemWithCovertChannel

Se actualiza el sistema SecureSystem para que posea un canal encubierto entre el sujeto hal (de nivel de autorización HIGH) a lyle (de nivel de autorización LOW).

Característica del sistema:

Se agregan las siguientes tres instrucciones
- CREATE nombre_sujeto nombre_objeto
- DESTROY nombre_sujeto nombre_objeto
- RUN nombre_sujeto

Instrucción CREATE
- La semántica de CREATE es tal que un nuevo objeto es adicionado al estado con SecurityLevel igual al nivel del sujeto que lo crea. Inicialmente tiene un valor de 0. Si ya existe un objeto con el mismo nombre, en cualquier nivel, la operación no es válida, no será ejecutada

Instrucción DESTROY
- Eliminará el objeto indicado del estado, asumiendo que el objeto existe y que
el sujeto tiene acceso de WRITE sobre el objeto, de acuerdo a la propiedad *
de BLP. Si no, la operación no es válida, no será ejecutada

Instrucción RUN
- El objetivo de RUN es permitirle a lyle hacer cualquier procesamiento que sea necesario para obtener el valor del bit que le envían a hal, agregarlo a un byte que está creando, y si el byte está completo, escribirlo en la salida
- Cada sujeto en la instrucción realizarán acciones diferentes
- Tiene como objetivo tener un canal encubierto entre los sujetos que operan (Para ello, no será posible compartir variables entre los sujetos)
- En la ejecución de RUN, la información se va enviando bit a bit entre los sujetos
- Para garantizar la existencia de un canal encubierto se provee un archivo de log para registrar las instrucciones realizadas por los sujetos
