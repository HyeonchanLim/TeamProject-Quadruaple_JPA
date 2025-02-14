package com.green.project_quadruaple.tripreview.model;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class StringArrayTypeHandler extends BaseTypeHandler<List<String>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        // List<String>을 쉼표로 구분된 하나의 String으로 변환하여 설정
        ps.setString(i, String.join(",", parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        if (result != null && !result.isEmpty()) {
            // 쉼표로 구분된 문자열을 List<String>으로 변환
            return Arrays.asList(result.split(","));
        }
        return null;
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        if (result != null && !result.isEmpty()) {
            // 쉼표로 구분된 문자열을 List<String>으로 변환
            return Arrays.asList(result.split(","));
        }
        return null;
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        if (result != null && !result.isEmpty()) {
            // 쉼표로 구분된 문자열을 List<String>으로 변환
            return Arrays.asList(result.split(","));
        }
        return null;
    }
}