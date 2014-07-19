USE [FacturaLabSQL]
GO
/****** Object:  StoredProcedure [dbo].[InsertarInstitucion]    Script Date: 07/05/2014 20:49:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[InsertarInstitucion]		
	@idTarifa smallint,
	@nombre nvarchar(250),
	@direccion nvarchar(250),
	@idFacMensual smallint,
	@ruc varchar(20),
	@emailcontacto varchar(200),
	@nombrecontacto varchar(200)
AS
BEGIN
	SET NOCOUNT ON;
	
	INSERT INTO institucion (tarifa, nombre, direccion, facMensual, ruc, emailcontacto, nombrecontacto) 
		values(@idTarifa, UPPER(@nombre),@direccion,@idFacMensual, 
		@ruc,@emailcontacto,@nombrecontacto);			
	SELECT SCOPE_IDENTITY() 'idInstitucion'
END
