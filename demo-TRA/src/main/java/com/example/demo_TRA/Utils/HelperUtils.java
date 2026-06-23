package com.example.demo_TRA.Utils;

import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Random;

public class HelperUtils {

    private static final Random RANDOM = new Random();

    // Generate Code
    public static String generateCode(String prefix) {
        return generateCode(prefix, 4);
    }

    public static String generateCode(String prefix, int length) {
        StringBuilder code = new StringBuilder(prefix + "-");

        for (int i = 0; i < length; i++) {
            code.append(RANDOM.nextInt(10));
        }

        return code.toString();
    }

    // Calculate Distance (KM)
    public static double calculateDistance(
            double lat1,
            double lng1,
            double lat2,
            double lng2) {

        final int EARTH_RADIUS = 6371; // km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2)
                * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    // Calculate Total
    public static double calculateTotal(double subtotal, double fee) {
        return subtotal + fee;
    }

    public static double calculateTotal(
            double subtotal,
            double fee,
            double discount) {

        return subtotal + fee - discount;
    }


    // Format Currency
    public static String formatCurrency(double amount) {
        NumberFormat formatter =
                NumberFormat.getCurrencyInstance(Locale.US);

        return formatter.format(amount);
    }

    public static String formatCurrency(
            double amount,
            String currencyCode) {

        return String.format("%.2f %s", amount, currencyCode);
    }

    // Business Hours
    public static boolean isBusinessOpen(
            String openTime,
            String closeTime) {

        LocalTime open = LocalTime.parse(openTime);
        LocalTime close = LocalTime.parse(closeTime);
        LocalTime now = LocalTime.now();

        return !now.isBefore(open) && !now.isAfter(close);
    }
}
