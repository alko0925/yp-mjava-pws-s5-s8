package ru.yp.sprint5pw.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yp.sprint5pw.entity.Account;


import java.sql.ResultSet;
import java.util.List;

@Repository
public class AccountDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Account> accountRowMapper = (ResultSet rs, int rowNum) -> new Account(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBigDecimal("balance")
    );

    public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Account> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, balance FROM account",
                accountRowMapper
        );
    }

    public Account findByName(String name) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, balance FROM account WHERE name = ? LIMIT 1",
                accountRowMapper,
                name
        );
    }

    public void create(String name) {
        jdbcTemplate.update(
                "INSERT INTO account (name) VALUES (?)",
                name
        );
    }

    public void update(Account account) {
        jdbcTemplate.update(
                "UPDATE account SET name = ?, balance = ? WHERE id = ?",
                account.getName(),
                account.getBalance(),
                account.getId()
        );
    }
}
