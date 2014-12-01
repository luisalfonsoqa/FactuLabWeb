alter table atenciondetalle add descuento decimal(10,1) null;
update atenciondetalle set detallemonto = null where detalleMonto is not null;