package dev.yuri;

import dev.yuri.jms.PriceConsumer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/prices")
public class PriceController {

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPriceQueue(String price) {
        var priceConsumer = new PriceConsumer();
        String resp = priceConsumer.consume();

        return Response.ok(resp).build();
    }
}