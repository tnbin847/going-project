package com.subin.gople.global.common.mybatis;

/**
 * 타입 핸들러를 통해 코드값을 처리하고자 하는 {@link Enum}클래스들을 일관적으로 구현하기 위해 정의한 인터페이스
 * <p>
 *     코드값을 데이터베이스 처리하고자 하는 {@link Enum}클래스는 타입 핸들러를 통해 데이터베이스에 저장하기 위해서는
 *     해당 인터페이스를 구현해야 한다.
 * </p>
 *
 * @see CodeEnumTypeHandler
 */

public interface CodeEnum {

    /**
     * 데이터베이스에 저장하고자 하는 코드값을 반환한다.
     */
    String getCode();

    /**
     * 뷰에 출력할 코드값을 매핑한다.
     */
    String getLabel();
}