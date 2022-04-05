/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.paams22.Model.Scenario;

import de.asr.dfki.paams22.Model.Artifacts.ProductKind;
import de.asr.dfki.paams22.Model.Container.Container;

/**
 *
 * @author tospie
 */
public class Scenario {

    private static Container rootContainer;

    public static Container build() {
	rootContainer = new Container("root", new String[]{"ldp:BasicContainer", "mosaik:RootContainer"});
	addProductKinds();
	return rootContainer;
    }

    public static void addProductKinds() {
	Container productKindsContainer = new Container("productkinds", new String[]{"ldp:BasicContainer", "mosaik:ProductKinds"});
	productKindsContainer.addArtifact(new ProductKind("mainboard"));
	productKindsContainer.addArtifact(new ProductKind("iotboard"));
	productKindsContainer.addArtifact(new ProductKind("cpu"));
	productKindsContainer.addArtifact(new ProductKind("rammodule"));
	rootContainer.addArtifact(productKindsContainer);

	
    }
}
