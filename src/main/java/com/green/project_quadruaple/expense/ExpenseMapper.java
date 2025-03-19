package com.green.project_quadruaple.expense;

import com.green.project_quadruaple.entity.model.TripUser;
import com.green.project_quadruaple.expense.model.dto.DeDto;
import com.green.project_quadruaple.expense.model.dto.ExpenseDto;
import com.green.project_quadruaple.expense.model.res.ExpenseOneRes;
import com.green.project_quadruaple.expense.model.res.ExpensesRes;
import com.green.project_quadruaple.expense.model.res.TripInUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExpenseMapper {
    //가계부 insert
    int insPaid (Map<String, Object> paramMap);

    //가계부 한줄 보기
    ExpenseOneRes selExpenseOne(long deId);

    //결제인원 정보 가져오기
    List<TripInUserInfo> getTripUser(long tripId, Long deId);
}
