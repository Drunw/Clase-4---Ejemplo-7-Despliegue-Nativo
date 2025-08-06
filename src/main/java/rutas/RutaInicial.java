package rutas;

import config.LoggerAuditoria;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.saga.CamelSagaService;
import org.apache.camel.saga.InMemorySagaService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.UUID;


@RegisterForReflection
@ApplicationScoped
public class RutaInicial extends RouteBuilder {
    @Inject
    LoggerAuditoria loggerAuditoria;

    @Override
    public void configure() throws Exception {

        from("direct:rutaInicial")
                .process(exchange -> {
                    exchange.removeProperty(LoggerAuditoria.PROPIEDAD_TRANSACCION);
                    exchange.setProperty(LoggerAuditoria.PROPIEDAD_TRANSACCION, UUID.randomUUID());
                    String mensaje = exchange.getIn().getHeader("nombre").toString();
                    String mensajeMayuscula = mensaje.toUpperCase();
                    exchange.getIn().setBody(mensajeMayuscula);
                }) // Convierte el mensaje a mayúsculas
                .setProperty(LoggerAuditoria.PROPIEDAD_MENSAJE,simple("Body Transformado: ${body}"))
                .bean(loggerAuditoria,LoggerAuditoria.LOG_SIMPLE)
                .setBody(simple("Hola!, ${body}"))
                .end();


    }
}
