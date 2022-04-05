/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.asr.dfki.paams22.Model.Container;

import de.asr.dfki.paams22.Model.RDFObject;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.eclipse.rdf4j.model.util.Values;

/**
 *
 * @author tospie
 * @param <T> Type of Artifact or Container that the container manages
 */
public class Container<T extends RDFObject> extends RDFObject {

    @Getter
    protected Set<T> contains = new HashSet<>();

    public Container(String id, String[] classes) {
	super(id, classes);
    }

    public void addArtifact(T artifact) {
	rdfModel.addAll(builder.subject("base:" + getId())
		.add("ldp:contains", "base:" + artifact.getId()).build());
	contains.add(artifact);
    }

    public RDFObject getRDFOfObject(String id) {
	return contains.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    public <T> T getObject(String id) {
	return (T) contains.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }
}
