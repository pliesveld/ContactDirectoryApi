package com.smhumayun.articles.angularspringdata.api.repository;

import com.smhumayun.articles.angularspringdata.api.model.Contact;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Humayun on 5/23/2016.
 */
@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
}
