package lab.app;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.client.Entity;
import lab.entities.Complaint;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();

        // 1. All complaints
        List<Complaint> complaints = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/api/complaints")
                        .queryParam("status", "open")
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Complaint>>() {});


        // print all complaints information
        complaints.forEach(c -> System.out.println(c.getId() + " " +  c.getAuthor() + " " +  c.getStatus() + " " +  c.getComplaintDate() + " " +  c.getComplaintText()));

        // 2. Status of specific open complaint
        Complaint o = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" +
                        "api/complaints")
                .queryParam("status", "open")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Complaint>>() {}).get(0);
        System.out.println(o.getId() + " " + o.getAuthor() + " " + o.getStatus() + " " + o.getComplaintDate() + " " + o.getComplaintText());

        // 3. change status of complaint to close by substitution
        o.setStatus("close");

        client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" +
                        "api/complaints")
                .path("{id}")
                .resolveTemplate("id", o.getId())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(o), Complaint.class);


        // 4. Get all open complaints
        List<Complaint> openComp = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/" +
                        "api/complaints")
                .queryParam("status", "open")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Complaint>>() {});
        openComp.forEach(c -> System.out.println(c.getId() + " " +  c.getAuthor() + " " +  c.getStatus() + " " +  c.getComplaintDate() + " " +  c.getComplaintText()));






        client.close();
    }
}
