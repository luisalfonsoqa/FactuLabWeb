select distinct(nomusuario) from ticket where idcajero is null 
/** Campo idCajero **/
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'lmorocho') where lower(nomusuario) like 'lmorocho'
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'lquispe') where lower(nomusuario) like 'lquispe'
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'nsosa16') where lower(nomusuario) like 'nsosa16'
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'aguerrero') where lower(nomusuario) like 'aguerrero'
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'mneyra') where lower(nomusuario) like 'mneyra'
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'jacosta') where lower(nomusuario) like 'jacosta'
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'nsosa') where lower(nomusuario) like 'nsosa'
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'mfarfan') where lower(nomusuario) like 'mfarfan'
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'rmedina') where lower(nomusuario) like 'rmedina'
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'yescobar') where lower(nomusuario) like 'yescobar'

update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'yescobar') where lower(nomusuario) like 'admin'
update ticket set idcajero = (select idusuario from usuario where LOWER(usuario) like 'yescobar') where lower(nomusuario) like 'jvalencia'
/** Campo inmediata **/
update ticket set inmediata = 0 where idPaciente is null
update ticket set inmediata = 1 where idPaciente is not null