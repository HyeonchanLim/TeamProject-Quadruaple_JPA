package com.green.project_quadruaple.expense;

import com.green.project_quadruaple.expense.model.req.*;
import com.green.project_quadruaple.expense.model.req.DutchReq;
import com.green.project_quadruaple.expense.model.req.ExpenseDelReq;
import com.green.project_quadruaple.expense.model.req.ExpenseInsReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("expense")
@Tag(name = "가계부")
public class ExpenseController {
    private final ExpenseService expenseService;

    //가계부 보기
    @GetMapping
    @Operation(summary = "가계부 보기", description = "여행에 관련한 비용목록 보기")
    public ResponseEntity<?> getExpenses(@RequestParam("trip_id") long tripId){
        return expenseService.getExpenses(tripId);
    }


    //가계부 추가
    @PostMapping
    @Operation(summary = "가계부 입력", description = "비용목록에 한 칸 추가")
    public ResponseEntity<?> postExpenses(@RequestBody ExpenseInsReq p){
        return expenseService.insSamePrice(p);
    }

    //가계부 조회
    @GetMapping("select")
    @Operation(summary = "가계부 조회", description = "입력된 금액 1줄 보기")
    public ResponseEntity<?> selectExpenses(@RequestParam("de_id") long deId, @RequestParam("trip_id") long tripId){
        return expenseService.selectExpenses(deId,tripId);
    }

    //결제할 인원 가져오기
    @GetMapping("trip_user")
    @Operation(summary = "결제할 인원가져오기", description = "de_id가 null이 아니라면 수정화면에서 호출하는것")
    public ResponseEntity<?> exceptedMember(@RequestParam (value = "de_id", required = false) Long deId,@RequestParam ("trip_id") long tripId){
        return expenseService.getTripUser(deId,tripId);
    }

    //가계부 수정
    @PutMapping
    @Operation(summary = "가계부 수정", description = "금액 혹은 인원수정")
    public ResponseEntity<?> updateExpenses(@RequestBody ExpensesUpdReq p){
        return expenseService.updateExpenses(p);
    }

    //가계부 삭제
    @DeleteMapping
    @Operation(summary = "가계부 삭제", description = "가계부 삭제, 본인만 삭제 가능")
    public ResponseEntity<?> delExpenses(@RequestBody ExpenseDelReq p){
        return expenseService.delExpenses(p);
    }
}
