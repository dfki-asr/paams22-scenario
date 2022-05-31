/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.wiait22.Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Triple;
import org.eclipse.rdf4j.model.impl.SimpleTriple;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import static org.eclipse.rdf4j.model.util.Statements.statement;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

/**
 *
 * @author tospie
 */
public abstract class RDFObject {

    @Getter
    protected Model rdfModel;

    protected ModelBuilder builder = new ModelBuilder();

    @Getter
    protected String id;

    @Getter
    @Setter
    protected String uri;

    public RDFObject(String id, String namespace, String[] classes) {
	this.id = id;
	this.rdfModel = new TreeModel();

	rdfModel.setNamespace("base", "http://localhost:8080/");
	rdfModel.setNamespace("productkinds", "http://localhost:8080/productkinds/");
	rdfModel.setNamespace("blueprints", "http://localhost:8080/blueprints/");
	rdfModel.setNamespace("workstations", "http://localhost:8080/workstations/");
	rdfModel.setNamespace("products", "http://localhost:8080/products/");
	rdfModel.setNamespace("orders", "http://localhost:8080/orders/");
	rdfModel.setNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	rdfModel.setNamespace("rdfs", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	rdfModel.setNamespace("dct", "http://purl.org/dc/terms/");
	rdfModel.setNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
	rdfModel.setNamespace("schema", "https://schema.org/");
	rdfModel.setNamespace("ajan", "http://www.ajan.de/ajan-ns#");
	rdfModel.setNamespace("mosaik", "http://dfki.de/mosaik#");
	rdfModel.setNamespace("stigld", "http://dfki.de/stigld#");
	rdfModel.setNamespace("ldp", "http://www.w3.org/ns/ldp#");

	builder.setNamespace("base", "http://localhost:8080/");
	builder.setNamespace("productkinds", "http://localhost:8080/productkinds/");
	builder.setNamespace("blueprints", "http://localhost:8080/blueprints/");
	builder.setNamespace("workstations", "http://localhost:8080/workstations/");
	builder.setNamespace("products", "http://localhost:8080/products/");
	builder.setNamespace("orders", "http://localhost:8080/orders/");
	builder.setNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	builder.setNamespace("rdfs", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
	builder.setNamespace("dct", "http://purl.org/dc/terms/");
	builder.setNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
	builder.setNamespace("schema", "https://schema.org/");
	builder.setNamespace("ajan", "http://www.ajan.de/ajan-ns#");
	builder.setNamespace("mosaik", "http://dfki.de/mosaik#");
	builder.setNamespace("stigld", "http://dfki.de/stigld#");
	builder.setNamespace("ldp", "http://www.w3.org/ns/ldp#");

	uri = namespace + id;
	for (String c : classes) {
	    rdfModel.addAll(builder.subject(uri)
		    .add(RDF.TYPE, c).build());
	}
    }

    public String getRDF() {
	ByteArrayOutputStream output = new ByteArrayOutputStream();
	Rio.write(rdfModel, output, RDFFormat.TURTLE);
	return output.toString();
    }

    public void addRDF(String rdfString) throws IOException {
	rdfModel.addAll(Rio.parse(new ByteArrayInputStream(rdfString.getBytes()), RDFFormat.TURTLE));
    }

}
