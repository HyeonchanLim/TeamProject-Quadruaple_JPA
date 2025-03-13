package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.Amenipoint;
import com.green.project_quadruaple.entity.model.AmenipointId;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmenipointRepository extends JpaRepository<Amenipoint , AmenipointId> {
    void deleteByStayTourRestaurFest(StayTourRestaurFest strf);
//    // 복합키 조회용 메서드 (amenityId 리스트와 strfId 조합으로 검색)
//    List<AmenipointId> findAllByAmenityIdInAndStrfId(List<Long> amenityIds, Long strfId);
//
//    // 특정 AmenipointId에 해당하는 데이터 조회
//    List<Amenipoint> findAllByAmeniPointId(Long ameniPointId);
}
