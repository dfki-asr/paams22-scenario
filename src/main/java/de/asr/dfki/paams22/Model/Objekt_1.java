/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.paams22.Model;

import java.time.LocalTime;
import lombok.Getter;

/**
 *
 * @author tospie
 */
public class Objekt_1 extends object_base {

    @Getter
    private int numeric = 42;

    @Getter
    private String name = "Hallo Welt Objekt";

    @Override
    protected String[] steps() {
	return new String[]{"solder", "fix"};
    }

    public Objekt_1() {
	super();
	id = "amazingObject1";
	rdfModel.addAll(builder.subject("base:" + id)
		.add("ex:name", name)
		.add("ex:value", numeric)
		.add("dct:lastModified", LocalTime.now())
		.build());
	addSteps();
    }

}
