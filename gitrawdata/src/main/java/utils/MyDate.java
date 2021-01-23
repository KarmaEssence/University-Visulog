package utils;

/**
 * @version 1.0
 */
public class MyDate implements Comparable {
    public final String zone;
    public Month month;
    private WeekDay weekDay;
    public final int day, hrs, min, sec, year;

    /**
     * we enumerate all the months of a year
     */
    public enum Month {
        Jan(31), Feb(28), Mar(31), Apr(30), May(31), Jun(30), Jul(31), Aug(31), Sep(30), Oct(31), Nov(30), Dec(31);

        public final int value;

        /**
         * we create a Month 
         * @param i the number of days in a month
         */
        Month(int i) {
            value = i;
        }
    }

    /**
     * we enumerate all the days of a week
     */
    private enum WeekDay {
        Mon, Tue, Wed, Thu, Fri, Sat, Sun
    }

    /**
     * the constructor of a MyDate type Object
     * @param str String containing all the caracteristics of a MyDate type Object
     */
    public MyDate(String str) {
        String[] params = str.split(" ");
        this.weekDay = WeekDay.valueOf(params[0]);
        this.month = Month.valueOf(params[1]);
        this.day = Integer.parseInt(params[2]);
        String[] clock = params[3].split(":");
        this.hrs = Integer.parseInt(clock[0]);
        this.min = Integer.parseInt(clock[1]);
        this.sec = Integer.parseInt(clock[2]);
        this.year = Integer.parseInt(params[4]);
        this.zone = params[5];
    }

    /**
     * this method counts how much months there is between two MyDates
     * @param date the second MyDate
     * @return int 
     */
    public int countMonthsBetween(MyDate date) {
        int res = 0;
        MyDate d1, d2;
        if (this.compareTo(date) < 0) {
            d1 = this;
            d2 = date;
        } else if (this.compareTo(date) > 0) {
            d1 = date;
            d2 = this;
        } else return 1;
        if (d2.month.ordinal() > d1.month.ordinal()) {
            res += (d2.year - d1.year) * 12;
            res += d2.month.ordinal() - d1.month.ordinal() + 1;
        } else if (d2.month.ordinal() < d1.month.ordinal()) {
            res += (d2.year - d1.year - 1) * 12;
            res += 12 - d1.month.ordinal() + d2.month.ordinal() + 1;
        } else {
            res += (d2.year - d1.year) * 12 + 1;
        }
        return res;
    }

    /**
     * this method counts how much days there is between two MyDate
     * @param date the second MyDate
     * @return int 
     */
    public int countDaysBetween(MyDate date) {
        int res = 0;
        MyDate d1, d2;
        if (this.compareTo(date) < 0) {
            d1 = this;
            d2 = date;
        } else if (this.compareTo(date) > 0) {
            d1 = date;
            d2 = this;
        } else return 0;
        Month[] months = Month.values();
        if (d1.month.ordinal() == d2.month.ordinal()) {
            res += d2.day - d1.day;
            res += (d2.year - d1.year) * 365;
        } else if (d2.month.ordinal() > d1.month.ordinal()) {
            res += (d2.year - d1.year) * 365;
            for (int i = d1.month.ordinal() + 1; i < d2.month.ordinal(); i++) {
                res += months[i].value;
            }
            res += d2.day;
            res += d1.month.value - d1.day;
        } else {
            res += (d2.year - d1.year - 1) * 365;
            for (int i = d1.month.ordinal() + 1; i < 12; i++) {
                res += months[i].value;
            }
            for (int i = 0; i < d2.month.ordinal(); i++) {
                res += months[i].value;
            }
            res += d2.day;
            res += d1.month.value - d1.day;
        }

        return res;
    }

    @Override
    /**
     * the usual toString() method
     * @return String the text description of a MyDate
     */
    public String toString() {
        String clock = String.format("%02d:%02d:%02d", hrs, min, sec);
        return weekDay + " " + month + " " + day + " " + clock + " " + year + " " + zone;
    }

    @Override
    /**
     * compares two MyDates
     * @param o the second MyDate
     * @return int 
     */
    public int compareTo(Object o) {
        MyDate date = (MyDate) o;
        if (this.year < date.year)
            return -1;
        if (this.year > date.year)
            return 1;
        if (this.month.ordinal() < date.month.ordinal())
            return -1;
        if (this.month.ordinal() > date.month.ordinal())
            return 1;
        if (this.day < date.day)
            return -1;
        if (this.day > date.day)
            return 1;
        if (this.hrs < date.hrs)
            return -1;
        if (this.hrs > date.hrs)
            return 1;
        if (this.min < date.min)
            return -1;
        if (this.min > date.min)
            return 1;
        return Integer.compare(this.sec, date.sec);
    }


}