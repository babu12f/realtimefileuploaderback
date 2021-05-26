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
@Component("pictureDao")
public class PictureDao {
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private SessionFactory sessionFactory;

    public PictureDao() {
    }

    @Autowired
    public PictureDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean insertPicture(Picture picture) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(picture);

        return jdbc.update("insert into pictures (imagePath, createdDate) values (:imagePath, :createdDate)", params) == 1;
    }

    public List<Picture> getAll() {

        return sessionFactory.getCurrentSession().createQuery("from Picture").list();
    }
}
