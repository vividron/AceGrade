package com.example.sgpa;

public class Subjects {

    public static int[] utMrkRange = new int[6];
    public static int[] endSemRange = new int[6];
    public static int[] termWorkRange = new int[9];
    public static int[] subCredit = new int[6];
    public static float[] termWorkCredit = new float[9];
    public static int totalCredit;

    public static String[] getSubjects(String branch, String sem) {
        String[] sub5 = new String[5];
        String[] sub6 = new String[6];

        if (sem.equals("Semester 1") || sem.equals("Semester 2")) {
            if (sem.equals("Semester 1")) {
                sub6[0] = "Applied Mathematics-I";
                sub6[1] = "Applied Physics";
                sub6[2] = "Applied Chemistry";
                sub6[3] = "Engineering Mechanics";
                sub6[4] = "Basic Electrical Engineering";
                sub6[5] = "Professional and Communication Ethics";

                utMrkRange[0] = 40;
                utMrkRange[1] = 30;
                utMrkRange[2] = 30;
                utMrkRange[3] = 40;
                utMrkRange[4] = 40;
                utMrkRange[5] = 30;

                endSemRange[0] = 60;
                endSemRange[1] = 45;
                endSemRange[2] = 45;
                endSemRange[3] = 60;
                endSemRange[4] = 60;
                endSemRange[5] = 45;

                subCredit[0] = 2;
                subCredit[1] = 2;
                subCredit[2] = 2;
                subCredit[3] = 2;
                subCredit[4] = 3;
                subCredit[5] = 2;

                totalCredit = 21;

                return sub6;
            } else {
                sub5[0] = "Applied Mathematics-II";
                sub5[1] = "Elective Physics";
                sub5[2] = "Elective Chemistry";
                sub5[3] = "Engineering Graphics";
                sub5[4] = "Program Core Course";

                utMrkRange[0] = 40;
                utMrkRange[1] = 30;
                utMrkRange[2] = 30;
                utMrkRange[3] = 40;
                utMrkRange[4] = 40;

                endSemRange[0] = 60;
                endSemRange[1] = 45;
                endSemRange[2] = 45;
                endSemRange[3] = 60;
                endSemRange[4] = 60;

                subCredit[0] = 2;
                subCredit[1] = 2;
                subCredit[2] = 2;
                subCredit[3] = 3;
                subCredit[4] = 2;

                totalCredit = 22;

                return sub5;
            }
        } else {
            switch (branch) {
                case "Computer Science":
                    switch (sem) {
                        case "Semester 3":
                            sub5[0] = "Engineering Mathematics-III";
                            sub5[1] = "Discrete Structures and Graph Theory";
                            sub5[2] = "Data Structure";
                            sub5[3] = "Digital Logic & Computer Architecture";
                            sub5[4] = "Computer Graphics";

                            utMrkRange[0] = 20;
                            utMrkRange[1] = 20;
                            utMrkRange[2] = 20;
                            utMrkRange[3] = 20;
                            utMrkRange[4] = 20;

                            endSemRange[0] = 80;
                            endSemRange[1] = 80;
                            endSemRange[2] = 80;
                            endSemRange[3] = 80;
                            endSemRange[4] = 80;

                            subCredit[0] = 3;
                            subCredit[1] = 3;
                            subCredit[2] = 3;
                            subCredit[3] = 3;
                            subCredit[4] = 3;

                            totalCredit = 23;
                            return sub5;

                        case "Semester 4":
                            sub5[0] = "Engineering Mathematics-IV";
                            sub5[1] = "Analysis of Algorithm";
                            sub5[2] = "Database Management System";
                            sub5[3] = "Operating System";
                            sub5[4] = "Microprocessor";

                            utMrkRange[0] = 20;
                            utMrkRange[1] = 20;
                            utMrkRange[2] = 20;
                            utMrkRange[3] = 20;
                            utMrkRange[4] = 20;

                            endSemRange[0] = 80;
                            endSemRange[1] = 80;
                            endSemRange[2] = 80;
                            endSemRange[3] = 80;
                            endSemRange[4] = 80;

                            subCredit[0] = 3;
                            subCredit[1] = 3;
                            subCredit[2] = 3;
                            subCredit[3] = 3;
                            subCredit[4] = 3;

                            totalCredit = 24;
                            return sub5;
                    }
                    break;

                case "Information Technology":
                    switch (sem) {
                        case "Semester 3":
                            sub5[0] = "Engineering Mathematics-III";
                            sub5[1] = "Data Structure and Analysis";
                            sub5[2] = "Database Management System";
                            sub5[3] = "Principle of Communication";
                            sub5[4] = "Paradigms and Computer Programming Fundamentals";

                            utMrkRange[0] = 20;
                            utMrkRange[1] = 20;
                            utMrkRange[2] = 20;
                            utMrkRange[3] = 20;
                            utMrkRange[4] = 20;

                            endSemRange[0] = 80;
                            endSemRange[1] = 80;
                            endSemRange[2] = 80;
                            endSemRange[3] = 80;
                            endSemRange[4] = 80;

                            subCredit[0] = 3;
                            subCredit[1] = 3;
                            subCredit[2] = 3;
                            subCredit[3] = 3;
                            subCredit[4] = 3;

                            totalCredit = 23;
                            return sub5;

                        case "Semester 4":
                            sub5[0] = "Engineering Mathematics-IV";
                            sub5[1] = "Computer Network and Network Design";
                            sub5[2] = "Operating System";
                            sub5[3] = "Automata Theory";
                            sub5[4] = "Computer Organization and Architecture";

                            utMrkRange[0] = 20;
                            utMrkRange[1] = 20;
                            utMrkRange[2] = 20;
                            utMrkRange[3] = 20;
                            utMrkRange[4] = 20;

                            endSemRange[0] = 80;
                            endSemRange[1] = 80;
                            endSemRange[2] = 80;
                            endSemRange[3] = 80;
                            endSemRange[4] = 80;

                            subCredit[0] = 3;
                            subCredit[1] = 3;
                            subCredit[2] = 3;
                            subCredit[3] = 3;
                            subCredit[4] = 3;

                            totalCredit = 23;
                            return sub5;
                    }
                    break;

                case "Electrical":
                    switch (sem) {
                        case "Semester 3":
                            sub5[0] = "Engineering Mathematics-III";
                            sub5[1] = "Electrical Circuit Analysis";
                            sub5[2] = "Fundamentals of Electrical Machines & Measurements";
                            sub5[3] = "Electrical Power System I";
                            sub5[4] = "Analog Electronics";

                            utMrkRange[0] = 20;
                            utMrkRange[1] = 20;
                            utMrkRange[2] = 20;
                            utMrkRange[3] = 20;
                            utMrkRange[4] = 20;

                            endSemRange[0] = 80;
                            endSemRange[1] = 80;
                            endSemRange[2] = 80;
                            endSemRange[3] = 80;
                            endSemRange[4] = 80;

                            subCredit[0] = 3;
                            subCredit[1] = 3;
                            subCredit[2] = 4;
                            subCredit[3] = 3;
                            subCredit[4] = 3;

                            totalCredit = 24;
                            return sub5;

                        case "Semester 4":
                            sub5[0] = "Engineering Mathematics-IV";
                            sub5[1] = "Electrical AC Machines-I";
                            sub5[2] = "Digital Electronics";
                            sub5[3] = "Power Electronic Devices and Circuits";
                            sub5[4] = "Electric and Hybrid Electric Vehicles";

                            utMrkRange[0] = 20;
                            utMrkRange[1] = 20;
                            utMrkRange[2] = 20;
                            utMrkRange[3] = 20;
                            utMrkRange[4] = 20;

                            endSemRange[0] = 80;
                            endSemRange[1] = 80;
                            endSemRange[2] = 80;
                            endSemRange[3] = 80;
                            endSemRange[4] = 80;

                            subCredit[0] = 3;
                            subCredit[1] = 3;
                            subCredit[2] = 3;
                            subCredit[3] = 3;
                            subCredit[4] = 3;

                            totalCredit = 23;
                            return sub5;
                    }
                    break;
                case "Electrical & Computer Science":
                    switch (sem) {
                        case "Semester 3":
                            sub5[0] = "Engineering Mathematics-III";
                            sub5[1] = "Electronic Devices";
                            sub5[2] = "Digital Electronics";
                            sub5[3] = "Data Structures and Algorithms";
                            sub5[4] = "Database Management Systems";

                            utMrkRange[0] = 20;
                            utMrkRange[1] = 20;
                            utMrkRange[2] = 20;
                            utMrkRange[3] = 20;
                            utMrkRange[4] = 20;

                            endSemRange[0] = 80;
                            endSemRange[1] = 80;
                            endSemRange[2] = 80;
                            endSemRange[3] = 80;
                            endSemRange[4] = 80;

                            subCredit[0] = 3;
                            subCredit[1] = 3;
                            subCredit[2] = 3;
                            subCredit[3] = 3;
                            subCredit[4] = 3;

                            totalCredit = 24;
                            return sub5;

                        case "Semester 4":
                            sub5[0] = "Engineering Mathematics-IV";
                            sub5[1] = "Electronic Circuits";
                            sub5[2] = "Controls and Instrumentation";
                            sub5[3] = "Microprocessors and Microcontrollers";
                            sub5[4] = "Discrete Structures and Automata Theory";

                            utMrkRange[0] = 20;
                            utMrkRange[1] = 20;
                            utMrkRange[2] = 20;
                            utMrkRange[3] = 20;
                            utMrkRange[4] = 20;

                            endSemRange[0] = 80;
                            endSemRange[1] = 80;
                            endSemRange[2] = 80;
                            endSemRange[3] = 80;
                            endSemRange[4] = 80;

                            subCredit[0] = 3;
                            subCredit[1] = 3;
                            subCredit[2] = 3;
                            subCredit[3] = 3;
                            subCredit[4] = 3;

                            totalCredit = 23;
                            return sub5;
                    }
                    break;
                case "Electrical & Telecommunication":
                    switch (sem) {
                        case "Semester 3":
                            sub5[0] = "Engineering Mathematics-III";
                            sub5[1] = "Electronic Devices & Circuits";
                            sub5[2] = "Digital System Design";
                            sub5[3] = "Network Theory";
                            sub5[4] = "Electronic Instrumentation & Control Systems";

                            utMrkRange[0] = 20;
                            utMrkRange[1] = 20;
                            utMrkRange[2] = 20;
                            utMrkRange[3] = 20;
                            utMrkRange[4] = 20;

                            endSemRange[0] = 80;
                            endSemRange[1] = 80;
                            endSemRange[2] = 80;
                            endSemRange[3] = 80;
                            endSemRange[4] = 80;

                            subCredit[0] = 3;
                            subCredit[1] = 3;
                            subCredit[2] = 3;
                            subCredit[3] = 3;
                            subCredit[4] = 3;

                            totalCredit = 24;
                            return sub5;

                        case "Semester 4":
                            sub5[0] = "Engineering Mathematics-IV";
                            sub5[1] = "Microcontrollers";
                            sub5[2] = "Linear Integrated Circuits";
                            sub5[3] = "Signals & Systems";
                            sub5[4] = "Principles of Communication Engineering";

                            utMrkRange[0] = 20;
                            utMrkRange[1] = 20;
                            utMrkRange[2] = 20;
                            utMrkRange[3] = 20;
                            utMrkRange[4] = 20;

                            endSemRange[0] = 80;
                            endSemRange[1] = 80;
                            endSemRange[2] = 80;
                            endSemRange[3] = 80;
                            endSemRange[4] = 80;

                            subCredit[0] = 3;
                            subCredit[1] = 3;
                            subCredit[2] = 3;
                            subCredit[3] = 3;
                            subCredit[4] = 3;

                            totalCredit = 24;
                            return sub5;
                    }
                    break;
            }
        }
        return null;
    }

    public static String[] getTermWorkSubjects(String branch, String sem) {
        String[] sub6 = new String[6];
        String[] sub7 = new String[7];
        String[] sub8 = new String[8];
        String[] sub9 = new String[9];

        if (sem.equals("Semester 1") || sem.equals("Semester 2")) {
            if (sem.equals("Semester 1")) {

                sub8[0] = "Applied Mathematics-I";
                sub8[1] = "Applied Physics";
                sub8[2] = "Applied Chemistry";
                sub8[3] = "Engineering Mechanics";
                sub8[4] = "Basic Electrical Engineering";
                sub8[5] = "Professional and Communication Ethics";
                sub8[6] = "C Programming";
                sub8[7] = "Engineering Workshop-I ";

                termWorkRange[0] = 25;
                termWorkRange[1] = 25;
                termWorkRange[2] = 25;
                termWorkRange[3] = 50;
                termWorkRange[4] = 50;
                termWorkRange[5] = 25;
                termWorkRange[6] = 50;
                termWorkRange[7] = 25;

                termWorkCredit[0] = 1;
                termWorkCredit[1] = 0.5f;
                termWorkCredit[2] = 0.5f;
                termWorkCredit[3] = 1;
                termWorkCredit[4] = 1;
                termWorkCredit[5] = 1;
                termWorkCredit[6] = 2;
                termWorkCredit[7] = 1;

                return sub8;
            } else {
                sub9[0] = "Applied Mathematics-II";
                sub9[1] = "Elective Physics";
                sub9[2] = "Elective Chemistry";
                sub9[3] = "Engineering Graphics";
                sub9[4] = "Program Core Lab";
                sub9[5] = "Social Science & Community Services ";
                sub9[6] = "Indian knowledge System";
                sub9[7] = "Python Programming";
                sub9[8] = "Engineering Workshop-II";

                termWorkRange[0] = 25;
                termWorkRange[1] = 25;
                termWorkRange[2] = 25;
                termWorkRange[3] = 50;
                termWorkRange[4] = 50;
                termWorkRange[5] = 25;
                termWorkRange[6] = 25;
                termWorkRange[7] = 50;
                termWorkRange[8] = 25;

                termWorkCredit[0] = 1;
                termWorkCredit[1] = 0.5f;
                termWorkCredit[2] = 0.5f;
                termWorkCredit[3] = 1;
                termWorkCredit[4] = 1;
                termWorkCredit[5] = 2;
                termWorkCredit[6] = 2;
                termWorkCredit[7] = 2;
                termWorkCredit[8] = 1;

                return sub9;
            }
        } else{
            switch (branch){
                case "Computer Science" :
                    switch (sem){
                        case "Semester 3" :
                            sub6[0] = "Engineering Mathematics-III";
                            sub6[1] = "Data Structure";
                            sub6[2] = "Digital Logic & Computer Architecture";
                            sub6[3] = "Computer Graphics";
                            sub6[4] = "Object Oriented Programming with Java";
                            sub6[5] = "Mini Project";

                            termWorkRange[0] = 25;
                            termWorkRange[1] = 50;
                            termWorkRange[2] = 25;
                            termWorkRange[3] = 50;
                            termWorkRange[4] = 75;
                            termWorkRange[5] = 50;

                            termWorkCredit[0] = 1;
                            termWorkCredit[1] = 1;
                            termWorkCredit[2] = 1;
                            termWorkCredit[3] = 1;
                            termWorkCredit[4] = 2;
                            termWorkCredit[5] = 2;

                            return sub6;

                        case "Semester 4" :
                            sub7[0] = "Engineering Mathematics-IV";
                            sub7[1] = "Analysis of Algorithm Lab";
                            sub7[2] = "Database Management System Lab";
                            sub7[3] = "Operating System Lab";
                            sub7[4] = "Microprocessor Lab";
                            sub7[5] = "Python Programming";
                            sub7[6] = "Mini Project";

                            termWorkRange[0] = 25;
                            termWorkRange[1] = 50;
                            termWorkRange[2] = 50;
                            termWorkRange[3] = 50;
                            termWorkRange[4] = 25;
                            termWorkRange[5] = 25;
                            termWorkRange[6] = 50;

                            termWorkCredit[0] = 1;
                            termWorkCredit[1] = 1;
                            termWorkCredit[2] = 1;
                            termWorkCredit[3] = 1;
                            termWorkCredit[4] = 1;
                            termWorkCredit[5] = 2;
                            termWorkCredit[6] = 2;

                            return sub7;
                    }
                    break;
                case "Information Technology" :
                    switch (sem){
                        case "Semester 3" :
                            sub6[0] = "Engineering Mathematics-III";
                            sub6[1] = "Data Structure and Analysis";
                            sub6[2] = "Computer programming Paradigms Lab";
                            sub6[3] = "SQL Lab";
                            sub6[4] = "Java Lab";
                            sub6[5] = "Mini Project";

                            termWorkRange[0] = 25;
                            termWorkRange[1] = 50;
                            termWorkRange[2] = 50;
                            termWorkRange[3] = 50;
                            termWorkRange[4] = 50;
                            termWorkRange[5] = 50;

                            termWorkCredit[0] = 1;
                            termWorkCredit[1] = 1;
                            termWorkCredit[2] = 1;
                            termWorkCredit[3] = 1;
                            termWorkCredit[4] = 2;
                            termWorkCredit[5] = 2;

                            return sub6;

                        case "Semester 4" :
                            sub6[0] = "Engineering Mathematics-IV";
                            sub6[1] = "Network Lab";
                            sub6[2] = "Unix Lab";
                            sub6[3] = "Microprocessor Lab";
                            sub6[4] = "Python Lab";
                            sub6[5] = "Mini Project";

                            termWorkRange[0] = 25;
                            termWorkRange[1] = 50;
                            termWorkRange[2] = 50;
                            termWorkRange[3] = 50;
                            termWorkRange[4] = 50;
                            termWorkRange[5] = 50;

                            termWorkCredit[0] = 1;
                            termWorkCredit[1] = 1;
                            termWorkCredit[2] = 1;
                            termWorkCredit[3] = 1;
                            termWorkCredit[4] = 2;
                            termWorkCredit[5] = 2;

                            return sub6;
                    }
                    break;
                case "Electrical" :
                    switch (sem){
                        case "Semester 3" :
                            sub6[0] = "Engineering Mathematics-III";
                            sub6[1] = "Electrical Machines & Measurements Lab";
                            sub6[2] = "Electronics Lab-I";
                            sub6[3] = "Simulation Lab-I";
                            sub6[4] = "Applied Electrical Engineering Lab (SBL)";
                            sub6[5] = "Mini Project";

                            termWorkRange[0] = 25;
                            termWorkRange[1] = 50;
                            termWorkRange[2] = 50;
                            termWorkRange[3] = 50;
                            termWorkRange[4] = 50;
                            termWorkRange[5] = 50;

                            termWorkCredit[0] = 1;
                            termWorkCredit[1] = 1;
                            termWorkCredit[2] = 1;
                            termWorkCredit[3] = 1;
                            termWorkCredit[4] = 2;
                            termWorkCredit[5] = 2;

                            return sub6;

                        case "Semester 4" :
                            sub6[0] = "Engineering Mathematics-IV";
                            sub6[1] = "Electrical AC Machines Lab I";
                            sub6[2] = "Python Programming Lab";
                            sub6[3] = "Electronics Lab II";
                            sub6[4] = "PCB Design and Fabrication Lab (SBL)";
                            sub6[5] = "Mini Project";

                            termWorkRange[0] = 25;
                            termWorkRange[1] = 50;
                            termWorkRange[2] = 50;
                            termWorkRange[3] = 50;
                            termWorkRange[4] = 50;
                            termWorkRange[5] = 50;

                            termWorkCredit[0] = 1;
                            termWorkCredit[1] = 1;
                            termWorkCredit[2] = 1;
                            termWorkCredit[3] = 1;
                            termWorkCredit[4] = 2;
                            termWorkCredit[5] = 2;

                            return sub6;
                    }
                    break;
                case "Electrical & Computer Science" :
                    switch (sem){
                        case "Semester 3" :
                            sub7[0] = "Engineering Mathematics-III";
                            sub7[1] = "Electronic Devices Lab";
                            sub7[2] = "Digital Electronics Lab";
                            sub7[3] = "Data Structures and Algorithms Lab";
                            sub7[4] = "Database Management systems lab";
                            sub7[5] = "OOPM (C++ and Java)";
                            sub7[6] = "Mini Project";

                            termWorkRange[0] = 25;
                            termWorkRange[1] = 50;
                            termWorkRange[2] = 50;
                            termWorkRange[3] = 50;
                            termWorkRange[4] = 50;
                            termWorkRange[5] = 50;
                            termWorkRange[6] = 50;

                            termWorkCredit[0] = 1;
                            termWorkCredit[1] = 1;
                            termWorkCredit[2] = 1;
                            termWorkCredit[3] = 1;
                            termWorkCredit[4] = 1;
                            termWorkCredit[5] = 2;
                            termWorkCredit[6] = 2;

                            return sub7;

                        case "Semester 4" :
                            sub6[0] = "Engineering Mathematics-IV";
                            sub6[1] = "Electronic Circuits Lab";
                            sub6[2] = "Controls and Instrumentation Lab";
                            sub6[3] = "Microprocessors and Microcontrollers Lab";
                            sub6[4] = "Python Programming";
                            sub6[5] = "Mini Project";

                            termWorkRange[0] = 25;
                            termWorkRange[1] = 50;
                            termWorkRange[2] = 50;
                            termWorkRange[3] = 50;
                            termWorkRange[4] = 50;
                            termWorkRange[5] = 50;

                            termWorkCredit[0] = 1;
                            termWorkCredit[1] = 1;
                            termWorkCredit[2] = 1;
                            termWorkCredit[3] = 1;
                            termWorkCredit[4] = 2;
                            termWorkCredit[5] = 2;

                            return sub6;
                    }
                    break;
                case "Electrical & Telecommunication" :
                    switch (sem){
                        case "Semester 3" :
                            sub7[0] = "Engineering Mathematics-III";
                            sub7[1] = "Network Theory";
                            sub7[2] = "Electronic Devices & Circuits Lab";
                            sub7[3] = "Digital System Design Lab";
                            sub7[4] = "Electronic Instrumentation & Control Systems Lab";
                            sub7[5] = "C++ and Java Programming";
                            sub7[6] = "Mini Project";

                            termWorkRange[0] = 25;
                            termWorkRange[1] = 25;
                            termWorkRange[2] = 50;
                            termWorkRange[3] = 50;
                            termWorkRange[4] = 25;
                            termWorkRange[5] = 50;
                            termWorkRange[6] = 25;

                            termWorkCredit[0] = 1;
                            termWorkCredit[1] = 1;
                            termWorkCredit[2] = 1;
                            termWorkCredit[3] = 1;
                            termWorkCredit[4] = 1;
                            termWorkCredit[5] = 2;
                            termWorkCredit[6] = 2;

                            return sub7;

                        case "Semester 4" :
                            sub7[0] = "Engineering Mathematics-IV";
                            sub7[1] = "Signals & Systems";
                            sub7[2] = "Microcontrollers Lab";
                            sub7[3] = "Linear Integrated Circuits Lab";
                            sub7[4] = "Principles of Communication Engineering Lab";
                            sub7[5] = "Python Programming";
                            sub7[6] = "Mini Project";

                            termWorkRange[0] = 25;
                            termWorkRange[1] = 25;
                            termWorkRange[2] = 25;
                            termWorkRange[3] = 50;
                            termWorkRange[4] = 50;
                            termWorkRange[5] = 50;
                            termWorkRange[6] = 50;

                            termWorkCredit[0] = 1;
                            termWorkCredit[1] = 1;
                            termWorkCredit[2] = 1;
                            termWorkCredit[3] = 1;
                            termWorkCredit[4] = 1;
                            termWorkCredit[5] = 2;
                            termWorkCredit[6] = 2;

                            return sub7;
                    }
                    break;
            }
        }
        return null;
    }

    public static int[] getInternalMarksRange(){
        return utMrkRange;
    }
    public static int[] getTermWorkRange(){
        return termWorkRange;
    }
    public static int[] getSubCredit(){
        return subCredit;
    }
    public static float[] getTermWorkCredit(){
        return termWorkCredit;
    }
    public static int getTotalCredit(){
        return totalCredit;
    }
    public static int[] getEndSemRange(){
        return endSemRange;
    }
}
