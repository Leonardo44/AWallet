package com.llopez.awallet.utilities;

public class Validations {

    public static boolean IsValidString(String textChain){
        boolean isValid = false;

        try{
            if(!textChain.equals("")){
                isValid = true;
            }
        } catch (Exception ex){ }

        return isValid;
    }

    public static boolean IsNumeric(String textChain){
        boolean isValid = false;

        try{
            boolean isValidNumber = true;
            for (int i = 0; i < textChain.length(); i++){
                if(!Character.isDigit(textChain.charAt(i))){
                    isValidNumber = false;
                    break;
                }
            }

            if(isValidNumber){
                isValid = true;
            }
        } catch (Exception ex){ }

        return isValid;
    }

    public static boolean IsNumberGreaterThan(String textChain, Integer number){
        boolean isValid = false;

        try{
            if(IsNumeric(textChain)){

                if(Double.parseDouble(textChain) > number){
                    isValid = true;
                }
            }
        } catch (Exception ex){ }

        return isValid;
    }
}
