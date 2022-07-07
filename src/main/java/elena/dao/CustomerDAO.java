package elena.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import elena.models.Customer;

import java.util.List;

@Component
public class CustomerDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> index() {
        return jdbcTemplate.query("SELECT * FROM Customer", new BeanPropertyRowMapper<>(Customer.class));
    }

    public Customer show(int id) {
        return jdbcTemplate.query("SELECT * FROM Customer WHERE id=?",new BeanPropertyRowMapper<>(Customer.class), new Object[]{id})
                .stream().findAny().orElse(null);
    }

    public void save(Customer customer) {
        jdbcTemplate.update("INSERT INTO Customer VALUES(?, ?, ?, ?)", customer.getId(), customer.getName(), customer.getTel(),
                customer.getEmail());
    }

    public void update(int id, Customer updatedCustomer) {
        jdbcTemplate.update("UPDATE Customer SET name=?, tel=?, email=? WHERE id=?", updatedCustomer.getName(),
                updatedCustomer.getTel(), updatedCustomer.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Customer WHERE id=?", id);
    }
}

