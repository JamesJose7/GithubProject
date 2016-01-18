package com.jose.githubproject.model;

/**
 * Created by Jose on 1/11/2016.
 */
public class MyDateFormatter {

    private final String[] MONTHS = {
            "Jan",
            "Feb",
            "March",
            "Apr",
            "May",
            "June",
            "July",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
    };

    private String mDate;

    public MyDateFormatter(String date) {
        // 2009-03-02T18:37:04Z
        // YYYY-MM-dd
        //Get the according strings
        String[] splitDate = date.split("T");
        date = splitDate[0];

        splitDate = date.split("-");
        String year = splitDate[0];
        String month = splitDate[1];
        String day = splitDate[2];

        date = String.format("Joined on %s %s %s",
                day,
                getMonth(month),
                year);

        mDate = date;
    }

    private String getMonth(String monthNumber) {
        String month;
        switch (monthNumber) {
            case "01":
                month =  MONTHS[0];
                break;
            case "02":
                month = MONTHS[1];
                break;
            case "03":
                month = MONTHS[2];
                break;
            case "04":
                month = MONTHS[3];
                break;
            case "05":
                month = MONTHS[4];
                break;
            case "06":
                month = MONTHS[5];
                break;
            case "07":
                month = MONTHS[6];
                break;
            case "08":
                month = MONTHS[7];
                break;
            case "09":
                month = MONTHS[8];
                break;
            case "10":
                month = MONTHS[9];
                break;
            case "11":
                month = MONTHS[10];
                break;
            case "12":
                month = MONTHS[11];
                break;
            default:
                month = "";
        }

        return month;
    }

    public String getDate() {
        return mDate;
    }
}
