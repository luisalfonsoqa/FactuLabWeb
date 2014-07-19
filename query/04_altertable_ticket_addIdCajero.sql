alter table TICKET add idCajero smallint null

ALTER TABLE TICKET 
ADD CONSTRAINT fk_ticket_cajero
FOREIGN KEY (idCajero)
REFERENCES usuario(idUsuario)


alter table TICKET add inmediata smallint null
