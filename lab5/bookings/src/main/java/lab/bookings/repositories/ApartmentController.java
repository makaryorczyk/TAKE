package lab.bookings.repositories;

// annotate as spring mvc controller

import lab.bookings.models.Apartment;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/apartments")
public class ApartmentController {

    private ApartmentRepository repository;

    public ApartmentController(ApartmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String getALL(Model model){
        var apartments = repository.findAll();
        Apartment apartment = new Apartment();
        model.addAttribute("apartments", apartments);
        model.addAttribute("apartment", apartment);
        return "apartments";
    }
//    @PostMapping()
//    public String create(Apartment apartment) {
//        repository.save(apartment);
//        return "redirect:/apartments";
//    }

    @PostMapping()
    public String createTable(@Valid Apartment apartment,
                              Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("apartments", repository.findAll());
            return "apartments";
        } else {
            repository.save(apartment);
            return "redirect:/apartments";
        }
    }

}
