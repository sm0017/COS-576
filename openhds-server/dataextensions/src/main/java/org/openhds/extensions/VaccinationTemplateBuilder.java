package org.openhds.extensions;

import org.openhds.domain.util.CalendarAdapter;
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

public class VaccinationTemplateBuilder implements ExtensionTemplate {
	
	JCodeModel jCodeModel;
	boolean templateBuilt = false;
	
	VaccinationTemplateBuilder(JCodeModel jCodeModel) {
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
		jfSerial.init(JExpr.lit(9088448901429863510L));
		
		// child
		JFieldVar jfChild = jc.field(JMod.PRIVATE , org.openhds.domain.model.Individual.class, "child");
		jfChild.annotate(org.openhds.domain.constraint.Searchable.class);
		jfChild.annotate(org.openhds.domain.constraint.CheckEntityNotVoided.class);
		jfChild.annotate(org.openhds.domain.constraint.CheckIndividualNotUnknown.class);
		jfChild.annotate(javax.persistence.ManyToOne.class);
		JAnnotationUse jaGroupHeadDesc = jfChild.annotate(org.openhds.domain.annotations.Description.class);
		jaGroupHeadDesc.param("description", "Individual receiving the vaccination, identified by the external id.");
		
		// getter
		JMethod jmgGroupHead = jc.method(JMod.PUBLIC, org.openhds.domain.model.Individual.class, "getChild");
		JBlock jmgGroupHeadBlock = jmgGroupHead.body();
		jmgGroupHeadBlock._return(jfChild);
		
		// setter
		JMethod jmsGroupHead = jc.method(JMod.PUBLIC, void.class, "setChild");
		JVar jvarGroupHead = jmsGroupHead.param(org.openhds.domain.model.Individual.class, "indiv");
		JBlock jmsGroupHeadBlock = jmsGroupHead.body();
		jmsGroupHeadBlock.assign(jfChild, jvarGroupHead);
		
		// bcg
		JFieldVar jfBcg = jc.field(JMod.PRIVATE , java.util.Calendar.class, "bcg");
		jfBcg.annotate(javax.validation.constraints.Past.class);
		JAnnotationUse jaTemporal = jfBcg.annotate(javax.persistence.Temporal.class);
		jaTemporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaBcgDesc = jfBcg.annotate(org.openhds.domain.annotations.Description.class);
		jaBcgDesc.param("description", "Bcg Date.");
		
		// getter
		JMethod jmgBcg = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getBcg");
		JAnnotationUse jaXmlBcg = jmgBcg.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlBcg.param("value", CalendarAdapter.class);
		JBlock jmgBcgBlock = jmgBcg.body();
		jmgBcgBlock._return(jfBcg);
		
		// setter
		JMethod jmsBcg = jc.method(JMod.PUBLIC, void.class, "setBcg");
		JVar jvarBcg = jmsBcg.param(java.util.Calendar.class, "date");
		JBlock jmsBcgBlock = jmsBcg.body();
		jmsBcgBlock.assign(jfBcg, jvarBcg);
		
		// polio1
		JFieldVar jfPolio1 = jc.field(JMod.PRIVATE , java.util.Calendar.class, "polio1");
		jfPolio1.annotate(javax.validation.constraints.Past.class);
		JAnnotationUse jaPolio1Temporal = jfPolio1.annotate(javax.persistence.Temporal.class);
		jaPolio1Temporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaPolio1Desc = jfPolio1.annotate(org.openhds.domain.annotations.Description.class);
		jaPolio1Desc.param("description", "1st polio.");
		
		// getter
		JMethod jmgPolio1 = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getPolio1");
		JAnnotationUse jaXmlPolio1 = jmgPolio1.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlPolio1.param("value", CalendarAdapter.class);
		JBlock jmgPolio1Block = jmgPolio1.body();
		jmgPolio1Block._return(jfPolio1);
		
		// setter
		JMethod jmsPolio1 = jc.method(JMod.PUBLIC, void.class, "setPolio1");
		JVar jvarPolio1 = jmsPolio1.param(java.util.Calendar.class, "date");
		JBlock jmsPolio1Block = jmsPolio1.body();
		jmsPolio1Block.assign(jfPolio1, jvarPolio1);
		
		// polio2
		JFieldVar jfPolio2 = jc.field(JMod.PRIVATE , java.util.Calendar.class, "polio2");
		jfPolio2.annotate(javax.validation.constraints.Past.class);
		JAnnotationUse jaPolio2Temporal = jfPolio2.annotate(javax.persistence.Temporal.class);
		jaPolio2Temporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaPolio2Desc = jfPolio2.annotate(org.openhds.domain.annotations.Description.class);
		jaPolio2Desc.param("description", "2nd polio.");
		
		// getter
		JMethod jmgPolio2 = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getPolio2");
		JAnnotationUse jaXmlPolio2 = jmgPolio2.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlPolio2.param("value", CalendarAdapter.class);
		JBlock jmgPolio2Block = jmgPolio2.body();
		jmgPolio2Block._return(jfPolio2);
		
		// setter
		JMethod jmsPolio2 = jc.method(JMod.PUBLIC, void.class, "setPolio2");
		JVar jvarPolio2 = jmsPolio2.param(java.util.Calendar.class, "date");
		JBlock jmsPolio2Block = jmsPolio2.body();
		jmsPolio2Block.assign(jfPolio2, jvarPolio2);
		
		// polio3
		JFieldVar jfPolio3 = jc.field(JMod.PRIVATE , java.util.Calendar.class, "polio3");
		jfPolio3.annotate(javax.validation.constraints.Past.class);
		JAnnotationUse jaPolio3Temporal = jfPolio3.annotate(javax.persistence.Temporal.class);
		jaPolio3Temporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaPolio3Desc = jfPolio3.annotate(org.openhds.domain.annotations.Description.class);
		jaPolio3Desc.param("description", "3rd polio.");
		
		// getter
		JMethod jmgPolio3 = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getPolio3");
		JAnnotationUse jaXmlPolio3 = jmgPolio3.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlPolio3.param("value", CalendarAdapter.class);
		JBlock jmgPolio3Block = jmgPolio3.body();
		jmgPolio3Block._return(jfPolio3);
		
		// setter
		JMethod jmsPolio3 = jc.method(JMod.PUBLIC, void.class, "setPolio3");
		JVar jvarPolio3 = jmsPolio3.param(java.util.Calendar.class, "date");
		JBlock jmsPolio3Block = jmsPolio3.body();
		jmsPolio3Block.assign(jfPolio3, jvarPolio3);
		
		// polio4
		JFieldVar jfPolio4 = jc.field(JMod.PRIVATE , java.util.Calendar.class, "polio4");
		jfPolio4.annotate(javax.validation.constraints.Past.class);
		JAnnotationUse jaPolio4Temporal = jfPolio4.annotate(javax.persistence.Temporal.class);
		jaPolio4Temporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaPolio4Desc = jfPolio4.annotate(org.openhds.domain.annotations.Description.class);
		jaPolio4Desc.param("description", "4th polio.");
		
		// getter
		JMethod jmgPolio4 = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getPolio4");
		JAnnotationUse jaXmlPolio4 = jmgPolio4.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlPolio4.param("value", CalendarAdapter.class);
		JBlock jmgPolio4Block = jmgPolio4.body();
		jmgPolio4Block._return(jfPolio4);
		
		// setter
		JMethod jmsPolio4 = jc.method(JMod.PUBLIC, void.class, "setPolio4");
		JVar jvarPolio4 = jmsPolio4.param(java.util.Calendar.class, "date");
		JBlock jmsPolio4Block = jmsPolio4.body();
		jmsPolio4Block.assign(jfPolio4, jvarPolio4);
		
		// dpt1
		JFieldVar jfDpt1 = jc.field(JMod.PRIVATE , java.util.Calendar.class, "dpt1");
		jfDpt1.annotate(javax.validation.constraints.Past.class);
		JAnnotationUse jaDpt1Temporal = jfDpt1.annotate(javax.persistence.Temporal.class);
		jaDpt1Temporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaDpt1Desc = jfDpt1.annotate(org.openhds.domain.annotations.Description.class);
		jaDpt1Desc.param("description", "1st dpt.");
		
		// getter
		JMethod jmgDpt1 = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getDpt1");
		JAnnotationUse jaXmlDpt1 = jmgDpt1.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlDpt1.param("value", CalendarAdapter.class);
		JBlock jmgDpt1Block = jmgDpt1.body();
		jmgDpt1Block._return(jfDpt1);
		
		// setter
		JMethod jmsDpt1 = jc.method(JMod.PUBLIC, void.class, "setDpt1");
		JVar jvarDpt1 = jmsDpt1.param(java.util.Calendar.class, "date");
		JBlock jmsDpt1Block = jmsDpt1.body();
		jmsDpt1Block.assign(jfDpt1, jvarDpt1);
		
		// dpt2
		JFieldVar jfDpt2 = jc.field(JMod.PRIVATE , java.util.Calendar.class, "dpt2");
		jfDpt2.annotate(javax.validation.constraints.Past.class);
		JAnnotationUse jaDpt2Temporal = jfDpt2.annotate(javax.persistence.Temporal.class);
		jaDpt2Temporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaDpt2Desc = jfDpt2.annotate(org.openhds.domain.annotations.Description.class);
		jaDpt2Desc.param("description", "2nd dpt.");
		
		// getter
		JMethod jmgDpt2 = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getDpt2");
		JAnnotationUse jaXmlDpt2 = jmgDpt2.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlDpt2.param("value", CalendarAdapter.class);
		JBlock jmgDpt2Block = jmgDpt2.body();
		jmgDpt2Block._return(jfDpt2);
		
		// setter
		JMethod jmsDpt2 = jc.method(JMod.PUBLIC, void.class, "setDpt2");
		JVar jvarDpt2 = jmsDpt2.param(java.util.Calendar.class, "date");
		JBlock jmsDpt2Block = jmsDpt2.body();
		jmsDpt2Block.assign(jfDpt2, jvarDpt2);
		
		// dpt3
		JFieldVar jfDpt3 = jc.field(JMod.PRIVATE , java.util.Calendar.class, "dpt3");
		jfDpt3.annotate(javax.validation.constraints.Past.class);
		JAnnotationUse jaDpt3Temporal = jfDpt3.annotate(javax.persistence.Temporal.class);
		jaDpt3Temporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaDpt3Desc = jfDpt3.annotate(org.openhds.domain.annotations.Description.class);
		jaDpt3Desc.param("description", "3rd dpt.");
		
		// getter
		JMethod jmgDpt3 = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getDpt3");
		JAnnotationUse jaXmlDpt3 = jmgDpt3.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlDpt3.param("value", CalendarAdapter.class);
		JBlock jmgDpt3Block = jmgDpt3.body();
		jmgDpt3Block._return(jfDpt3);
		
		// setter
		JMethod jmsDpt3 = jc.method(JMod.PUBLIC, void.class, "setDpt3");
		JVar jvarDpt3 = jmsDpt3.param(java.util.Calendar.class, "date");
		JBlock jmsDpt3Block = jmsDpt3.body();
		jmsDpt3Block.assign(jfDpt3, jvarDpt3);
		
		// measels
		JFieldVar jfMeasels = jc.field(JMod.PRIVATE , java.util.Calendar.class, "measels");
		jfMeasels.annotate(javax.validation.constraints.Past.class);
		JAnnotationUse jaMeaselsTemporal = jfMeasels.annotate(javax.persistence.Temporal.class);
		jaMeaselsTemporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaMeaselsDesc = jfMeasels.annotate(org.openhds.domain.annotations.Description.class);
		jaMeaselsDesc.param("description", "First child measels.");
		
		// getter
		JMethod jmgMeasels = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getMeasels");
		JAnnotationUse jaXmlMeasels = jmgMeasels.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlMeasels.param("value", CalendarAdapter.class);
		JBlock jmgMeaselsBlock = jmgMeasels.body();
		jmgMeaselsBlock._return(jfMeasels);
		
		// setter
		JMethod jmsMeasels = jc.method(JMod.PUBLIC, void.class, "setMeasels");
		JVar jvarMeasels = jmsMeasels.param(java.util.Calendar.class, "date");
		JBlock jmsMeaselsBlock = jmsMeasels.body();
		jmsMeaselsBlock.assign(jfMeasels, jvarMeasels);
	}

	@Override
	public void buildClassAnnotations(JDefinedClass jc) {
		
		// create Description annotation
		JAnnotationUse jad = jc.annotate(org.openhds.domain.annotations.Description.class);
		jad.param("description", "A Vaccination is filled out for ever Pregnancy Outcome. It contains a list of " +
				"dates in which a child has received various immunications.");
				
		// create Entity annotation
		jc.annotate(javax.persistence.Entity.class);
		
		JAnnotationUse jat = jc.annotate(javax.persistence.Table.class);
		jat.param("name", "vaccination");
		
		JAnnotationUse jxmlRoot = jc.annotate(javax.xml.bind.annotation.XmlRootElement.class);
		jxmlRoot.param("name", "vaccination");
	}
}
