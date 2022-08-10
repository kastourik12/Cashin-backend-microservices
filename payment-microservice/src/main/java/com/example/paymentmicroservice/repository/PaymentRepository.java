package com.example.paymentmicroservice.repository;

import com.example.paymentmicroservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,String> {



    @Query(value = "select x.* from payments x where x.user_id = :id",nativeQuery = true)
    List<Payment> findAllByUserId(String id);
}
