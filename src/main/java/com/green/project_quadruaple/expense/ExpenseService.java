package com.green.project_quadruaple.expense;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.expense.model.dto.DeDto;
import com.green.project_quadruaple.expense.model.dto.PaidUser;
import com.green.project_quadruaple.expense.model.req.*;
import com.green.project_quadruaple.expense.model.res.ExpenseOneRes;
import com.green.project_quadruaple.expense.model.res.ExpensesRes;
import com.green.project_quadruaple.expense.model.res.TripUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseMapper expenseMapper;
    private final AuthenticationFacade authenticationFacade;

    //trip 참여객 체크
    boolean isUserJoinTrip(long tripId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return !(principal instanceof JwtUser) ||!expenseMapper.IsUserInTrip(tripId,authenticationFacade.getSignedUserId());
    }

    //추가하기
    @Transactional
    public ResponseEntity<ResponseWrapper<Long>> insSamePrice(ExpenseInsReq p){
        if(isUserJoinTrip(p.getTripId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        DeDto d=new DeDto(null, p.getPaidFor());
        expenseMapper.insDe(d);
        Long deId=d.getDeId();
        if(deId==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));}
        int result=insPaidUsers(p.getPaidUserList(),deId,p.getTripId(),p.getTotalPrice());
        if(result==0){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(),deId));
    }
    //paidUsers에 입력하기
    int insPaidUsers(List<Long> userList, long deId, long tripId, int totalPrice){
        List<Map<String, Object>> userPaid = new ArrayList<>(userList.size());
        int price = (int) (Math.round((double) totalPrice / userList.size() / 10) * 10);
        Integer randomNum=null;
        if(price*userList.size()!=totalPrice){
            Random r=new Random();
            randomNum=r.nextInt(userList.size());
        }
        for(int i=0;i<userList.size();i++){
            Map<String, Object> map=new HashMap<>();
            if(randomNum != null && randomNum==i){
                map.put("price", price+totalPrice-price*userList.size());
            }else {
                map.put("price",price);
            }
            map.put("userId",userList.get(i));
            userPaid.add(map);
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("deId", deId);
        paramMap.put("tripId", tripId);
        paramMap.put("userPaid", userPaid);
        return expenseMapper.insPaid(paramMap);
    }

    //가계부 수정
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> updateExpenses(ExpensesUpdReq p){
        if(isUserJoinTrip(p.getTripId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        int delRes= expenseMapper.delPaidUser(p.getDeId());
        if(delRes==0){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        int result=insPaidUsers(p.getPaidUserList(),p.getDeId(),p.getTripId(),p.getTotalPrice());
        if(result==0){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        if(p.getPaidFor()!=null){
            int updRes=expenseMapper.udpFor(p.getPaidFor(),p.getDeId());
            if(updRes==0){
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
            }
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(),result));
    }

    //이 결제에서 제외된 인원보기
    public ResponseEntity<ResponseWrapper<List<TripUser>>> getTripUser(Long deId, long tripId){
        if(isUserJoinTrip(tripId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        List<TripUser> tripUser=expenseMapper.getTripUser(tripId, deId);
        if(tripUser==null){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(),tripUser));
    }


    //가계부 보기
    public ResponseEntity<ResponseWrapper<ExpensesRes>> getExpenses(long tripId){
        if(isUserJoinTrip(tripId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        long userId=authenticationFacade.getSignedUserId();
        ExpensesRes result= expenseMapper.getExpenses(tripId,userId);
        if(result==null){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(),result));
    }

    //가계부 한줄 보기
    public ResponseEntity<ResponseWrapper<ExpenseOneRes>> selectExpenses(long deId, long tripId){
        if(isUserJoinTrip(tripId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        ExpenseOneRes res=expenseMapper.selExpenseOne(deId);
        if(res==null){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), res));
    }

    //가계부 삭제하기
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> delExpenses(ExpenseDelReq p){
        if(isUserJoinTrip(p.getTripId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        int respu=expenseMapper.delPaidUser(p.getDeId());
        int resep=expenseMapper.delExpenses(p.getDeId());
        if(resep+respu==0){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), resep+respu));
    }
}
