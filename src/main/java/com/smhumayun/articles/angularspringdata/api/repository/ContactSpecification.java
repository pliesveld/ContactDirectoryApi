package com.smhumayun.articles.angularspringdata.api.repository;

import com.smhumayun.articles.angularspringdata.api.model.Contact;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Humayun on 5/25/2016.
 */
public class ContactSpecification {

    public static Specification<Contact> getFilterSpec (Long filterId, String filterName, String filterPrimaryNumber
            , String filterSecondaryNumber, String filterEmailAddress, DateTime filterUpdatedOnStart
            , DateTime filterUpdatedOnEnd) {
        return new Specification<Contact>() {
            @Override
            public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> criteriaQuery
                    , CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if(filterId != null) {
                    Expression<String> expr = root.get("id").as(String.class);
                    predicates.add(criteriaBuilder.like(expr, "%" + filterId + "%"));
                }
                if(filterName != null) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + filterName + "%"));
                }
                if(filterPrimaryNumber != null) {
                    predicates.add(criteriaBuilder.like(root.get("primaryNumber"), "%" + filterPrimaryNumber + "%"));
                }
                if(StringUtils.hasLength(filterSecondaryNumber)) {
                    predicates.add(criteriaBuilder.like(root.get("secondaryNumber"), "%" + filterSecondaryNumber + "%"));
                }
                if(filterEmailAddress != null) {
                    predicates.add(criteriaBuilder.like(root.get("emailAddress"), "%" + filterEmailAddress + "%"));
                }
                if(filterUpdatedOnStart != null && filterUpdatedOnEnd != null) {
                    predicates.add(criteriaBuilder.between(root.get("updatedOn"), filterUpdatedOnStart, filterUpdatedOnEnd));
                }
                else if(filterUpdatedOnStart != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("updatedOn"), filterUpdatedOnStart));
                }
                else if(filterUpdatedOnEnd != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("updatedOn"), filterUpdatedOnStart));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
