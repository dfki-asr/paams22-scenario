/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.paams22.Application;

import de.asr.dfki.paams22.Model.Container.Container;
import de.asr.dfki.paams22.Model.RDFObject;
import de.asr.dfki.paams22.Model.Scenario.Scenario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	    return "Container object with id " + containerId + " not found .";
	}
    }
}
