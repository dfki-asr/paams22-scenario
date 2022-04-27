/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.paams22.Application;

import de.asr.dfki.paams22.Model.Artifacts.Order;
import de.asr.dfki.paams22.Model.Artifacts.Product;
import de.asr.dfki.paams22.Model.Artifacts.Workstation;
import de.asr.dfki.paams22.Model.Container.Container;
import de.asr.dfki.paams22.Model.RDFObject;
import de.asr.dfki.paams22.Model.Scenario.Scenario;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tospie
 */
@RestController
public class ApplicationController {

    private final Container rootContainer;

    public ApplicationController() {
	rootContainer = Scenario.build();
    }

    @GetMapping(value = "/", produces = "text/turtle")
    String getRootContainer(HttpServletRequest request) {
	return rootContainer.getRDF().replace("http://localhost:8080/", getCalledServer(request));
    }

    @GetMapping(value = "{containerId}", produces = "text/turtle")
    String getArtifactContainer(HttpServletRequest request,
	    HttpServletResponse response,
	    @PathVariable("containerId") final String containerId) {

	try {
	    response.setStatus(200);
	    var object = rootContainer.getRDFOfObject(containerId);
	    return object.getRDF().replace("http://localhost:8080/", getCalledServer(request));
	} catch (Exception e) {
	    response.setStatus(404);
	    return "Container object with id " + containerId + " not found .";
	}
    }

    @GetMapping(value = "{containerId}/{objectId}", produces = "text/turtle")
    String getArtifact(HttpServletRequest request,
	    HttpServletResponse response,
	    @PathVariable("containerId") final String containerId,
	    @PathVariable("objectId") final String objectId) {

	try {
	    response.setStatus(200);
	    Container container = (Container) rootContainer.getObject(containerId);
	    RDFObject object = container.getRDFOfObject(objectId);

	    return object.getRDF().replace("http://localhost:8080/", getCalledServer(request));
	} catch (Exception e) {
	    response.setStatus(404);
	    return "Container object with id " + containerId + " not found , or Object with id " + objectId + " not found in container.";
	}
    }

    @RequestMapping(value = "orders/{orderId}", consumes = "text/turtle", method = RequestMethod.PUT)
    String updateOrder(HttpServletRequest request,
	    HttpServletResponse response,
	    @PathVariable("orderId") final String orderId,
	    @RequestBody String receivedRdf) throws IOException {

	Container orderContainer;
	Order order;
	try {
	    orderContainer = (Container) rootContainer.getObject("orders");
	    order = (Order) orderContainer.getObject(orderId);
	} catch (Exception e) {
	    response.setStatus(404);
	    return "Container object with id orders not found , or order with id " + orderId + " not found in container.";
	}

	try {
	    order.addRDF(receivedRdf);
	} catch (IOException e) {
	    response.setStatus(500);
	    return "Something went wrong with processing RDF for order " + orderId + ": " + e.getMessage();
	}
	response.setStatus(200);
	return ("");
    }

    @PostMapping(value = "/products", consumes = "text/turtle")
    String postProduct(HttpServletRequest request,
	    HttpServletResponse response,
	    @RequestBody String receivedRdf) {

	Container productContainer;
	try {
	    productContainer = (Container) rootContainer.getObject("products");
	} catch (Exception e) {
	    response.setStatus(404);
	    return "Container object with id products not found.";
	}

	try {
	    Product product = new Product(java.util.UUID.randomUUID().toString());
	    product.addRDF(receivedRdf);
	    productContainer.addArtifact(product);
	    response.setStatus(201);
	    return "Created: " + product.getUri();
	} catch (IOException e) {
	    response.setStatus(500);
	    return "Something went wrong with creating a new product: " + e.getMessage();
	}
    }

    @GetMapping("/workstations/{workstationId}/produce")
    String produce(HttpServletResponse response,
	    @PathVariable("workstationId") final String workstationId) {

	Workstation workstation;
	Container wsContainer;
	try {
	    wsContainer = (Container) rootContainer.getObject("workstations");
	    workstation = (Workstation) wsContainer.getObject(workstationId);
	    if (workstation == null) {
		throw new Exception();
	    }
	} catch (Exception e) {
	    response.setStatus(404);
	    return "Container object with id workstations not found , or workstation with id " + workstationId + " not found in container.";
	}
	try {
	    Container productContainer;
	    productContainer = (Container) rootContainer.getObject("products");
	    Product producedProduct = new Product(java.util.UUID.randomUUID().toString(), workstation.getYields());
	    productContainer.addArtifact(producedProduct);
	    response.setStatus(200);
	    //return "Ok";
	    return "";
	} catch (Exception e) {
	    return "Something went wrong with producing a product on workstation " + workstation.getId() + ": " + e.getMessage();
	}
    }

    private String getCalledServer(HttpServletRequest request) {
	String calledServer = "http://" + request.getServerName() + ":" + request.getServerPort() + "/";
	return calledServer;
    }
}
