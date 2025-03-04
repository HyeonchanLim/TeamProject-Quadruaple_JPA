package com.green.project_quadruaple.common.config.enumdata;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.core.parameters.P;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        if(userRole == null) {
            return "";
        }
        return userRole.getValue();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbEnum) {
        if(dbEnum == null) {
            return null;
        }
        return UserRole.getByValue(dbEnum);
    }
}
