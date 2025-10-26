package com.innogent.Library.management.System.repositories;

import com.innogent.Library.management.System.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
