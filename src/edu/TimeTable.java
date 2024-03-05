package edu;

public class TimeTable {
    private boolean[][][] table = new boolean[7][24][2];
    private String[] days = new String[]{"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
    public boolean canAdd(TimeTable timeTable) {
        boolean[][][] table = timeTable.getTable();
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 24; j++)
                for (int k = 0; k < 2; k++)
                    if (this.table[i][j][k] && table[i][j][k])
                        return false;
        return true;
    }
    private int getDay(String day) {
        day = day.toLowerCase();
        for (int i = 0; i < days.length; i++)
            if (day.equals(days[i]))
                return i;
        return -1;
    }
    private String getDay(int day) {
        return days[day];
    }
    public void setCell(int i, int j, int k, boolean b) {
        try {
            table[i][j][k] = b;
        } catch (Exception e) {
            System.out.println("out of bound in timeTable");
        }
    }
    public boolean getCell(int i, int j, int k) {
        try {
            return table[i][j][k];
        } catch (Exception e) {
            System.out.println("out of bound in timeTable");
            return false;
        }
    }
    public boolean addInterval(String day1, int hour1, int minute1, String day2, int hour2, int minute2) {
        TimeTable timeTable = new TimeTable();
        boolean[][][] table = timeTable.getTable();
        int day = getDay(day1);
        int goal = getDay(day2);
        do {
            table[day][hour1][minute1] = true;
            if (minute1 < 1) {
                minute1++;
                continue;
            }
            if (hour1 < 23) {
                hour1++;
                minute1 = 0;
                continue;
            }
            day = (day + 1) % 7;
            hour1 = 0;
            minute1 = 0;
        } while (day != goal || hour1 != hour2 || minute1 != minute2);
        return addTable(timeTable);
    }
    public boolean addTable(TimeTable timeTable) {
        if (!canAdd(timeTable))
            return false;
        boolean[][][] table = timeTable.getTable();
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 24; j++)
                for (int k = 0; k < 2; k++)
                    this.table[i][j][k] = this.table[i][j][k] | table[i][j][k];
        return true;
    }
    public void removeTable(TimeTable timeTable) {
        boolean[][][] table = timeTable.getTable();
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 24; j++)
                for (int k = 0; k < 2; k++)
                    this.table[i][j][k] = table[i][j][k]? false: this.table[i][j][k];
    }
    public boolean[][][] getTable() {
        return table;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++)
                for (int k = 0; k < 2; k++)
                    result += (table[i][j][k]? "1": "0") + " ";
            result += "\n";
        }
        return result;
    }
    public void writeForUser() {
        System.out.print(" ".repeat(9));
        for (int i = 7;  i < 20; i++) {
            System.out.printf("%2d:00 |%2d:30 |", i, i);
        }
        System.out.println();
        for (int day = 0; day < 7; day++) {
            System.out.printf("%9s", getDay(day));
            for (int i = 7; i < 20; i++) {
                for (int j = 0; j < 2; j++)
                    System.out.printf("%6s|", (table[day][i][j]? "■".repeat(6): " "));
            }
            System.out.println();
        }
    }
}
