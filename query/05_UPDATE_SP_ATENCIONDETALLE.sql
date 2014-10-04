USE [FacturaLabSQL]
GO
/****** Object:  StoredProcedure [dbo].[InsertarAtencionDetalle]    Script Date: 09/27/2014 21:38:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[InsertarAtencionDetalle]
	@idAtencion int,
	@idAnalisis smallint,
	@cantidad smallint,
	@totalConDescu decimal(10,2),
	@precioUniSinDesc decimal(10,2),
	@precioUni decimal(10,2),
	@detalleMonto nvarchar(250)
AS
BEGIN
	--SET NOCOUNT ON;	
	insert into FacturaLabSQL.dbo.atenciondetalle 
	values(@idAtencion, @idAnalisis, @cantidad, @totalConDescu, @precioUniSinDesc, @precioUni, @detalleMonto)	
END
