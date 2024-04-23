package lab.ejb;


import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.Message;
import jakarta.jms.JMSException;
import jakarta.jms.ObjectMessage;
import jakarta.persistence.EntityManager;
import jakarta.jms.*;
import jakarta.persistence.PersistenceContext;

@JMSDestinationDefinition(name = "java:app/jms/NewsQueue",
                          interfaceName = "jakarta.jms.Queue",
                          resourceAdapter = "jmsra",
                          destinationName = "NewsQueue")
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                                  propertyValue = "java:app/jms/NewsQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                                  propertyValue = "jakarta.jms.Queue") })
public class NewsMDB implements jakarta.jms.MessageListener {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                var text = msg.getText();
                var textSplit = text.split("\\|");
                NewsItem e = new NewsItem();
                e.setHeading(textSplit[0]);
                e.setBody(textSplit[1]);
                em.persist(e);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
