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
public class Order extends Artifact {

    private ProductKind ordered;

    public Order(String id, ProductKind ordered) {
	super(id, "orders:", new String[]{"schema:Order"});

	this.ordered = ordered;

	rdfModel.addAll(builder.subject(getUri())
		.add("schema:orderedItem", ordered.getUri())
		.build());
    }

}
