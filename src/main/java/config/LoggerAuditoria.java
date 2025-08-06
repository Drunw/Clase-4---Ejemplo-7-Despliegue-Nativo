package config;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RegisterForReflection
@ApplicationScoped
public class LoggerAuditoria {

    /**
     * NOMBRES METODOS CREADOS
     */

    public static final String LOG_PETICION_ENTRADA = "imprimirLogEntradaAPI(${exchangeProperty.idTransaccion},${routeId},${body})";
    public static final String LOG_PETICION_SALIDA = "imprimirLogSalidaAPI(${exchangeProperty.idTransaccion},${routeId},${body})";
    public static final String LOG_PETICION_SALIDA_ERROR =  "imprimirLogSalidaAPI(${exchangeProperty.idTransaccion},${routeId})";
    public static final String LOG_ERROR = "imprimirLogError(${exchangeProperty.idTransaccion},${routeId},${exchangeProperty.message})";
    public static final String LOG_SIMPLE = "imprimirLogSimple(${exchangeProperty.idTransaccion},${routeId},${exchangeProperty.message})";


    /**
     * NOMBRES PROPIEDADES NECESARIAS PARA AUDITORIA
     */
    public static final String PROPIEDAD_TRANSACCION = "idTransaccion";
    public static final String PROPIEDAD_TRANSACCION_HIJO = "idTransaccionHijo";
    public static final String PROPIEDAD_ENDPOINT= "endpointConsumo";
    public static final String PROPIEDAD_PROCEDIMIENTO = "procedimiento";
    public static final String PROPIEDAD_MENSAJE = "message";


    private static final Logger logger = LoggerFactory.getLogger(LoggerAuditoria.class);

    /**
     * Metodo para escribir log de entrada de API
     * @param idTransaccion
     * @param idRuta
     * @param body
     */
    public void imprimirLogEntradaAPI(String idTransaccion, String idRuta, String body) {
        String mensaje = "Peticion de entrada al API ";
        String log = String.format("{\"route\":\"%S\", \"traceId\":\"%S\", \"message\":\"%s\",\"entrada\":\"%S\"}",
                idRuta, idTransaccion, mensaje, body);
        logger.info(log);
    }

    /**
     * Metodo para escribir Log de salida del api
     * @param idTransaccion
     * @param idRuta
     * @param body
     */
    public void imprimirLogSalidaAPI(String idTransaccion, String idRuta, String body) {
        String mensaje = "Body de salida del API  ";
        String log = String.format("{\"route\":\"%S\", \"traceId\":\"%S\", \"message\":\"%s\" ,\"salida\":\"%S\"}",
                idRuta, idTransaccion, mensaje, body);
        logger.info(log);
    }


    /**
     * Metodo para imprimir log de error
     * @param idTransaccion
     * @param idRuta
     * @param mensaje
     */
    public void imprimirLogError(String idTransaccion, String idRuta, String mensaje) {

        String log = String.format("{\"route\":\"%S\", \"traceId\":\"%S\", \"error\":\"%s\"}",
                idRuta, idTransaccion, mensaje);
        logger.error(log);
    }

    /**
     * Metodo para imprimir log de informacion simple
     * @param idTransaccion
     * @param idRuta
     * @param mensaje
     */
    public void imprimirLogSimple(String idTransaccion, String idRuta, String mensaje) {

        String log = String.format("{\"route\":\"%S\", \"traceId\":\"%S\", \"mensaje\":\"%s\"}",
                idRuta, idTransaccion, mensaje);
        logger.info(log);
    }

}

