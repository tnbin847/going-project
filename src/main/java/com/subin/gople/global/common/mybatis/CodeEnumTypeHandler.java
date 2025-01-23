package com.subin.gople.global.common.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link CodeEnum}인터페이스를 구현한 {@link Enum}클래스에 정의된 코드값을 데이터베이스에 저장하거나 데이터베이스로부터 가져온 코드값을
 * {@link Enum}으로 변환하기 위해 매핑 처리를 수행하는 타입 핸들러
 *
 * @param <E>   타입 핸들러의 적용 대상인 {@link Enum}
 */

@MappedTypes(CodeEnum.class)
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    private final E[] constants;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) { throw new IllegalArgumentException("Type argument cannot be null."); }
        this.type = type;
        this.constants = type.getEnumConstants();
        if (!type.isInterface() && this.constants == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " dose not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertColumnCodeToCodeEnum(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertColumnCodeToCodeEnum(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertColumnCodeToCodeEnum(cs.getString(columnIndex));
    }

    /**
     * 데이터베이스로부터 가져온 코드값과 일치한 상수값을 가진 {@link Enum}을 반환한다.
     *
     * @param columnCode    데이터베이스로부터 가져온 코드값
     * @return              코드값과 상수값이 일치하다면 해당 {@link Enum}을, 일치한 상수값이 없다면 {@code null}을 반환
     */
    private E convertColumnCodeToCodeEnum(final String columnCode) {
        try {
            for (E codeEnum : constants) {
                if (codeEnum.getCode().equals(columnCode)) {
                    return codeEnum;
                }
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert " + columnCode + " to " + type.getSimpleName(), e);
        }
    }
}