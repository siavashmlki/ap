package com.university.library.repository;

import com.university.library.entity.BookLoan;
import com.university.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
    List<BookLoan> findByUser(User user);
    List<BookLoan> findByApprovedFalseAndReturnedFalse();
    List<BookLoan> findByApprovedTrueAndReturnedFalse();
    long count();
    long countByApprovedTrue();
    long countByReturnedFalse();
}