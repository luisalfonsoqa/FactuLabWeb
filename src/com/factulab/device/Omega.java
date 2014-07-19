package com.factulab.device;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.factulab.dao.bean.Paciente;
import com.factulab.dao.exception.FactulabException;
import com.factulab.dao.form.AnalisisForm;
import com.factulab.dao.form.AtencionForm;

public class Omega {
	Logger miLog = Logger.getLogger(Omega.class);
	

//    public static void main(String[] args) {
//        try {
//            new Omega().generaArchivoDesdeAtencion(860, "1220012");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//	private String obtenerCarpetaRemota{}{
//		Properties prop = new Properties();
//		OutputStream output = null;
//		try {
//			output = new FileOutputStream("config.properties");
//	 
//			// set the properties value
//			prop.setProperty("database", "localhost");
//			prop.setProperty("dbuser", "mkyong");
//			prop.setProperty("dbpassword", "password");
//	 
//			// save properties to project root folder
//			prop.store(output, null);
//	 
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (output != null) {
//				try {
//					output.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//	 
//		}
//	  }
//	}
	
	/**
	 * 
	 * @param atencionForm
	 * @throws Exception
	 */
	public void generaArchivoDesdeAtencion(AtencionForm atencionForm, String dirLocal, String dirRemoto) throws Exception {
            //obetenemos el detalle de la atencion para enviarla al omega.
            //cambiariamos esto por buscar las facutasdetalle las cuales contienen las atenciones.
            /**
             * ARCHIVO REMOTO
             */
			boolean correcto = false;
		  	try {
		  		grabarArchivoOmega(atencionForm, dirRemoto);
		  		correcto = true;
          	} catch(Exception e){
	          	miLog.error(e.getMessage()+" Error al grabar el archivo en el DirectorioRemoto["+dirRemoto+"]",e);
          	} finally {
          		try {
    		  		grabarArchivoOmega(atencionForm, dirLocal);
    		  		correcto = true;
              	} catch(Exception e){
    	          	miLog.error(e.getMessage()+" Error al grabar el archivo en el DirectorioLocal["+dirLocal+"]",e);
              	}
          	}
			if(!correcto){
				throw new FactulabException("Error al grabar en DirectorioRemoto["+dirRemoto+"] DiretorioLocal["+dirLocal+"].");
			}
    }
	
	private void grabarArchivoOmega(AtencionForm atencionForm, String carpeta) throws Exception {
        String nombre_archivo = carpeta + atencionForm.getCodigoOmega() + ".pet";
        //miLog.info("Se creará el archivo local: "+nombre_archivo);
        PrintWriter archivo_out = null;
        File archivo = new File(nombre_archivo);
        archivo_out = new PrintWriter(new FileWriter(archivo));
        creaCabecera(archivo_out);
        creaPaciente(archivo_out, atencionForm.getCodigoOmega(), atencionForm.getPaciente(), atencionForm.getMedico().getIdMedico(), atencionForm.getInstitucion().getIdInstitucion());
        creaDetalles(archivo_out, atencionForm.getCodigoOmega(), atencionForm.getlAnalisis());
        creaCierre(archivo_out);
        archivo_out.close();
}


    private void creaCabecera(PrintWriter archivoOut) {
        Calendar hoy = Calendar.getInstance();
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        String fecha = sdf.format(hoy.getTime());
        archivoOut.println("H|\\^&|||OMEGA|||||HOST||P||" + fecha);
    }

    private void creaCierre(PrintWriter archivoOut) {
        archivoOut.println("L|1|N");
    }

    private void creaPaciente(PrintWriter archivoOut, String numPeticion, Paciente paciente, Integer idMedico, Integer idInstitucion) {
        StringBuilder cad = new StringBuilder("P|");
        cad.append("1").append("|");  // Num. Orden
        cad.append(numPeticion).append("|"); // Num. Petición
        cad.append(paciente.getIdPaciente().toString()).append("|"); // Id unico paciente
        //cad.append("").append("^").append(paciente.getDni()).append("|"); // Num SS y Num DNI
        cad.append("^|");
        cad.append(paciente.getApepat()).append(" ").append(paciente.getApemat()).append("^^").append(paciente.getNombre()).append("||"); // Apellidos y Nombres

        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String fecha = "";
        if (paciente.getFecnac() != null) {
            fecha = sdf.format(paciente.getFecnac().getTime());
        }
        cad.append(fecha).append("|"); // Fecha Nacimiento
        cad.append(paciente.getSexo()).append("|"); // Sexo
        /*
        cad.append(paciente.getDireccion()).append("^").
        append(paciente.getDistrito().getDistrito()).append("^").
        append(paciente.getDistrito().getProvincia().getIdProvincia()).append("|"); // Dirección
        cad.append(paciente.getTelefono()).append("|"); // Telefono */
//         System.out.println("Paciente: "+paciente.getIdPaciente());
        cad.append("|^^^|||").append(idMedico); //Id Paciente
        cad.append("|^|||||||||");
        /*cad.append("|"); // Doctor
        cad.append("^|"); // Grupo de facturación
        cad.append("|"); // Diagnostico
        cad.append("|"); // Número de Episodio o Icu
        cad.append("|"); // Observación */

        Calendar hoy = Calendar.getInstance();
        DATE_FORMAT = "yyyyMMddHHmmss";
        sdf = new SimpleDateFormat(DATE_FORMAT);
        fecha = sdf.format(hoy.getTime());

        cad.append(fecha).append("^").append(fecha).append("|"); // Fechas Registro/extracción
        cad.append("R"); // prioridad
        /*cad.append("|"); // Cama
        cad.append("^|"); // Codigo Tipo y Motivo
        cad.append("|"); // Codigo de Servicio
        cad.append("|"); // Codigo Origen y Destino
        cad.append("|"); // Grupo Fisiológico/edad fisiológica
        cad.append("|"); // Numero copias informe*/
//        System.out.println("Institucion: "+paciente.getInstitucion().getIdInstitucion());
        cad.append("|||^||||||").append(idInstitucion); //Institucion
        cad.append("^|^||||||||");

        archivoOut.println(cad.toString());
    }

    private void creaDetalles(PrintWriter archivoOut, String numPeticion, List<AnalisisForm> lAnalisis) {
        int conta = 1;
        for (AnalisisForm ana : lAnalisis) {
        	if (ana.getIdAnalisisOmega() != null && !ana.getIdAnalisisOmega().equals("")
                    && !ana.getIdAnalisisOmega().equals("0")) {
                StringBuilder cad = new StringBuilder("O|");
                cad.append(conta).append("|"); // Num Orden
                cad.append(numPeticion).append("||"); // Num. Petición

                cad.append("^^^").append(ana.getIdAnalisisOmega()).append("^^^^");
                if (ana.getSeccion() == 6) {
                    cad.append("M");
                } else {
                    cad.append("B");
                }
                cad.append("^^|||||||||||");
                cad.append("1").append("|||"); // Acción
                cad.append("1").append("|||||||");
                cad.append("F"); // Tipo

                archivoOut.println(cad.toString());
                conta++;
            }
		}
    }
}
