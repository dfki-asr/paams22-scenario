/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.paams22.Application;

import de.asr.dfki.paams22.Model.Artifacts.Order;
import de.asr.dfki.paams22.Model.Container.Container;
import de.asr.dfki.paams22.Model.RDFObject;
import de.asr.dfki.paams22.Model.Scenario.Scenario;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    String getRootContainer() {
	return rootContainer.getRDF();
    }

    @GetMapping(value = "{containerId}", produces = "text/turtle")
    String getArtifactContainer(HttpServletRequest request,
	    HttpServletResponse response,
	    @PathVariable("containerId") final String containerId) {

	try {
	    response.setStatus(200);
	    var object = rootContainer.getRDFOfObject(containerId);
	    return object.getRDF();
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
	    return object.getRDF();
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
	} catch (Exception e) {
	    response.setStatus(500);
	    return "Something went wrong with processing RDF for order " + orderId;
	}
	response.setStatus(200);
	return ("Updated");
    }
}
