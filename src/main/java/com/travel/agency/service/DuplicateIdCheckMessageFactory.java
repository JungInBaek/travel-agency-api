package com.travel.agency.service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DuplicateIdCheckMessageFactory {

    private final static Map<Boolean, Function<Boolean, Map<String, String>>> map = new HashMap<>();

    static {
        map.put(true, (Boolean result) -> {
            Map<String, String> map = new HashMap<>();
            map.put("message", "사용 가능한 아이디입니다");
            return map;
        });
        map.put(false, (Boolean result) -> {
            Map<String, String> map = new HashMap<>();
            map.put("message", "중복된 아이디입니다");
            return map;
        });
    }

    public static Map<String, String> getMessage(Boolean result) {
        Function<Boolean, Map<String, String>> messageFunction = map.get(result);
        return messageFunction.apply(result);
    }

}
