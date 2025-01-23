package com.subin.gople.global.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 상태 여부 컬럼 값을 처리하기 위해 서로 다른 타입의 값들을 상응되는 의미별로 정의한 {@link Enum}클래스
 */

@RequiredArgsConstructor
@Getter
public enum StatusFormats {

    YES (1, "Y", true),
    NO (0, "N", false);

    private final int numeric;

    private final String string;

    private final boolean bool;

    /**
     * 전달된 정수값에 해당하는 논리형의 상태값을 반환한다.
     *
     * @param value     논리형 상태값으로 변환할 정수값으로 보통 1 또는 0을 전달받는다.
     * @return          전달된 값이 1일 경우 {@code true}를, 0일 경우 {@code false}를 반환
     */
    public boolean toBoolean(int value) {
        return Arrays.stream(values())
                .filter(format -> format.getNumeric() == value)
                .findFirst()
                .map(StatusFormats::isBool)
                .orElseThrow(() -> new IllegalArgumentException("Cannot convert " + value + " to boolean."));
    }
}