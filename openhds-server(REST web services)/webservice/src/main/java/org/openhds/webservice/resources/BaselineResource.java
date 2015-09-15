package org.openhds.webservice.resources;

import java.io.Serializable;
import java.sql.SQLException;
import org.openhds.controller.exception.ConstraintViolations;
import org.openhds.controller.service.BaselineService;
import org.openhds.domain.model.Individual;
import org.openhds.webservice.FieldBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Rest call for the Baseline service 
 *
 * @Smita
 *
 */
@Controller
@RequestMapping("/baseline")
public class BaselineResource extends AbstractResource<Individual> 
{

	private static final Logger logger = LoggerFactory.getLogger(BaselineResource.class);

	private BaselineService baselineservice;
	private FieldBuilder fieldBuilder;


	@Autowired
	public BaselineResource(BaselineService baselineservice, FieldBuilder fieldBuilder) {
		this.baselineservice = baselineservice;
		this.fieldBuilder = fieldBuilder;
	}

	// Method to handle create Individual
	@RequestMapping(method = RequestMethod.POST, produces = "application/xml")
	public ResponseEntity<? extends Serializable> insertIndividual(@RequestBody Individual item) 
	{
		ResponseEntity<? extends Serializable> response = createResource(item);
		return response;
	}

	@Override
	protected Individual copy(Individual item) {
		// TODO Auto-generated method stub

		Individual copy = new Individual();

		copy.setDob(item.getDob());
		copy.setExtId(item.getExtId());
        copy.setCollectedBy(item.getCollectedBy());
		copy.setFather(copyExtId(item.getFather()));
		copy.setFirstName(item.getFirstName());
		copy.setGender(item.getGender());
		copy.setLastName(item.getLastName());
		String middleName = item.getMiddleName() == null ? "" : item.getMiddleName();
		copy.setMiddleName(middleName);
		copy.setMother(copyExtId(item.getMother()));

		return copy;
	}

	@Override
	protected void saveResource(Individual item) throws ConstraintViolations
	{

		try {
			baselineservice.createIndividual(item);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	protected void setReferencedFields(Individual item, ConstraintViolations cv) {
		// TODO Auto-generated method stub
		item.setCollectedBy(fieldBuilder.referenceField(item.getCollectedBy(), cv));
		item.setMother(fieldBuilder.referenceField(item.getMother(), cv,
				"Mother ext id not valid"));
		item.setFather(fieldBuilder.referenceField(item.getFather(), cv,
				"Father ext id not valid"));
	}


	private static Individual copyExtId(Individual individual) {
		Individual copy = new Individual();
		if (individual == null) {
			logger.warn("Individual had a null father or mother - using UNK as default value");
			copy.setExtId("UNK");
		} else {
			copy.setExtId(individual.getExtId());
		}

		return copy;
	}


}













