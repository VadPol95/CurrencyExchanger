package com.vadpol.ex.repository;

import com.vadpol.ex.entity.User;
import com.vadpol.ex.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findAllByUser(User user);

}
