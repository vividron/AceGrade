package com.example.sgpa;

import java.util.ArrayList;
import java.util.List;

public class OnPrefPredictMarksAlgo {

    private static final int[] subCredit = Subjects.getSubCredit();
    public static String[] correctedPref = new String[SubjectPreferences.sub.length];
    public static boolean invalidPref = false;
    public static float preTC = 0;
    public static float sgpaTC = 0;
    public static ArrayList<Integer> easySub = new ArrayList<>();
    public static ArrayList<Integer> interSub = new ArrayList<>();
    public static ArrayList<Integer> hardSub = new ArrayList<>();

    public static int[] calculate(int[] termWorkMrks, int[] subPref, int[] endSemMrks, boolean[] endSemOrPref, int[] internalMrks, float sgpa) {

        int numOfSub = subPref.length;
        int numOfTermWorkSub = termWorkMrks.length;
        int totalCredits = Subjects.getTotalCredit();
        float[] termWorkCredit = Subjects.getTermWorkCredit();
        int[] endSemRange = Subjects.getEndSemRange();
        int[] internalRange = Subjects.getInternalMarksRange();
        int[] sub = new int[numOfSub];  // end sem marks, TO CALCULATE
        int[] startMul = new int[numOfSub];
        int[] interPlusPass = new int[numOfSub];
        int[] endRange = new int[numOfSub];
        int[] startRange = new int[numOfSub];
        sgpaTC = sgpa * totalCredits;

        if(!easySub.isEmpty()) easySub.clear();
        if(!interSub.isEmpty()) interSub.clear();
        if(!hardSub.isEmpty()) hardSub.clear();
        ArrayList<Integer> preList = new ArrayList<>(); // used for checking which preference is selected, agar sabhi preference honge to array will contain 0,1,2

        for (int i = 0; i < numOfSub; i++) {
            if (endSemRange[i] == 80) {
                if(endSemOrPref[i]) interPlusPass[i] = endSemMrks[i]+internalMrks[i];
                else interPlusPass[i] = internalMrks[i] + 32;
                startMul[i] = sub[i] = Range.sub.getMultiplierFor100(interPlusPass[i]);
            }
            else if (endSemRange[i] == 60) {
                if(endSemOrPref[i]) interPlusPass[i] = endSemMrks[i]+internalMrks[i];               //setting initial start for increment
                else interPlusPass[i] = internalMrks[i] + 24;
                if(Semester.s1Ors2) startMul[i] = sub[i] = Range.sub.getMultiplierFor100(interPlusPass[i]);
                else startMul[i] = sub[i]=Range.sub.getMultiplierFor75(interPlusPass[i]);
            } else if (endSemRange[i] == 45) {
                if(endSemOrPref[i]) interPlusPass[i] = endSemMrks[i]+internalMrks[i];
                else interPlusPass[i] = internalMrks[i] + 18;
                startMul[i] = sub[i] = Range.sub.getMultiplierFor75(interPlusPass[i]);
            }
            if(!endSemOrPref[i]) {
                if (!preList.contains(subPref[i])) {
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
        for (int i = 0; i < numOfTermWorkSub; i++) {
            sum += Range.sub.subTwGetCredit(termWorkMrks[i], termWorkCredit[i]);
        }

        boolean em = false, mh = false, eh = false, emh = false;

        if (preList.contains(0) && preList.contains(1) && preList.contains(2)) emh = true;
        else if (preList.contains(0) && preList.contains(1)) em = true;
        else if (preList.contains(1) && preList.contains(2)) mh = true;
        else if (preList.contains(0) && preList.contains(2)) eh = true;

        for (int i = 0; i < numOfSub; i++) {
            if(!endSemOrPref[i]) {
                startRange[i] = startRange(subPref[i], em, mh, eh, emh);
                endRange[i] = endRange(subPref[i], sub[i]);
            }
        }

        int[] subPriority = new int[numOfSub]; // priority of subject
        List<Integer> l = new ArrayList<>();

        //easy
        if (!easySub.isEmpty()) getPriority(easySub, l, internalMrks, internalRange);
        //inter
        if (!interSub.isEmpty()) getPriority(interSub, l, internalMrks, internalRange);
        //hard
        if (!hardSub.isEmpty()) getPriority(hardSub, l, internalMrks, internalRange);

        for(int i=0; i<numOfSub; i++) if(endSemOrPref[i]) l.add(i);

        int s = 0;
        for (Integer element : l) {
            subPriority[s++] = element;
        }

        int cnt = 0;
        while (sgpaTC > subCreditSum(sub) + sum) {
            boolean flag = true;
            for (int i = 0; i < numOfSub; i++) {
                if (!endSemOrPref[subPriority[i]]) {
                    if (sub[subPriority[i]] < endRange[subPriority[i]] && cnt >= startRange[subPriority[i]]) sub[subPriority[i]]++;
                    if (sgpaTC <= subCreditSum(sub) + sum) break;
                }
                if (sub[i] != endRange[i]) if (!endSemOrPref[i]) flag = false;
            }
            if (flag) break;
            cnt++;
        }

        preTC = subCreditSum(sub) + sum;
        invalidPref = sgpaTC - preTC > 2; //if all pref are incremented till there endRange and still the required sgpa is not close to the given input sgpa, then change the pref
        for(int i=0; i<numOfSub; i++){
            if(endSemOrPref[i]) sub[i] = endSemMrks[i];
            else {
                if(sub[i]<=endRange(1,startMul[i])) correctedPref[i] = "Inter";
                if(sub[i]<=endRange(2,startMul[i])) correctedPref[i] = "Hard";

                if (endSemRange[i] == 80)
                    sub[i] = Range.sub.getMarksFor100(sub[i], interPlusPass[i]) - internalMrks[i];
                else if (endSemRange[i] == 60) {
                    if (Semester.s1Ors2) sub[i] = Range.sub.getMarksFor100(sub[i], interPlusPass[i]) - internalMrks[i];
                    else sub[i] =  Range.sub.getMarksFor75(sub[i], interPlusPass[i]) - internalMrks[i];
                } else if (endSemRange[i] == 45) {
                    sub[i] = Range.sub.getMarksFor75(sub[i], interPlusPass[i]) - internalMrks[i];
                }
            }
            if(sub[i]>endSemRange[i]) return null;
        }
        preTC = Math.round((preTC / totalCredits)*100)/100f;
        sgpaTC = Math.round((sgpaTC / totalCredits)*100)/100f;
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
                int index = findCredit(temp, 2);
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

    private static int findCredit(ArrayList<Integer> a, int credit) {
        int index=0;
        for (int i : a) {
            if (getCredit(i) == credit) return index;
            index++;
        }
        return -1;
    }

    private static int getCredit(int i) {
        return subCredit[i];
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
        int totalCredits = Subjects.getTotalCredit();
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