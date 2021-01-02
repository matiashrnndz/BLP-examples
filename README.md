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

El propósito del InsecureSystem es implementar un sistema de seguridad simple, en Java, siguiendo las reglas de seguridad de Bell-LaPadula (BLP), seguridad simple, la propiedad * y la tranquilidad fuerte.
El objetivo es que sea un sistema simple no seguro.

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


## SecureSystemWithCovertChannel


