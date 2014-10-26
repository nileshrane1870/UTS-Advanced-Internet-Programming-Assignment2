
package au.edu.uts.aip.detentiontracker.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;


@RequestScoped
public class ProcessingWAO {
    
    //@Resource(name = "pinPayments")
    String pinPayments ="http://test-api.pin.net.au/1/";
    
    
    private PINResponse request = new PINResponse();
    private Response response = new Response();
    private PINCard reqCard = new PINCard();
    private PINCard respCard = new PINCard();
    
    
     public PINResponse getRequest()
    {
        
        return request;
    }
    public Response getResponse()
    {
        return response;
    }

    public PINCard getReqCard() {
        return reqCard;
    }

    public PINCard getRespCard() {
        return respCard;
    }
    
        public String PAYSOMEBITCHES()
    {
        request.setAmount(200000);
        request.setCurrency("USD");
        request.setDescription("what eves brah");
        request.setEmail("WHAT YOU SAY@faggot.com");
        request.setIpAddress("203.192.1.172");
        PINCard lolcard = new PINCard();
        //lolcard.setToken("ch_aM8lCZsusic-ehncUVjFFw");
        lolcard.setScheme("master");
        lolcard.setNumber("5520000000000000");
        lolcard.setExpiry_Month(5);
        lolcard.setExpiry_Year(2015);
        lolcard.setCVC(123);
        lolcard.setName("Roland Robot");
        lolcard.setAddress_Line1("42 Sevenoaks St");
        lolcard.setAddress_Line2("");
        lolcard.setAddress_City("Lathlain");
        lolcard.setAddressPostcode(6454);
        lolcard.setAddress_State("WA");
        lolcard.setAddress_Country("Australia");
        request.setCard(lolcard);
        
                
                pinPayments += "charges";
        Client client = null;
        
        try{
            System.out.println("my url is" + pinPayments);
            client = ClientBuilder.newClient();
            response = client.target(pinPayments)
                    .request()
                    .header("Authorization", "Basic MDVXVzFlMzVtRGJka1lONlhsQVhkdzpxd2VydHkxMjM=")
                    .post(Entity.json(request), Response.class );
                    
                    
            
            
             FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(response.toString()));
            
            
        } catch (ProcessingException | WebApplicationException | NullPointerException e)
        {
               Logger log = Logger.getLogger(this.getClass().getName());
            log.log(Level.SEVERE, "Could not communicate with swap web service", e);
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("OH SHIT SON YOU BROKE IT " + e.getLocalizedMessage().toString() + e.getMessage().toString()   ));
            
            return null;
    } finally {
            if(client != null)
                client.close();
        }
        
    return "PINTEST";

    } 
    public String makeCard(String cardNumber, int month, int year, int cvc, String name, String add1, String add2, String city, int post, String state)
    {
        
        
        
//        reqCard.setNumber(cardNumber);
//        reqCard.setExpiry_Month(month);
//        reqCard.setExpiry_Year(year);
//        reqCard.setCVC(cvc);
//        reqCard.setName(name);
//        reqCard.setAddress_Line1(add1);
//        reqCard.setAddress_Line2(add2);
//        reqCard.setAddress_City(city);
//        reqCard.setAddressPostcode(post);
//        reqCard.setAddress_State(state);
//        reqCard.setAddress_Country("Australia");
        
         PINCard lolcard = new PINCard();
        //lolcard.setToken("ch_aM8lCZsusic-ehncUVjFFw");
        lolcard.setScheme("master");
        lolcard.setNumber("5520000000000000");
        lolcard.setExpiry_Month(5);
        lolcard.setExpiry_Year(2015);
        lolcard.setCVC(123);
        lolcard.setName("Roland Robot");
        lolcard.setAddress_Line1("42 Sevenoaks St");
        lolcard.setAddress_Line2("");
        lolcard.setAddress_City("Lathlain");
        lolcard.setAddressPostcode(6454);
        lolcard.setAddress_State("WA");
        lolcard.setAddress_Country("Australia");
        reqCard.equals(lolcard);
        
        System.out.println(pinPayments == null);
        pinPayments += "cards";
        Client client = null;
        
        try{
            System.out.println("my url is for cards " + pinPayments);
            client = ClientBuilder.newClient();
            respCard = client.target(pinPayments)
                    .request()
                    .header("Authorization", "Basic MDVXVzFlMzVtRGJka1lONlhsQVhkdzpxd2VydHkxMjM=")
                    .post(Entity.json(reqCard), PINCard.class );
                    
                    
            
            System.out.println("how many frogs on this " + respCard.getToken());
            return respCard.getToken();
        } catch (ProcessingException | WebApplicationException | NullPointerException e)
        {
               
            
            FacesContext context = FacesContext.getCurrentInstance();
            System.out.println("Card token creation failed " + e.getMessage().toString());
            
          return null;
    } finally {
            if(client != null)
                client.close();
        }
    }
}