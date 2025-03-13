package com.green.project_quadruaple.expense;

import com.green.project_quadruaple.entity.view.Depay;
import com.green.project_quadruaple.entity.view.DepayIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepayRepository extends JpaRepository<Depay, DepayIds> {
    @Query("select d from Depay d where d.tripId= :tripId order by d.deId desc")
    Optional<List<Depay>> findByTripId(Long tripId);
}
