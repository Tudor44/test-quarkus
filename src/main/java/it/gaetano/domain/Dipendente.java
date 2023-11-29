package it.gaetano.domain;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Dipendente {

	@NotBlank
    private String codiceFiscale;	
    @NotBlank
    private String nome;
    @NotBlank
    private String cognome;

    private LocalDate dataDiNascita;
    private LocalDate dataAssunzione;

}