package com.stubhub.fulfillment;import org.drools.KnowledgeBase;import org.drools.KnowledgeBaseFactory;import org.drools.builder.KnowledgeBuilder;import org.drools.builder.KnowledgeBuilderFactory;import org.drools.builder.ResourceType;import org.drools.io.ResourceFactory;import org.drools.runtime.StatelessKnowledgeSession;import org.junit.Test;import static org.junit.Assert.assertTrue;/** * Created with IntelliJ IDEA. * User: jicui * Date: 2/17/13 * Time: 4:57 PM * To change this template use File | Settings | File Templates. */public class FeeRuleTest {    @Test    public void testFedexFee(){        FulfillmentUGPermission fugp = createFedexFulfillmentUGPermission();        fugp.setGenreId(120l);        fugp.setGeoId(7l);        StatelessKnowledgeSession session= getFeeRuleSession();        session.execute( fugp);        assertTrue(fugp.getDeliveryFee().getAmount()==3f);        fugp.setGenreId(1l);        fugp.setGeoId(2l);        session.execute( fugp);        assertTrue(fugp.getDeliveryFee().getAmount()==12f);    }    @Test    public void testBarcodeFee(){        FulfillmentUGPermission fugp = createBarcodeFulfillmentUGPermission();        fugp.setGenreId(120l);        fugp.setGeoId(7l);        StatelessKnowledgeSession session= getFeeRuleSession();        session.execute( fugp);        assertTrue(fugp.getDeliveryFee().getAmount()==1.2f);    }    private StatelessKnowledgeSession getFeeRuleSession() {        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();        kbuilder.add(ResourceFactory.newClassPathResource("rules/fee.drl", getClass()), ResourceType.DRL);        if (kbuilder.hasErrors()) {            System.err.println(kbuilder.getErrors().toString());        }        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());        StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();        return ksession;    }    private FulfillmentUGPermission createFedexFulfillmentUGPermission() {        FulfillmentUGPermission fugp=new  FulfillmentUGPermission();        FulfillmentWindow fw=new FulfillmentWindow();        FulfillmentDeliveryMethod fdm=new FulfillmentDeliveryMethod(FulfillmentMethod.FEDEX, DeliveryMethod.FEDEX_2D);        fw.setFulfillmentDeliveryMethod(fdm);        fugp.setFulfillmentWindow(fw);        return fugp;    }    private FulfillmentUGPermission createBarcodeFulfillmentUGPermission() {        FulfillmentUGPermission fugp=new  FulfillmentUGPermission();        FulfillmentWindow fw=new FulfillmentWindow();        FulfillmentDeliveryMethod fdm=new FulfillmentDeliveryMethod(FulfillmentMethod.BARCODE, DeliveryMethod.INSTANT_DOWNLOAD);        fw.setFulfillmentDeliveryMethod(fdm);        fugp.setFulfillmentWindow(fw);        return fugp;    }}