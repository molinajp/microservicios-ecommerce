package com.undefined.sales.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.undefined.sales.model.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query(value = "SELECT DISTINCT product_id, SUM(quantity) AS quantity FROM ordersdb.items "
            + "GROUP BY product_id order by quantity desc LIMIT 0, :limit ", nativeQuery = true)
    List<Map<Long, Integer>> getTopItems(@Param("limit") Integer limit);
    
}
