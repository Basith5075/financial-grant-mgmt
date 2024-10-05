package com.fingrant.FinanceMgmtGrant.util;

import com.shared.token.Tokenization;

public class Util {

    public static String tokenize(String input){
        return Tokenization.obfuscate(input);
    }

}