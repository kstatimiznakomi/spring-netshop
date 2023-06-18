package com.newshop.shopnetnew.dao;

import com.newshop.shopnetnew.domain.Cities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<Cities, Long> {
}
