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
public class ProductKind extends Artifact {

    public ProductKind(String id) {
	super(id, "productkinds:", new String[]{"mosaik:ProductKind"});
    }
}
