package edu.ntut.user.myhw3;

import android.widget.RadioGroup;

/**
 * Created by user on 2018/3/20.
 */

public class MarriageSuggestion {

    public String getSuggestion(String strSex, int iAgeRange) {

        String strSug = "建議: ";

        if (strSex.equals("male")) {
            switch (iAgeRange) {
                case 1:
                    strSug += "還不急";
                    break;
                case 2:
                    strSug += "開始找對象";
                    break;
                case 3:
                    strSug += "趕快結婚";
                    break;
            }
        } else {
            switch (iAgeRange) {
                case 1:
                    strSug += "還不急";
                    break;
                case 2:
                    strSug += "開始找對象";
                    break;
                case 3:
                    strSug += "趕快結婚";
                    break;
            }
        }

        return strSug;
    }
}
