package lab.ejb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.ejb.Stateless;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@Stateless
@JMSDestinationDefinition(name = "java:app/jms/NewsQueue",
        interfaceName = "jakarta.jms.Queue",
        resourceAdapter = "jmsra",
        destinationName = "NewsQueue")
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:app/jms/NewsQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "jakarta.jms.Queue") })
public class NewsItemFacade implements NewsItemFacadeLocal {

    @PersistenceContext
    private EntityManager em;

    public List<NewsItem> getAllNewsItems() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<NewsItem> cq = cb.createQuery(NewsItem.class);
        Root<NewsItem> rootEntry = cq.from(NewsItem.class);
        CriteriaQuery<NewsItem> ct = cq.select(rootEntry);
        TypedQuery<NewsItem> allNewsItemsQuery = em.createQuery(ct);
        return allNewsItemsQuery.getResultList();
    }
}
