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
public class Objekt_2 extends object_base {

    @Getter
    private double[] position = new double[]{1.4, 2.0};

    @Getter
    private String name = "Hallo Welt Objekt";

    @Override
    protected String[] steps() {
	return new String[]{"glue", "make_coffee"};
    }

    public Objekt_2() {
	super();
	id = "awesomeObject2";
	rdfModel.addAll(builder.subject("base:" + id)
		.add("ex:name", name)
		.add("ex:pos_x", position[0])
		.add("ex:pos_y", position[1])
		.add("ex:lastModified", LocalTime.now())
		.build());	
	addSteps();
    }

}
