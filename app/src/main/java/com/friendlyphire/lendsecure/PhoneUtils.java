package com.friendlyphire.lendsecure;

import android.content.Context;
import android.telephony.TelephonyManager;

public abstract class PhoneUtils {

    /*
    For use with other applications, this method will need to be adjusted to check for other types of wrongly formatted
    phone numbers.
    @pre number is not null
     */
    public static boolean checkValid(String number){
        number=number.trim();
        if(number.startsWith("++")||number.startsWith("000")||number.startsWith("00+")||number.startsWith("+00")||number.startsWith("*")||number.startsWith("#"))
            return false;
        else return true;
    }

    /*
    @pre: context and number are valid
     */
    public static boolean checkForeign(Context context, String number){
        if(!hasCountryCode(number))
            return false;
        String ownCC = getCountryZipCode(context);
        String[] splitNumber = splitNumber(context,number);
        if(inEU(context,ownCC)){
            if(inEU(context,splitNumber[0]))
                return false;
            else return true;
        }
        else
            return !ownCC.equals(splitNumber[0]);

    }

    /*
    @pre: number is a valid phone number
    */
    public static boolean hasCountryCode(String number){
        return number.startsWith("+")||number.startsWith("00");
    }

    /*
    @pre: number is a valid phone number and context is a valid context
    Split the given phone number off from its country code
     */
    public static String[] splitNumber(Context context, String number){
        String[] splitNumber = null;
        if(!hasCountryCode(number)){
            splitNumber=new String[2];
            splitNumber[0]=getCountryZipCode(context);
            splitNumber[1]=number;
        }
        else{
            if(number.startsWith("+"))
                number=number.substring(1);
            else if(number.startsWith("00"))
                number=number.substring(2);
            String[] rl=context.getResources().getStringArray(R.array.CountryCodes);
            for(int i=0;i<rl.length;i++){
                String[] splitArray = rl[i].split(",");
                if(splitArray[0].equals(number.substring(0,splitArray[0].length()))){
                    splitNumber=new String[2];
                    splitNumber[0] = number.substring(0, splitArray[0].length());
                    splitNumber[1] = number.substring(splitArray[0].length(),number.length());
                }
            }
        }
        return splitNumber;
    }

    /*
    @pre: context and OwnCC are valid
     */
    public static boolean inEU(Context context,String ownCC) {
        String[] rl=context.getResources().getStringArray(R.array.EUCodes);
        for(int i=0;i<rl.length;i++){
            if(rl[i].equals(ownCC))
                return true;
        }
        return false;
    }

    /*
    @pre: context and number are valid
     */
    public static boolean checkPremium(Context context, String number){
        String[] splitNumber=splitNumber(context,number);
        String[] rl=context.getResources().getStringArray(R.array.CountryCodes);
        String[] splitRL;
        for(int i=0;i<rl.length;i++){
            splitRL = rl[i].split(",");
            if(splitRL[0].equals(splitNumber[0])&&splitRL.length>=3){
                String[] PNArray = splitRL[2].split(":");
                for(int j=0;j<PNArray.length;j++)
                    if(PNArray[j].equals(splitNumber[1].substring(0,splitNumber[1].length()>=
                            PNArray[j].length()?PNArray[j].length():splitNumber[1].length())))
                        return true;
            }
        }
        return false;
    }

    /*
    @pre: context and number are valid
     */
    public static boolean checkPremiumSMS(Context context, String number){
        return number.length()<=6||PhoneUtils.checkForeign(context,number);
    }

    /*
    @pre: context is valid
    Return the country code of this device's simcard
     */
    public static String getCountryZipCode(Context context){
        String CountryZipCode="";
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String CountryID= manager.getSimCountryIso().toUpperCase();
        String[] rl=context.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }
}