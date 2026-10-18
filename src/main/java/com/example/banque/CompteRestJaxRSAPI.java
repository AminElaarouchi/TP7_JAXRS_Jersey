package com.example.banque;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@Path("/banque")
public class CompteRestJaxRSAPI {
    private final CompteRepository repository;

    public CompteRestJaxRSAPI(CompteRepository repository) { this.repository = repository; }

    @GET @Path("/comptes") @Produces(MediaType.APPLICATION_JSON)
    public List<Compte> getComptesJson() { return repository.findAll(); }

    @GET @Path("/comptes") @Produces(MediaType.APPLICATION_XML)
    public CompteList getComptesXml() { return new CompteList(repository.findAll()); }

    @GET @Path("/comptes/{id}") @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCompte(@PathParam("id") long id) {
        return repository.findById(id).map(c -> Response.ok(c).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST @Path("/comptes")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addCompte(Compte compte) {
        compte.setId(null);
        return Response.status(Response.Status.CREATED).entity(repository.save(compte)).build();
    }

    @PUT @Path("/comptes/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateCompte(@PathParam("id") long id, Compte compte) {
        return repository.findById(id).map(existing -> {
            existing.setSolde(compte.getSolde());
            existing.setDateCreation(compte.getDateCreation());
            existing.setType(compte.getType());
            return Response.ok(repository.save(existing)).build();
        }).orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE @Path("/comptes/{id}")
    public Response deleteCompte(@PathParam("id") long id) {
        if (!repository.existsById(id)) return Response.status(Response.Status.NOT_FOUND).build();
        repository.deleteById(id);
        return Response.noContent().build();
    }
}
