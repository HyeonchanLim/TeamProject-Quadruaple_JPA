package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.Amenipoint;
import com.green.project_quadruaple.entity.model.AmenipointId;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenipointRepository extends JpaRepository<Amenipoint , AmenipointId> {
    void deleteByStayTourRestaurFest(StayTourRestaurFest strf);
}
