package com.factulab.service;

import org.apache.log4j.Logger;

import com.factulab.dao.UsuarioDAO;
import com.factulab.dao.bean.Impresora;
import com.factulab.dao.bean.Usuario;
import com.factulab.dao.exception.DAOException;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.util.DAOConstante;

public class UsuarioService {
	Logger miLog = Logger.getLogger(UsuarioService.class);
	
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	public Usuario obtenerUsuario(String txtUsuario, String txtClave) throws FactulabException, DAOException{
		Usuario usuario = usuarioDAO.validarUsuario(txtUsuario, txtClave);
		usuario.setImprimeTickets(usuario.getIdTipoUsuario() == DAOConstante.BD_TIPO_USUARIO_CAJA);
		return usuario;
	}
	
	public void obtenerImpresora(Usuario usuario, String txtDevice) throws FactulabException, DAOException{
		if(usuario.getIdTipoUsuario() == DAOConstante.BD_TIPO_USUARIO_CAJA){
			Impresora impresora = usuarioDAO.obtenerImpresoraPorNombre(txtDevice);
			usuario.setNombreImpresora(impresora.getNombre());
			usuario.setSerieImpresora(impresora.getSerieMaquina());
			usuario.setIdImpresora(impresora.getIdImpresora());
		}
	}
}
