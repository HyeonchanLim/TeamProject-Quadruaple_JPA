package com.green.project_quadruaple.report;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.base.ReportEnum;
import com.green.project_quadruaple.entity.model.Report;
import com.green.project_quadruaple.entity.model.Role;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.entity.repository.ReportRepository;
import com.green.project_quadruaple.report.model.ReportGetByAmdinDto;
import com.green.project_quadruaple.report.model.ReportPostByUserDto;
import com.green.project_quadruaple.report.model.ReportUpdateByAdminDto;
import com.green.project_quadruaple.entity.repository.UserRepository;
import com.green.project_quadruaple.user.exception.CustomException;
import com.green.project_quadruaple.user.exception.UserErrorCode;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final RoleRepository roleRepository;

    // 신고 추가
    public int insReport(ReportPostByUserDto dto) {
        long signedUserId = authenticationFacade.getSignedUserId();

        User user = userRepository.findById(signedUserId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // ADMIN 권한이 있는 유저 조회 (무조건 하나만 존재)
        Role adminRole = roleRepository.findByRole(UserRole.ADMIN);
        User adminUser = adminRole.getUser();

        // 한글로 입력된 카테고리를 ENUM 값으로 변환
        ReportEnum category = ReportEnum.getKeyByName(dto.getCategory());
        if (category == null) {
            throw new IllegalArgumentException("유효하지 않은 신고 유형입니다: " + dto.getCategory());
        }

        Report report = new Report();
        report.setReportUser(user);
        report.setManager(adminUser);
        report.setCategory(category);
        report.setReportTarget(dto.getReportTarget());
        report.setReason(dto.getReason());

        reportRepository.save(report);

        return 1;
    }

    // 신고 수정
    public int updReport(ReportUpdateByAdminDto dto) {
        long signedUserId = authenticationFacade.getSignedUserId();

        // 현재 로그인한 유저가 ADMIN인지 확인
        Role adminRole = roleRepository.findByRole(UserRole.ADMIN);
        if (adminRole.getUser().getUserId() != signedUserId) {
            throw new CustomException(UserErrorCode.UNAUTHENTICATED);
        }

        // 수정할 신고 찾기
        Report report = reportRepository.findById(dto.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 신고 유형입니다."));

        // 처리 내용 업데이트
        report.setProcessed(dto.getProcessed());
        report.setProcessedAt(LocalDateTime.now());

        // 변경된 신고 저장
        reportRepository.save(report);

        return 1;
    }

    // 신고 조회
    public List<ReportGetByAmdinDto> getReportByAmdinDto() {
        long signedUserId = authenticationFacade.getSignedUserId();

        // 현재 로그인한 유저가 ADMIN인지 확인
        Role adminRole = roleRepository.findByRole(UserRole.ADMIN);
        if (adminRole.getUser().getUserId() != signedUserId) {
            throw new CustomException(UserErrorCode.UNAUTHENTICATED);
        }

        List<Report> reports = reportRepository.findAll();

        if (reports.isEmpty()) {
            return new ArrayList<>();
        }

        return reports.stream()
                .map(report -> new ReportGetByAmdinDto(
                        report.getReportId(),
                        report.getReportUser().getUserId(),
                        report.getCategory(),
                        report.getReportTarget(),
                        report.getReason(),
                        report.getCreatedAt(),
                        report.getProcessed(),
                        report.getProcessedAt()
                ))
                .collect(Collectors.toList());
    }
}
