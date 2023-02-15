package dev.yuri;

import dev.yuri.jms.PriceSender;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/prices")
public class PriceController {

    @POST
    @Path("/{price}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendPrice(String price) {
        PriceSender priceSender = new PriceSender();
        priceSender.send(price);

        return Response.ok(price + " was sent.").build();
    }
}