package com.nrbswift.spring4web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Transactional
@Component("userDao")
public class UserDAO {
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private SessionFactory sessionFactory;

    public UserDAO() {
    }

    @Autowired
    public UserDAO(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }


    public boolean createUser(User user) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);

        MapSqlParameterSource pa = new MapSqlParameterSource();
        pa.addValue("username", user.getUsername());
        pa.addValue("authority", "ROLE_ADMIN");

        jdbc.update("insert into users (username, name, email, password, enabled) values (:username, :name, :email, :password, :enabled)", params);

        return jdbc.update("insert into authorities (username, authority) values (:username, :authority)", pa) == 1;
    }

    public boolean createUserT(User user) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);

        MapSqlParameterSource pa = new MapSqlParameterSource();
        pa.addValue("username", user.getUsername());
        pa.addValue("authority", "ROLE_ADMIN");

        jdbc.update("insert into users (username, name, email, password, enabled) values (:username, :name, :email, :password, :enabled)", params);

        return jdbc.update("insert into authoritiess (username, authority) values (:username, :authority)", pa) == 1;
    }


    public boolean exists(String username) {
        return jdbc.queryForObject("select count(*) from users where username = :username",
                new MapSqlParameterSource("username", username), Integer.class) > 0;

    }

    public List<User> getAll() {

        return sessionFactory.getCurrentSession().createQuery("from User").list();

//        return jdbc.query("select * from users, authorities where users.username=authorities.username",
//                BeanPropertyRowMapper.newInstance(User.class));
    }
}
