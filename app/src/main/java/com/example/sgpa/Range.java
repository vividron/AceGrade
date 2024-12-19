package com.example.sgpa;

public class Range {
    public static class sub {

        static int getMarksFor100(int n , int passing) {
            if (n == 4) {
                if (passing > 40) return getPassing(passing, 40);
                else return 40;
            } else if (n == 5) {
                if (passing > 45) return getPassing(passing, 45);
                else return 45;
            } else if (n == 6) {
                if (passing > 50) return getPassing(passing, 50);
                else return 50;
            } else if (n == 7) {
                if (passing > 60) return getPassing(passing, 60);
                else return 60;
            } else if (n == 8) {
                if (passing > 70) return getPassing(passing, 70);
                else return 70;
            } else if (n == 9) {
                if (passing > 75) return getPassing(passing, 75);
                else return 75;
            } else if (n == 10) {
                if (passing > 80) return getPassing(passing, 80);
                else return 80;
            }
            return 0;
        }

        static int getMultiplierFor100(int marks){
            if (marks >= 40 && marks <= 44) {
                return 4;
            } else if (marks >= 45 && marks <= 49) {
                return 5;
            } else if (marks >= 50 && marks <= 59) {
                return 6;
            } else if (marks >= 60 && marks <= 69) {
                return 7;
            } else if (marks >= 70 && marks <= 74) {
                return 8;
            } else if (marks >= 75 && marks <= 79) {
                return 9;
            } else if (marks >= 80 && marks <= 100) {
                return 10;
            }
            return 0;
        }

        static int getMarksFor75(int n , int passing) {
            if (n == 4) {
                if (passing > 30) return getPassing(passing, 30);
                else return 30;
            } else if (n == 5) {
                if (passing > 34) return getPassing(passing, 34);
                else return 34;
            } else if (n == 6) {
                if (passing > 37) return getPassing(passing, 37);
                else return 37;
            } else if (n == 7) {
                if (passing > 45) return getPassing(passing, 45);
                else return 45;
            } else if (n == 8) {
                if (passing > 53) return getPassing(passing, 53);
                else return 53;
            } else if (n == 9) {
                if (passing > 57) return getPassing(passing, 57);
                else return 57;
            } else if (n == 10) {
                if (passing > 60) return getPassing(passing, 60);
                else return 60;
            }
            return 0;
        }
        static int getMultiplierFor75(int marks) {

            if (marks >= 30 && marks <= 33) {
                return 4;
            } else if (marks >= 34 && marks <= 36) {
                return 5;
            } else if (marks >= 37 && marks <= 44) {
                return 6;
            } else if (marks >= 45 && marks <= 52) {
                return 7;
            } else if (marks >= 53 && marks <= 56) {
                return 8;
            } else if (marks >= 57 && marks <= 59) {
                return 9;
            } else if (marks >= 60 && marks <= 75) {
                return 10;
            }
            return 0;
        }
        static int getPassing(int pssMrk , int mrk){
            while (mrk!=pssMrk) mrk++;
            return mrk;
        }
        static float subTwGetCredit(int opt, float mul) {
            if (opt==0) { // just pass
                return mul * 4;
            }else if (opt==1) { // avg
                return mul * 6;
            } else if (opt==2) {
                return mul * 8;
            } else if (opt==3) {
                return mul * 9;
            } else if (opt==4) {
                return mul * 10;
            }
            return mul*5;
        }
    }
}
