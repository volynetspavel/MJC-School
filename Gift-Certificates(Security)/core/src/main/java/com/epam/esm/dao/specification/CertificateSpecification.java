package com.epam.esm.dao.specification;

import com.epam.esm.constant.TableColumn;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Specification for searching certificates by several parameters.
 */
public class CertificateSpecification {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String TAGS = "tags";
    private static final String PERCENT = "%";

    public static Specification<Certificate> hasSeveralParams(String tagName, String partOfCertificateName,
                                                              String partOfCertificateDescription) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            Predicate predicate;

            if (!tagName.isEmpty()) {
                predicate = criteriaBuilder.like(root.join(TAGS).get(NAME),
                        PERCENT + tagName + PERCENT);
                predicateList.add(predicate);
            }
            if (!partOfCertificateName.isEmpty()) {
                predicate = criteriaBuilder.like(root.get(TableColumn.NAME),
                        PERCENT + partOfCertificateName + PERCENT);
                predicateList.add(predicate);
            }
            if (!partOfCertificateDescription.isEmpty()) {
                predicate = criteriaBuilder.like(root.get(TableColumn.DESCRIPTION),
                        PERCENT + partOfCertificateDescription + PERCENT);
                predicateList.add(predicate);
            }
            query.orderBy(criteriaBuilder.asc(root.get(ID)));

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }

    public static Specification<Certificate> hasSeveralTags(List<String> tagNames) {
        return (root, query, criteriaBuilder) -> {

            Join<Certificate, Tag> tagJoin = root.join(TAGS);

            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(tagJoin.get(NAME));
            for (String name : tagNames) {
                inClause.value(name);
            }

            query
                    .groupBy(root.get(ID))
                    .having(criteriaBuilder.equal(criteriaBuilder.count(tagJoin.get(NAME)), tagNames.size()));
            return criteriaBuilder.and(inClause);
        };
    }
}
