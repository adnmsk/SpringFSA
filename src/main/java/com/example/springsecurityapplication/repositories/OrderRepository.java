package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Transactional
    @Modifying
    @Query("update Order o set o.status = ?1 where o.status = ?2")
    int ALTER(Status status, @NonNull Status status1);
    List<Order> findByPerson(Person person);

    //Получаем все заказы и запихиваем их в лист

    List<Order> getAllById(Order order);

    @Query(value = "select * from orders where (lower(number) LIKE '?1%'", nativeQuery = true)

    List<Order> findByLastNumber(String lastNumber);

    Order findById(int id);


}
