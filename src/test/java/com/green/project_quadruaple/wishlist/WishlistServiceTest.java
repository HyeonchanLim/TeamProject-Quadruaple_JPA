//package com.green.project_quadruaple.wishlist;
//
//import com.green.project_quadruaple.common.config.jwt.JwtTokenProvider;
//import com.green.project_quadruaple.common.config.jwt.JwtUser;
//import com.green.project_quadruaple.common.config.jwt.UserRole;
//import com.green.project_quadruaple.wishlist.WishlistMapper;
//import com.green.project_quadruaple.wishlist.model.wishlistDto.WishListReq;
//import com.green.project_quadruaple.wishlist.model.wishlistDto.WishListRes;
//import jakarta.servlet.http.HttpServletRequest;
//import org.assertj.core.util.Arrays;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.stubbing.OngoingStubbing;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class WishlistServiceTest {
//    @Mock
//    private WishlistMapper wishlistMapper;
//
//    @Mock
//    private JwtTokenProvider jwtTokenProvider;
//
//    @InjectMocks
//    private WishlistService wishlistService;
//
//    @Test
//    void addWishList() {
//        // Given
//        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
//        WishListReq wishListReq = new WishListReq(1L, 123L); // userId와 strfId 초기화
//
//        String mockToken = "mockToken"; // Mock JWT 토큰
//        long mockUserId = 1L;           // Mock 사용자 ID
//
//        // Mock 객체 동작 정의
//        when(jwtTokenProvider.resolveToken(request)).thenReturn(mockToken);
//        OngoingStubbing<JwtUser> roleUser = when(jwtTokenProvider.getJwtUserFromToken(mockToken))
//                .thenReturn(new JwtUser(mockUserId, List.of()));// 변환 후 해결
//
//        // When
//        wishlistService.addWishList(wishListReq);
//
//        // Then
//        verify(jwtTokenProvider).resolveToken(request);
//        verify(jwtTokenProvider).getJwtUserFromToken(mockToken);
//        verify(wishlistMapper).insertWishList(mockUserId, wishListReq.getStrfId());
//    }
//    @Test
//    void getWishList() {
//        // Given
//        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
//        String mockToken = "mockToken";
//        long mockUserId = 1L;
//
//        List<Object> mockResponse = Arrays.asList(
//                new WishListRes(1L, mockUserId, 123L, "Mock Item 1", "2025-01-01T00:00:00")
//
//        );
//        when(jwtTokenProvider.resolveToken(request)).thenReturn(mockToken);
//        when(jwtTokenProvider.getJwtUserFromToken(mockToken))
//                .thenReturn(new JwtUser(mockUserId, List.of(UserRole.ROLE_USER)));
//        when(wishlistMapper.selectWishListByUserId(mockUserId)).thenReturn(mockResponse);
//
//        // When
//        List<WishListRes> result = wishlistService.getWishList();
//
//        // Then
//        assertEquals(mockResponse, result);
//        verify(jwtTokenProvider).resolveToken(request);
//        verify(jwtTokenProvider).getJwtUserFromToken(mockToken);
//        verify(wishlistMapper).selectWishListByUserId(mockUserId);
//    }
//}
//
