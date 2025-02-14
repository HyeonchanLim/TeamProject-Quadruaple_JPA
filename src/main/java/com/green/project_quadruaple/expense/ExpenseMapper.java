package com.green.project_quadruaple.expense;

import com.green.project_quadruaple.expense.model.dto.DeDto;
import com.green.project_quadruaple.expense.model.dto.DutchPaidUserDto;
import com.green.project_quadruaple.expense.model.dto.PaidUser;
import com.green.project_quadruaple.expense.model.req.DutchReq;
import com.green.project_quadruaple.expense.model.res.ExpenseOneRes;
import com.green.project_quadruaple.expense.model.res.ExpensesRes;
import com.green.project_quadruaple.expense.model.res.TripUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExpenseMapper {
    //가계부 개설
    void insDe(DeDto d);
    //가계부 insert
    int insPaid (Map<String, Object> paramMap);

    //가계부 보기
    ExpensesRes getExpenses(long tripId, long userId);

    //가계부 한줄 보기
    ExpenseOneRes selExpenseOne(long deId);

    //참여유저인지 확인하기
    boolean IsUserInTrip(long tripId, long userId);

    //결제인원 정보 가져오기
    List<TripUser> getTripUser(long tripId, Long deId);

    //가계부 삭제
    int delExpenses(long deId);
    //paidUser 삭제
    int delPaidUser(long deId);

    //사용목적 수정
    int udpFor(String paidFor, long deId);
}
