package com.green.project_quadruaple.wishlist.model.wishlistDto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListReq {

    private String strfId;

    // Getter 및 Setter
    public String getStrfId() {
        return strfId;
    }

    public void setStrfId(String strfId) {
        this.strfId = strfId;
    }
}