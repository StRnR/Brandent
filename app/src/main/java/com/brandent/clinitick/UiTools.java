package com.brandent.clinitick;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UiTools {

    public static void closeKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String priceToString(long amount, boolean showToman) {
        String tmp = String.valueOf(amount);
        StringBuilder stringBuilder = new StringBuilder(tmp);
        int index = 3;
        while (index < tmp.length()) {
            stringBuilder.insert(tmp.length() - index, ",");
            index += 3;
        }
        if (showToman) {
            stringBuilder.append(" تومان");
        }
        return stringBuilder.toString();
    }

    public static Long stringToPrice(String string) {
        long amount = 0;
        for (char ch : string.toCharArray()) {
            if ((int) ch <= 57 && (int) ch >= 48) {
                amount = amount * 10 + ((int) ch) - 48;
            }
        }
        return amount;
    }
}
