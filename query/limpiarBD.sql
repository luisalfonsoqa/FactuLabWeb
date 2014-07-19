--1. Borrar Tablas
drop table cuenta
drop table cuentadetalle
drop table tipousuario
drop table usuario
drop table turno
drop table movdinero
drop table impresora
drop table tipoatencion
drop table especialidad
drop table boletadetalle
drop table atenciondetalle
drop table ticketdetalle
drop table ticket
drop table boleta
drop table mediopago
drop table sequence
drop table facturadetalle
drop table factura
drop table atencion
drop table medico
drop table insti_anali
drop table analisis
drop table paciente
drop table distrito
drop table tipopaciente
drop table provincia
drop table departamento
drop table franquicia
drop table institucion
drop table seccion
drop table tarifa
drop table parametro
--2. Tarea > Retaurar > Base de datos > marca en la segunda pestaña el "Remplazar"
--3. actualizar Parametros
update parametro set valParametro = 'C:\omega\Remoto\' where idParametro like 'OmegaDirSalida' 
update parametro set valParametro = 'C:\omega\Local\' where idParametro like 'OmegaDirSalidaBkup' 
insert impresora (seriemaquina,serieticket, numeroticket, nombre, activo) values ('SERIELOCAL', '6','1','BIXOLON SRP-270,winspool,Ne02:',1)

update usuario set clave = 'lquispe' 
--