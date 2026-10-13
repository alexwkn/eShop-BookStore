package com.eshop.bookstore.dataAccess.repository;

import com.eshop.bookstore.dataAccess.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {

}
