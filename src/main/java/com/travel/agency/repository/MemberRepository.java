package com.travel.agency.repository;

import com.travel.agency.domain.Member;
import com.travel.agency.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
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
    public Optional<Member> duplicateIdCheck(String id) {
        String qlString = "select m from Member m where m.id = :id";
        return em.createQuery(qlString, Member.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByIdAndPassword(String id, String password) {
        Assert.notNull(id, "아이디를 입력해주세요");
        String qlString = "select m from Member m where m.id = :id and m.password = :password";
        return em.createQuery(qlString, Member.class)
                .setParameter("id", id)
                .setParameter("password", password)
                .getResultStream()
                .findFirst();
    }

    public Optional<String> getSalt(String id) {
        Assert.notNull(id, "아이디를 입력해주세요");
        String qlString = "select m.salt from Member m where m.id = :id";
        return em.createQuery(qlString, String.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(String id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public List<Member> findAll() {
        String qlString = "select m from Member m";
        return em.createQuery(qlString, Member.class)
                .getResultList();
    }

    public void deleteById(String id) {
        delete(findById(id).orElseThrow(MemberNotFoundException::new));
    }

    public void delete(Member member) {
        if (member == null) {
            return;
        }
        em.remove(em.contains(member) ? member : em.merge(member));
    }

    public void deleteAll() {
        findAll().forEach(this::delete);
    }

}
