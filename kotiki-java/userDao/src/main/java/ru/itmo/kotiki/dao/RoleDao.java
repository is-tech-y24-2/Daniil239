package ru.itmo.kotiki.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.dto.Roles;
import ru.itmo.kotiki.dao.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, Integer> {

    Role findByName(Roles name);
}
