/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.wiait22.Model.Artifacts;

import lombok.Getter;
import org.eclipse.rdf4j.model.util.Values;

/**
 *
 * @author tospie
 */
public class Workstation extends Artifact {

    @Getter
    private ProductKind yields;

    @Getter
    private String interactionEndpoint;

    public Workstation(String id, ProductKind yields) {
	super(id, "workstations:", new String[]{"mosaik:Workstation"});

	this.yields = yields;
	interactionEndpoint = "http://localhost:8080/workstations/" + id + "/produce/";

	rdfModel.addAll(builder.subject(getUri())
		.add("schema:yields", yields.getUri())
		.add("mosaik:endpoint", Values.iri(interactionEndpoint))
		.build());
    }

}
