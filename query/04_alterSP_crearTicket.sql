USE [FacturaLabSQL]
GO
/****** Object:  StoredProcedure [dbo].[InsertarTicket]    Script Date: 07/13/2014 21:26:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[InsertarTicket]
	@idImpresora smallint,
	@idPaciente int,
	@idInstitucion smallint,
	@ruc varchar(11),
	@nombre varchar(250),
	@direccion varchar(250),
	@subtotal decimal(18,2),
	@igv decimal(18,2),
	@total decimal(18,2),
	@tipopago varchar(5),
	@nomsede varchar(200),
	@nomusuario varchar(200),
	@idFranquicia smallint,
	@impresora varchar(50),
	@idusuario smallint,
	@tipoTicket smallint
AS
BEGIN
	DECLARE @serieTicket SMALLINT
	DECLARE @numeroTicket BIGINT
	SET NOCOUNT ON;
	
    SELECT @serieTicket = i.serieTicket, @numeroTicket = i.numeroTicket 
	from impresora i where i.idImpresora = @idImpresora
	
	INSERT INTO ticket VALUES(@serieTicket, @numeroTicket, GETDATE(), NULLIF(@idPaciente, 0), @idInstitucion, @ruc, @nombre, @direccion, @subtotal, @igv,
	@total, 0, @tipopago, @nomsede, @nomusuario, @idFranquicia, @impresora, @idusuario, @tipoTicket)
	
	UPDATE impresora SET numeroTicket = @numeroTicket + 1 where idImpresora = @idImpresora
	
	SELECT @serieTicket 'serieTicket', @numeroTicket 'numeroTicket', SCOPE_IDENTITY() 'idTicket'
END
