package com.newshop.shopnetnew.dao;

import com.newshop.shopnetnew.domain.DeliveryPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPointsRepository extends JpaRepository<DeliveryPoints, Long> {
    public DeliveryPoints getDeliveryPointsById(Long id);

}
