package org.verfallen.bestbusstopintheworld.util;

import java.math.BigDecimal;

public class CommonUtil {

    public static String conversionDistanse( int aDistanceMeter ) {
        return ( aDistanceMeter >= 1000 ) ? Math.round( ( new BigDecimal( String.valueOf( aDistanceMeter / 1000 ) )
                .setScale( 1, BigDecimal.ROUND_HALF_UP ).longValue() ) ) + "km" : aDistanceMeter + "m";
    }
}
