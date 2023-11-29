package it.gaetano.service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import jakarta.enterprise.context.ApplicationScoped;

import it.gaetano.domain.Dipendente;

import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class DipendenteService {
    private final ConcurrentMap<String, Dipendente> dipendenti = new ConcurrentHashMap<>();

    public DipendenteService() {
    }

    public Collection<Dipendente> getDipendenti() {
        return this.dipendenti.values();
    }

    public Uni<Dipendente> getDipendente(String codiceFiscale) {
        return Uni.createFrom().item(this.dipendenti.get(codiceFiscale)); 
    }

    public Collection<Dipendente> addDipendente(Dipendente dipendente) {
        this.dipendenti.put(dipendente.getCodiceFiscale(), dipendente);
        return this.dipendenti.values();
    }

    public Uni<Dipendente> deleteDipendente(String codiceFiscale) {
        return Uni.createFrom().item(this.dipendenti.remove(codiceFiscale));
    }

}