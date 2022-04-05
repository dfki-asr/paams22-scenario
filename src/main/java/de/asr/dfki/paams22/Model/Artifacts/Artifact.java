/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.paams22.Model.Artifacts;

import de.asr.dfki.paams22.Model.RDFObject;

/**
 *
 * @author tospie
 */
public abstract class Artifact extends RDFObject {

    public Artifact(String id, String namespace, String[] classes) {
	super(id, namespace, classes);
    }
}
