/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.wiait22.Model;

import java.io.ByteArrayOutputStream;
import lombok.Getter;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;

/**
 *
 * @author tospie
 */
public abstract class object_base {

    protected String exampleNamespace = "http://dfki.asr.de/MOSAIK/example#";

    protected ModelBuilder builder = new ModelBuilder();

    @Getter
    protected String id;

    protected abstract String[] steps();

    @Getter
    protected Model rdfModel;

    public object_base() {
	this.rdfModel = new TreeModel();

	rdfModel.setNamespace("base", "http://localhost:8080/");
	rdfModel.setNamespace("ex", exampleNamespace);
	// Namespace, in dem generelle RDF Konzepte beschrieben sind
	rdfModel.setNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	// Namespace, in dem Zusammenhänge im RDF Datenmodell beschrieben sind (RDF Klassen usw)
	rdfModel.setNamespace("rdfs", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	// Namespace, der Konzepte wie Identifier und dergleichen beschreibt
	rdfModel.setNamespace("dct", "http://purl.org/dc/terms/");
	// Namespace, in dem xsd Datentypen beschrieben sind
	rdfModel.setNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
	// Namespace, in dem so ziemlich alles irgendwie beschrieben ist
	rdfModel.setNamespace("schema", "https://schema.org/");
	// Selbsterfundener Namespace, in dem die Fertigungsschritte beschrieben sein könnten
	rdfModel.setNamespace("steps", "http://dfki.asr.de/MOSAIK/example#steps");

    }

    public String getRDF() {	
	ByteArrayOutputStream output = new ByteArrayOutputStream();
	Rio.write(rdfModel, output, RDFFormat.TURTLE);
	return output.toString();
    }

    protected void addSteps() {
	for (String step : steps()) {
	    rdfModel.addAll(builder.subject("base:" + getId()).add("schema:provides", "steps:" + step).build());
	}
    }
}
