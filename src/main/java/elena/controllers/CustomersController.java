package elena.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import elena.dao.CustomerDAO;
import elena.models.Customer;

import javax.validation.Valid;

@Controller
@RequestMapping("/customers")
@Api(value = "Контроллер для иллюстрации")
public class CustomersController {

    private final CustomerDAO customerDAO;

    @Autowired
    public CustomersController(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @GetMapping()
    @ApiOperation("Получение записей")
    public String index(Model model) {
        model.addAttribute("customers", customerDAO.index());
        return "customers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("customer", customerDAO.show(id));
        return "customers/show";
    }

    @GetMapping("/new")
    public String newCustomer (@ModelAttribute("customer") Customer customer) {
        return "customers/new";
    }

    @PostMapping()
    @ApiOperation("Создание записи")
    public String create(@ModelAttribute("customer") @Valid Customer customer,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "customers/new";

        customerDAO.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")

    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("customer", customerDAO.show(id));
        return "customers/edit";
    }

    @PatchMapping("/{id}")
    @ApiOperation("Редактирование записей")
    public String update(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "customers/edit";

        customerDAO.update(id, customer);
        return "redirect:/customers";
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление записей")
    public String delete(@PathVariable("id") int id) {
        customerDAO.delete(id);
        return "redirect:/customers";
    }
}

