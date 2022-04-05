/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.paams22.Model.Artifacts;

/**
 *
 * @author tospie
 */
public class Product extends Artifact {

    private ProductKind ofProductkind;

    public Product(String id) {
	super(id, "products:", new String[]{"mosaik:Product"});
    }

    public Product(String id, ProductKind ofProductKind) {
	super(id, "products:", new String[]{"mosaik:Product"});
	this.ofProductkind = ofProductkind;
	rdfModel.addAll(builder.subject(getUri())
		.add("mosaik:productKind", ofProductKind.getUri())
		.build());
    }
}
