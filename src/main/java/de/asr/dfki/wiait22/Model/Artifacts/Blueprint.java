/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.wiait22.Model.Artifacts;

/**
 *
 * @author tospie
 */
public class Blueprint extends Artifact {

    private final ProductKind about;
    private final ProductKind[] supply;

    public Blueprint(String id, ProductKind about, ProductKind[] supply) {
	super(id, "blueprints:", new String[]{"mosaik:Blueprint"});

	this.about = about;
	this.supply = supply;

	rdfModel.addAll(builder.subject(getUri())
		.add("schema:about", about.getUri())
		.build());

	for (ProductKind s : supply) {
	    rdfModel.addAll(builder.subject(getUri())
		    .add("schema:supply", s.getUri())
		    .build());
	}
    }
}
