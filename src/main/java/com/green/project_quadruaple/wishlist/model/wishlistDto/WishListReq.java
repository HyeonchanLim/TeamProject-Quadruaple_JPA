package com.green.project_quadruaple.wishlist.model.wishlistDto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListReq {

    private long strfId;

    // Getter 및 Setter
    public long getStrfId() {
        return strfId;
    }

    public void setStrfId(long strfId) {
        this.strfId = strfId;
    }
}