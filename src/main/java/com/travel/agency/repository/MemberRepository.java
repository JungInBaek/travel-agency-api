package com.travel.agency.repository;

import com.travel.agency.domain.Member;
import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(String id) {
        return Optional.of(em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    public void deleteById(String id) {
        Assert.notNull(id, "The given delete member id must not be null!");

        delete(findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", Member.class, id), 1)));
    }

    public void delete(Member member) {
        Assert.notNull(member, "Entity must not be null!");

        if (member == null) {
            return;
        }

        em.remove(em.contains(member) ? member : em.merge(member));
    }

}
