/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.paams22.Model.Scenario;

import de.asr.dfki.paams22.Model.Artifacts.Blueprint;
import de.asr.dfki.paams22.Model.Artifacts.ProductKind;
import de.asr.dfki.paams22.Model.Container.Container;

/**
 *
 * @author tospie
 */
public class Scenario {

    private static Container rootContainer;

    // ProductKinds
    private static ProductKind mainboard;
    private static ProductKind iotboard;
    private static ProductKind cpu;
    private static ProductKind ram;
    private static ProductKind ifx;

    public static Container build() {
	rootContainer = new Container("base", new String[]{"ldp:BasicContainer", "mosaik:RootContainer"});
	addProductKinds();
	addBlueprints();
	return rootContainer;
    }

    public static void addProductKinds() {
	Container productKindsContainer = new Container("productkinds", new String[]{"ldp:BasicContainer", "mosaik:ProductKinds"});
	mainboard = new ProductKind("mainboard");
	iotboard = new ProductKind("iotboard");
	cpu = new ProductKind("cpu");
	ram = new ProductKind("rammodule");
	ifx = new ProductKind("interface");
	productKindsContainer.addArtifact(iotboard);
	productKindsContainer.addArtifact(ifx);
	productKindsContainer.addArtifact(mainboard);
	productKindsContainer.addArtifact(cpu);
	productKindsContainer.addArtifact(ram);
	rootContainer.addArtifact(productKindsContainer);
    }

    public static void addBlueprints() {
	Container blueprintsContainer = new Container("blueprints", new String[]{"ldp:BasicContainer", "mosaik:ProductKinds"});
	blueprintsContainer.addArtifact(new Blueprint("dispenseCpu", cpu, new ProductKind[]{}));
	blueprintsContainer.addArtifact(new Blueprint("dispenceRam", ram, new ProductKind[]{}));
	blueprintsContainer.addArtifact(new Blueprint("dispenseInterface", ifx, new ProductKind[]{}));
	blueprintsContainer.addArtifact(new Blueprint("assembleMainboard", mainboard, new ProductKind[]{cpu, ram}));
	blueprintsContainer.addArtifact(new Blueprint("assembleIOTBoard", iotboard, new ProductKind[]{mainboard, ifx}));
	rootContainer.addArtifact(blueprintsContainer);
    }
}
