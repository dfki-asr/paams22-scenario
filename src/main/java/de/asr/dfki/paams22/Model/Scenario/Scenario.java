/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.paams22.Model.Scenario;

import de.asr.dfki.paams22.Model.Artifacts.Blueprint;
import de.asr.dfki.paams22.Model.Artifacts.Order;
import de.asr.dfki.paams22.Model.Artifacts.ProductKind;
import de.asr.dfki.paams22.Model.Artifacts.Workstation;
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

    // Workstations
    private static Workstation cpuDispenser;
    private static Workstation ramDispenser;
    private static Workstation ifxDispenser;
    private static Workstation mainboardAssembly;
    private static Workstation iotboardAssembly;

    public static Container build() {
	rootContainer = new Container("base", new String[]{"ldp:BasicContainer", "mosaik:RootContainer"});
	addProductsContainer();
	addProductKinds();
	addBlueprints();
	addWorkstations();
	addOrders();
	return rootContainer;
    }

    public static void addProductsContainer() {
	Container productsContainer = new Container("products", new String[]{"ldp:BasicContainer", "mosaik:Products"});
	rootContainer.addArtifact(productsContainer);
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
	Container blueprintsContainer = new Container("blueprints", new String[]{"ldp:BasicContainer", "mosaik:Blueprints"});
	blueprintsContainer.addArtifact(new Blueprint("dispenseCpu", cpu, new ProductKind[]{}));
	blueprintsContainer.addArtifact(new Blueprint("dispenceRam", ram, new ProductKind[]{}));
	blueprintsContainer.addArtifact(new Blueprint("dispenseInterface", ifx, new ProductKind[]{}));
	blueprintsContainer.addArtifact(new Blueprint("assembleMainboard", mainboard, new ProductKind[]{cpu, ram}));
	blueprintsContainer.addArtifact(new Blueprint("assembleIOTBoard", iotboard, new ProductKind[]{mainboard, ifx}));
	rootContainer.addArtifact(blueprintsContainer);
    }

    public static void addWorkstations() {
	cpuDispenser = new Workstation("cpudispenser", cpu);
	ramDispenser = new Workstation("ramdispenser", ram);
	ifxDispenser = new Workstation("ifxdispenser", ifx);
	mainboardAssembly = new Workstation("mainboardassembly", mainboard);
	iotboardAssembly = new Workstation("iotboardassembly", iotboard);

	Container workstationContainer = new Container("workstations", new String[]{"ldp:BasicContainer", "mosaik:Workstations"});
	workstationContainer.addArtifact(cpuDispenser);
	workstationContainer.addArtifact(ramDispenser);
	workstationContainer.addArtifact(ifxDispenser);
	workstationContainer.addArtifact(mainboardAssembly);
	workstationContainer.addArtifact(iotboardAssembly);

	rootContainer.addArtifact(workstationContainer);
    }

    public static void addOrders() {
	Container orderContainer = new Container("orders", new String[]{"ldp:BasicContainer", "mosaik:Orders"});
	orderContainer.addArtifact(new Order("order_1", iotboard));
	rootContainer.addArtifact(orderContainer);
    }
}
