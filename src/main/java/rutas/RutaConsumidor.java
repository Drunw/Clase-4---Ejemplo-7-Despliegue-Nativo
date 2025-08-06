package rutas;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;


@RegisterForReflection
@ApplicationScoped
public class RutaConsumidor extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration()
                .bindingMode(RestBindingMode.off);

        rest("/ejemploNativo/{nombre}")
                .get()
                .to("direct:rutaInicial");

    }
}
