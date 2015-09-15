package org.openhds.extensions;

import org.openhds.domain.util.CalendarAdapter;
import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

public class PregnancyObservationTemplateBuilder implements ExtensionTemplate  {
	
	JCodeModel jCodeModel;
	boolean templateBuilt = false;
	
	PregnancyObservationTemplateBuilder(JCodeModel jCodeModel) {
		this.jCodeModel = jCodeModel;
	}

	@Override
	public void buildTemplate(JDefinedClass jc) {
		
		JDocComment jDocComment = jc.javadoc();
		jDocComment.add("Generated by JCodeModel");
		
		jc._extends(org.openhds.domain.model.AuditableCollectedEntity.class);
		jc._implements(java.io.Serializable.class);
		
		buildClassAnnotations(jc);
		buildFieldsAndMethods(jc);
		
		templateBuilt = true;
	}

	@Override
	public void buildFieldsAndMethods(JDefinedClass jc) {
		
		// serial uuid
		JFieldVar jfSerial = jc.field(JMod.PUBLIC | JMod.STATIC | JMod.FINAL, long.class, "serialVersionUID");
		jfSerial.init(JExpr.lit(-4737117368371754337L));
		
		// mother
		JFieldVar jfMother = jc.field(JMod.PRIVATE , org.openhds.domain.model.Individual.class, "mother");
		jfMother.annotate(org.openhds.domain.constraint.CheckIndividualNotUnknown.class);
		jfMother.annotate(org.openhds.domain.constraint.Searchable.class);
		JAnnotationUse jaCheckFemale = jfMother.annotate(org.openhds.domain.constraint.CheckIndividualGenderFemale.class);
		jaCheckFemale.param("allowNull", true);
		jaCheckFemale.param("message", "The mother specified is not female gender");
		JAnnotationUse jaMotherNotVoided = jfMother.annotate(org.openhds.domain.constraint.CheckEntityNotVoided.class);
		jaMotherNotVoided.param("allowNull", true);
		jaMotherNotVoided.param("message", "The mother has been voided");
		JAnnotationUse jfMotherCascade = jfMother.annotate(javax.persistence.ManyToOne.class);
		JAnnotationArrayMember motherArray = jfMotherCascade.paramArray("cascade");
		motherArray.param(javax.persistence.CascadeType.MERGE);
		motherArray.param(javax.persistence.CascadeType.PERSIST);
		jfMotherCascade.param("targetEntity", org.openhds.domain.model.Individual.class);		
		JAnnotationUse jaMotherDesc = jfMother.annotate(org.openhds.domain.annotations.Description.class);
		jaMotherDesc.param("description", "The mother of the pregnancy observation, identified by the external id.");
		
		// getter
		JMethod jmgMother = jc.method(JMod.PUBLIC, org.openhds.domain.model.Individual.class, "getMother");
		JBlock jmgMotherBlock = jmgMother.body();
		jmgMotherBlock._return(jfMother);
		
		// setter
		JMethod jmsMother = jc.method(JMod.PUBLIC, void.class, "setMother");
		JVar jvarMother = jmsMother.param(org.openhds.domain.model.Individual.class, "mom");
		JBlock jmsMotherBlock = jmsMother.body();
		jmsMotherBlock.assign(jfMother, jvarMother);
				
		// expectedDeliveryDate
		JFieldVar jfExpectedDelivery = jc.field(JMod.PRIVATE , java.util.Calendar.class, "expectedDeliveryDate");
		jfExpectedDelivery.annotate(javax.validation.constraints.NotNull.class);
		JAnnotationUse jaTemporal = jfExpectedDelivery.annotate(javax.persistence.Temporal.class);
		jaTemporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaExpectedDeliveryDesc = jfExpectedDelivery.annotate(org.openhds.domain.annotations.Description.class);
		jaExpectedDeliveryDesc.param("description", "Expected delivery date.");
		
		// getter
		JMethod jmgExpectedDelivery = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getExpectedDeliveryDate");
		JAnnotationUse jaXmlExpectedDelivery = jmgExpectedDelivery.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlExpectedDelivery.param("value", CalendarAdapter.class);
		JBlock jmgExpectedDeliveryBlock = jmgExpectedDelivery.body();
		jmgExpectedDeliveryBlock._return(jfExpectedDelivery);
		
		// setter
		JMethod jmsExpectedDelivery = jc.method(JMod.PUBLIC, void.class, "setExpectedDeliveryDate");
		JVar jvarExpectedDelivery = jmsExpectedDelivery.param(java.util.Calendar.class, "date");
		JBlock jmsExpectedDeliveryBlock = jmsExpectedDelivery.body();
		jmsExpectedDeliveryBlock.assign(jfExpectedDelivery, jvarExpectedDelivery);
		
		// recordedDate
		JFieldVar jfRecordedDate = jc.field(JMod.PRIVATE , java.util.Calendar.class, "recordedDate");
		jfRecordedDate.annotate(javax.validation.constraints.Past.class);
		jfRecordedDate.annotate(javax.validation.constraints.NotNull.class);
		JAnnotationUse jaRecordedDateTemporal = jfRecordedDate.annotate(javax.persistence.Temporal.class);
		jaRecordedDateTemporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaRecordedDateDesc = jfRecordedDate.annotate(org.openhds.domain.annotations.Description.class);
		jaRecordedDateDesc.param("description", "Recorded date of the pregnancy observation.");
		
		// getter
		JMethod jmgRecordedDate = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getRecordedDate");
		JAnnotationUse jaXmlRecordedDate = jmgRecordedDate.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlRecordedDate.param("value", CalendarAdapter.class);
		JBlock jmgRecordedDateBlock = jmgRecordedDate.body();
		jmgRecordedDateBlock._return(jfRecordedDate);
		
		// setter
		JMethod jmsRecordedDate = jc.method(JMod.PUBLIC, void.class, "setRecordedDate");
		JVar jvarRecordedDate = jmsRecordedDate.param(java.util.Calendar.class, "date");
		JBlock jmsRecordedDateBlock = jmsRecordedDate.body();
		jmsRecordedDateBlock.assign(jfRecordedDate, jvarRecordedDate);
		
		// visit
		JFieldVar jfVisit = jc.field(JMod.PRIVATE , org.openhds.domain.model.Visit.class, "visit");
		jfVisit.annotate(org.openhds.domain.constraint.Searchable.class);
		jfVisit.annotate(javax.validation.constraints.NotNull.class);
		jfVisit.annotate(javax.persistence.ManyToOne.class);
		JAnnotationUse jaVisitDesc = jfVisit.annotate(org.openhds.domain.annotations.Description.class);
		jaVisitDesc.param("description", "The visit this pregnancy observation was registered during.");
		
		// getter
		JMethod jmgVisit = jc.method(JMod.PUBLIC, org.openhds.domain.model.Visit.class, "getVisit");
		JBlock jmgVisitBlock = jmgVisit.body();
		jmgVisitBlock._return(jfVisit);
		
		// setter
		JMethod jmsVisit = jc.method(JMod.PUBLIC, void.class, "setVisit");
		JVar jvarVisit = jmsVisit.param(org.openhds.domain.model.Visit.class, "vis");
		JBlock jmsVisitBlock = jmsVisit.body();
		jmsVisitBlock.assign(jfVisit, jvarVisit);
	}

	@Override
	public void buildClassAnnotations(JDefinedClass jc) {
		
		// create Description annotation
		JAnnotationUse jad = jc.annotate(org.openhds.domain.annotations.Description.class);
		jad.param("description", "A Pregnancy Observation is used to monitor a " +
		"pregnancy. It contains information about the mother who is pregnant, " +
		"the date the pregnancy started, as well as the expected delivery date.");
				
		// create Entity annotation
		jc.annotate(javax.persistence.Entity.class);
		
		JAnnotationUse jat = jc.annotate(javax.persistence.Table.class);
		jat.param("name", "pregnancyobservation");
		
		JAnnotationUse jxmlRoot = jc.annotate(javax.xml.bind.annotation.XmlRootElement.class);
		jxmlRoot.param("name", "pregnancyobservation");
	}
}