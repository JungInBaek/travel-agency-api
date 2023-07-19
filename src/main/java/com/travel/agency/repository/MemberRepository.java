package com.travel.agency.repository;

import com.travel.agency.domain.Member;
import com.travel.agency.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

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
        try {
            Member member = em.createQuery("select m from Member m where m.id = :id", Member.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            throw new MemberNotFoundException();
        }
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public void deleteById(String id) {
        Assert.notNull(id, "The given delete member id must not be null!");

        delete(findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", Member.class, id), 1)
        ));
    }

    public void delete(Member member) {
        Assert.notNull(member, "Entity must not be null!");

        if (member == null) {
            return;
        }

        em.remove(em.contains(member) ? member : em.merge(member));
    }

    public void deleteAll() {
        findAll().stream().forEach(member -> {
            delete(member);
        });
    }

}
