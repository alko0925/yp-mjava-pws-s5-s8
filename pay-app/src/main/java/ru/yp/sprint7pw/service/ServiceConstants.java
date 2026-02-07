package ru.yp.sprint7pw.service;

import java.util.Arrays;

public class ServiceConstants {
    public enum OperationType {
        DEPOSIT, PAYMENT;

        public static boolean isEnumContains(String str) {
            return Arrays.stream(values()).anyMatch(e -> e.name().equals(str));
        }
    }
}
