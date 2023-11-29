package it.gaetano.rest;

import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import it.gaetano.domain.Dipendente;
import it.gaetano.service.DipendenteService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.smallrye.mutiny.Uni;


@Path("/dipendenti")
@Tag(name = "Gestione Dipendenti", description = "Gestione Dipendenti")
public class DipendentiResource {

	private final DipendenteService dipendenteService;

	public DipendentiResource(DipendenteService dipendenteService) {
		this.dipendenteService = dipendenteService;
	}

	@GET
	@Path("/{codiceFiscale}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Leggi il dipendente", description = "Leggi il dipendente")
	@APIResponse(responseCode = "200", description = "Leggi i dati del dipendente richiesto", content = @Content(schema = @Schema(implementation = Dipendente.class)))
	@APIResponse(responseCode = "404", description = "Dipendente richiesto non trovato")
	public Uni<Response> getDipendente(@Parameter(required = true, description = "Codice fiscale dipendente") @PathParam("codiceFiscale") String codiceFiscale) {
		
		return this.dipendenteService.getDipendente(codiceFiscale)
			.onItem().ifNotNull().transform(dipendente -> Response.ok(dipendente).build())
			.onItem().ifNull().continueWith(Response.status(Status.NOT_FOUND).build());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Aggiungi un dipendente", description = "Aggiungi un dipendente")
	@APIResponse(responseCode = "200", description = "Nuovo dipendente aggiunto")
	public Collection<Dipendente> addDipendente(@Parameter(required = true, description = "Dati dipendente") @NotNull @Valid Dipendente dipendente) {
		return this.dipendenteService.addDipendente(dipendente);
	}

	@DELETE
	@Path("/{codiceFiscale}")
	@Operation(summary = "Cancella un dipendente", description = "Cancella un dipendente")
	@APIResponse(responseCode = "204", description = "Dipendente richiesto cancellato")
	@APIResponse(responseCode = "404", description = "Dipendente da cancellare richiesto non trovato")
	public Uni<Response> deleteDipendente(@Parameter(required = true, description = "Codice fiscale dipendente") @PathParam("codiceFiscale") String codiceFiscale) {
		return this.dipendenteService.deleteDipendente(codiceFiscale)
			.onItem().ifNotNull().transform(dipendente -> Response.ok(dipendente).build())
			.onItem().ifNull().continueWith(Response.status(Status.NOT_FOUND).build());
	
	}

}