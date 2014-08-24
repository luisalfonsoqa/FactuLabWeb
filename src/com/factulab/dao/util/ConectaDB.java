package com.factulab.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConectaDB {

	public Connection getConexion() throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
				.newInstance();

		String url = "jdbc:sqlserver://WinXPFactulab:1433;databaseName=FacturaLabSQL"; // DESARROLLO
		// String url = "jdbc:sqlserver://SERVIDOR:1433;databaseName=FacturaLabSQL"; //PRODUCCION

		Connection cn = DriverManager.getConnection(url, "UserfactuLabSQL", "Factu144User");
		return cn;
	}
}