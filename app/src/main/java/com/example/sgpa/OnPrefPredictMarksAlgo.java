package com.example.sgpa;

import java.util.ArrayList;
import java.util.List;

public class OnPrefPredictMarksAlgo {

    private static final int[] SUBJECT_CREDITS = Subjects.getSubCredit(); /// credits which are assigned to subjects ex- 2,3,_
    public static String[] correctedPref = new String[SubjectPreferences.sub.length]; /// use to showcase the new preferences. if the user's selected pref gives SGPA more than the desired one then it automatically change the subject preference.
    public static boolean invalidPref = false; /// to check if the given preferences align with desired output(SGPA).
    public static float preTC = 0; /// total credits calculated through preferences.
    public static float sgpaTC = 0; /// total credits calculated through input SGPA.
    public static ArrayList<Integer> easySub = new ArrayList<>(); /// to store all the easy pref subjects.
    public static ArrayList<Integer> interSub = new ArrayList<>(); /// to store all the intermediate pref subjects.
    public static ArrayList<Integer> hardSub = new ArrayList<>(); /// to store all the hard pref subjects.

    public static int[] calculate(int[] termWorkMrks, int[] subPref, int[] endSemMrks, boolean[] isEndSemMarks, int[] internalMrks, float sgpa) {

        int numOfSub = subPref.length;
        int[] sub = new int[numOfSub];  /// marks to score in end sem. TO CALCULATE.
        int numOfTermWorkSub = termWorkMrks.length;
        int Credits = Subjects.getCredits();
        float[] termWorkCredit = Subjects.getTermWorkCredit();
        int[] endSemRange = Subjects.getEndSemRange(); /// end sem exam marks for particular subjects.
        int[] internalRange = Subjects.getInternalMarksRange(); /// unit test marks for particular subjects.
        int[] startMul = new int[numOfSub]; /// initial multiplier for each sub's. multiplier is the number which is decided by the range.
        int[] interPlusPass = new int[numOfSub]; /// to store the addition of passing marks required for end sem and unit test marks for each sub, depending on it's range.
        int[] startRange = new int[numOfSub]; /// to decide when the sub multiplier will start incrementing.
        int[] endRange = new int[numOfSub]; /// to decide when to stop incrementing the sub multiplier.
        sgpaTC = sgpa * Credits;

        if(!easySub.isEmpty()) easySub.clear();
        if(!interSub.isEmpty()) interSub.clear();
        if(!hardSub.isEmpty()) hardSub.clear();
        ArrayList<Integer> preList = new ArrayList<>(); /// used for checking which preference is selected, agar sabhi unique preference honge to array will contain 0,1,2

        for (int i = 0; i < numOfSub; i++) { //setting initial multiplier for each sub to start incrementation
            if (endSemRange[i] == 80) {
                if(isEndSemMarks[i]) interPlusPass[i] = endSemMrks[i]+internalMrks[i]; // if user enter the end sem marks instead of pref we directly calculate the marks and store it into sub array. no need for incrementation
                else interPlusPass[i] = internalMrks[i] + 32; // adding passing marks to unit test marks will give us the minimum marks possible for that particular sub.
                startMul[i] = sub[i] = Range.sub.getMultiplierFor100(interPlusPass[i]); // constant marks(if the user enters the end sem marks) or the minimum marks(if the user selects pref) will help us to find starting multiplier of sub.
            }
            else if (endSemRange[i] == 60) {
                if(isEndSemMarks[i]) interPlusPass[i] = endSemMrks[i]+internalMrks[i];
                else interPlusPass[i] = internalMrks[i] + 24;
                if(Semester.s1Ors2) startMul[i] = sub[i] = Range.sub.getMultiplierFor100(interPlusPass[i]);
                else startMul[i] = sub[i]=Range.sub.getMultiplierFor75(interPlusPass[i]);
            } else if (endSemRange[i] == 45) {
                if(isEndSemMarks[i]) interPlusPass[i] = endSemMrks[i]+internalMrks[i];
                else interPlusPass[i] = internalMrks[i] + 18;
                startMul[i] = sub[i] = Range.sub.getMultiplierFor75(interPlusPass[i]);
            }
            if(!isEndSemMarks[i]) { // separating each sub according to prefs
                if (!preList.contains(subPref[i])) { // subPref array holds the info of prefs as 0,1,2. 0 - easy, 1- inter, 2-hard. for ex : if there are 5 subjects which have prefs as easy,easy,easy,hard,inter then the subPref array will have the length of 5 and values |0|0|0|2|1|.
                    preList.add(subPref[i]);
                }
                if (subPref[i] == 0) {
                    easySub.add(i);
                    correctedPref[i] = "Easy";
                } else if (subPref[i] == 1) {
                    interSub.add(i);
                    correctedPref[i] = "Inter";
                } else if (subPref[i] == 2) {
                    hardSub.add(i);
                    correctedPref[i] = "Hard";
                }
            }
        }

        float sum = 0;
        for (int i = 0; i < numOfTermWorkSub; i++) { // calculating term work's total credits
            sum += Range.sub.subTwGetCredit(termWorkMrks[i], termWorkCredit[i]);
        }

        boolean em = false, mh = false, eh = false, emh = false;

        if (preList.contains(0) && preList.contains(1) && preList.contains(2)) emh = true;
        else if (preList.contains(0) && preList.contains(1)) em = true;
        else if (preList.contains(1) && preList.contains(2)) mh = true;
        else if (preList.contains(0) && preList.contains(2)) eh = true;

        for (int i = 0; i < numOfSub; i++) { // calculating each sub's start and end range
            if(!isEndSemMarks[i]) {
                startRange[i] = startRange(subPref[i], em, mh, eh, emh);
                endRange[i] = endRange(subPref[i], sub[i]);
            }
        }

        int[] subPriority = new int[numOfSub]; // priority of subjects according to the credits assigned. for ex - 2 credit sub will increment before 3 credit sub.
        List<Integer> l = new ArrayList<>();

        //easy
        if (!easySub.isEmpty()) getPriority(easySub, l, internalMrks, internalRange);
        //inter
        if (!interSub.isEmpty()) getPriority(interSub, l, internalMrks, internalRange);
        //hard
        if (!hardSub.isEmpty()) getPriority(hardSub, l, internalMrks, internalRange);

        for(int i=0; i<numOfSub; i++) if(isEndSemMarks[i]) l.add(i);

        int s = 0;
        for (Integer element : l) {
            subPriority[s++] = element;
        }

        int cnt = 0;
        while (sgpaTC > subCreditSum(sub) + sum) {
            boolean flag = true;
            for (int i = 0; i < numOfSub; i++) {
                if (!isEndSemMarks[subPriority[i]]) { //checking if the user has provided end sem marks for this sub, if it is then no incrementation as the marks are already provided.
                    if (sub[subPriority[i]] < endRange[subPriority[i]] && cnt >= startRange[subPriority[i]]) sub[subPriority[i]]++; // this logic will allow to increment easy sub first and then inter followed by hard.
                    if (sgpaTC <= subCreditSum(sub) + sum) break; //after each incrementation we will check if the required sgpa is reached or not.
                }
                if (sub[i] != endRange[i]) if (!isEndSemMarks[i]) flag = false; // if each sub's multiplier is equal to the end range after incrementation then we will break the loop. this is necessary as the loop will run infinitely if the prefs are not able to align with the desired SGPA. in this case each sub multiplier have reached there maximum range acc to prefs.
            }
            if (flag) break;
            cnt++;
        }

        preTC = subCreditSum(sub) + sum;
        invalidPref = sgpaTC - preTC > 2; // if all pref are incremented till there endRange and still the required sgpa is not close to the given input sgpa, then change the pref.
        for(int i=0; i<numOfSub; i++){ // calculating the minimum marks as per the multiplier calculated then storing it into sub array
            if(isEndSemMarks[i]) sub[i] = endSemMrks[i];
            else {
                if(sub[i]<=endRange(1,startMul[i])) correctedPref[i] = "Inter";
                if(sub[i]<=endRange(2,startMul[i])) correctedPref[i] = "Hard";
                if (endSemRange[i] == 80)
                    sub[i] = Range.sub.getMarksFor100(sub[i], interPlusPass[i]) - internalMrks[i];
                else if (endSemRange[i] == 60) {
                    if (Semester.s1Ors2) sub[i] = Range.sub.getMarksFor100(sub[i], interPlusPass[i]) - internalMrks[i];
                    else sub[i] =  Range.sub.getMarksFor75(sub[i], interPlusPass[i]) - internalMrks[i];
                }else if (endSemRange[i] == 45) {
                    sub[i] = Range.sub.getMarksFor75(sub[i], interPlusPass[i]) - internalMrks[i];
                }
            }
            if(sub[i]>endSemRange[i]) return null;
        }
        preTC = Math.round((preTC / Credits)*100)/100f; // pref total credits is now reused for storing SGPA calculated using pref upto 2 digit decimal
        sgpaTC = Math.round((sgpaTC / Credits)*100)/100f; // user input SGPA is retained
        return sub;
    }

    public static int startRange(int pre, boolean em, boolean mh, boolean eh, boolean emh) {
        if (emh) {
            if (pre == 0) return 0;
            else if (pre == 1) return 2;
            else if (pre == 2) return 3;
        } else if (em) {
            if (pre == 0) return 0;
            else if (pre == 1) return 2;
        } else if (mh) {
            if (pre == 1) return 0;
            else if (pre == 2) return 2;
        } else if (eh) {
            if (pre == 0) return 0;
            else if (pre == 2) return 2;
        }
        return 0;
    }

    public static int endRange(int pre, int sub) {
        if (pre == 0) return 10;
        else if (pre == 1) {
            if(sub==5 || sub==6) return sub+2;
            else return sub+3;
        }
        else if (pre == 2){
            if(sub==5 || sub==6) return sub+1;
            else return sub+2;
        }
        return -1;
    }

    public static void getPriority(ArrayList<Integer> pref, List<Integer> l, int[] internalMrks, int[] internalRange) {
        ArrayList<Integer> temp = new ArrayList<>(pref);
        boolean have2Credit = true;
        for (int i = 0; i < pref.size(); i++) {
            if (have2Credit) {
                int index = findCredit(temp);
                if (index != -1) {
                    l.add(temp.get(index));
                    temp.remove(index);
                } else {
                    have2Credit = false;
                }
            }
            if (!have2Credit) {
                int minInterMrk = Integer.MAX_VALUE;
                int index = 0;
                for (int sub = 0; sub < temp.size(); sub++) {
                    int diff = internalRange[temp.get(sub)] - internalMrks[temp.get(sub)];
                    if (diff < minInterMrk) {
                        minInterMrk = diff;
                        index = sub;
                    }
                }
                System.out.println(temp.get(index));
                l.add(temp.get(index));
                temp.remove(index);
            }
        }
        temp.clear();
    }

    private static int findCredit(ArrayList<Integer> a) {
        int index=0;
        for (int i : a) {
            if (getCredit(i) == 2) return index;
            index++;
        }
        return -1;
    }

    private static int getCredit(int i) {
        return SUBJECT_CREDITS[i];
    }

    private static float subCreditSum(int[] sub) {
        float sum = 0f;
        for (int i = 0; i < sub.length; i++) {
            sum = sum + (sub[i] * getCredit(i));
        }
        return sum;
    }
    public static Float calculateSgpa(int[] termWorkMrks, int[] internalMrks, int[] endSemMrks){
        int[] endSemRange = Subjects.getEndSemRange();
        float[] termWorkCredit = Subjects.getTermWorkCredit();
        int totalCredits = Subjects.getCredits();
        int[] sub = new int[endSemMrks.length];
        for (int i = 0; i < endSemMrks.length; i++) {
            if (endSemRange[i] == 80) {
                sub[i] = Range.sub.getMultiplierFor100(endSemMrks[i]+internalMrks[i]);
            }
            else if (endSemRange[i] == 60) {
                if(Semester.s1Ors2) sub[i] = Range.sub.getMultiplierFor100(endSemMrks[i]+internalMrks[i]);
                else sub[i] = Range.sub.getMultiplierFor75(endSemMrks[i]+internalMrks[i]);
            } else if (endSemRange[i] == 45) {
                sub[i] = Range.sub.getMultiplierFor75(endSemMrks[i]+internalMrks[i]);
            }
        }
        float sum = 0;
        for (int i = 0; i < termWorkMrks.length; i++) {
            sum += Range.sub.subTwGetCredit(termWorkMrks[i], termWorkCredit[i]);
        }
        return Math.round(((subCreditSum(sub)+sum)/totalCredits)*100)/100f;
    }
}