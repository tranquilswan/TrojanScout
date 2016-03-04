package com.example.gearbox.scoutingappredux;

import java.io.Serializable;
//xfgx

/**
 * Created by gearbox on 04/02/16.
 */
public class Team implements Serializable {

    private long  mDBid;

    private int mTeamNum;

    private String mPicLoc;

    private String mDriveSystem;

    private String mFuncMech;

    private String mGoalType;

    private int visionExist;

    private int autonomousExist;

    private String mTeamName;

    private String mComments;

    private int mGroupA1;

    private int mGroupA2;

    private int mGroupB1;

    private int mGroupB2;

    private int mGroupC1;

    private int mGroupC2;

    private int mGroupD1;

    private int mGroupD2;

    private int mLowBar;

    private int mChallengeOrScale;

    public Team(long mDBid, int mTeamNum, String mPicLoc, String mDriveSystem, String mFuncMech,
                String mGoalType, int visionExistance, int autonomousExistance, String mTeamName,
                String comments, int groupA1, int groupA2, int groupB1, int groupB2, int groupC1,
                int groupC2, int groupD1, int groupD2, int lowBar, int challengeOrScale) {
        this.mDBid = mDBid;
        this.mTeamNum = mTeamNum;
        this.mPicLoc = mPicLoc;
        this.mDriveSystem = mDriveSystem;
        this.mFuncMech = mFuncMech;
        this.mGoalType = mGoalType;
        this.visionExist = visionExistance;
        this.autonomousExist = autonomousExistance;
        this.mTeamName = mTeamName;
        this.mComments = comments;
        this.mGroupA1 = groupA1;
        this.mGroupA2 = groupA2;
        this.mGroupB1 = groupB1;
        this.mGroupB2 = groupB2;
        this.mGroupC1 = groupC1;
        this.mGroupC2 = groupC2;
        this.mGroupD1 = groupD1;
        this.mGroupD2 = groupD2;
        this.mLowBar = lowBar;
        this.mChallengeOrScale = challengeOrScale;
    }

    public Team(int mTeamNum, String mPicLoc, String mDriveSystem, String mFuncMech, String mGoalType, int visionExistance,
                int autonomousExistance, String mTeamName, String comments, int groupA1, int groupA2, int groupB1, int groupB2,
                int groupC1, int groupC2, int groupD1, int groupD2, int lowBar, int challengeOrScale) {
        this.mTeamNum = mTeamNum;
        this.mPicLoc = mPicLoc;
        this.mDriveSystem = mDriveSystem;
        this.mFuncMech = mFuncMech;
        this.mGoalType = mGoalType;
        this.visionExist = visionExistance;
        this.autonomousExist = autonomousExistance;
        this.mTeamName = mTeamName;
        this.mComments = comments;
        this.mGroupA1 = groupA1;
        this.mGroupA2 = groupA2;
        this.mGroupB1 = groupB1;
        this.mGroupB2 = groupB2;
        this.mGroupC1 = groupC1;
        this.mGroupC2 = groupC2;
        this.mGroupD1 = groupD1;
        this.mGroupD2 = groupD2;
        this.mLowBar = lowBar;
        this.mChallengeOrScale = challengeOrScale;
    }

    public long getmDBid() {
        return mDBid;
    }

    public void setmDBid(long mDBid) {
        this.mDBid = mDBid;
    }

    public int getmTeamNum() {
        return mTeamNum;
    }

    public void setmTeamNum(int mTeamNum) {
        this.mTeamNum = mTeamNum;
    }

    public String getmPicLoc() {
        return mPicLoc;
    }

    public void setmPicLoc(String mPicLoc) {
        this.mPicLoc = mPicLoc;
    }

    public String getmDriveSystem() {
        return mDriveSystem;
    }

    public void setmDriveSystem(String mDriveSystem) {
        this.mDriveSystem = mDriveSystem;
    }

    public String getmFuncMech() {
        return mFuncMech;
    }

    public void setmFuncMech(String mFuncMech) {
        this.mFuncMech = mFuncMech;
    }

    public String getmGoalType() {
        return mGoalType;
    }

    public void setmGoalType(String mGoalType) {
        this.mGoalType = mGoalType;
    }

    public int isVisionExist() {
        return visionExist;
    }

    public void setVisionExist(int visionExist) {
        this.visionExist = visionExist;
    }

    public int isAutonomousExist() {
        return autonomousExist;
    }

    public void setAutonomousExist(int autonomousExist) {
        this.autonomousExist = autonomousExist;
    }


    public String getmTeamName() {
        return mTeamName;
    }

    public void setmTeamName(String mTeamName) {
        this.mTeamName = mTeamName;
    }

    public String getmComments() {
        return mComments;
    }

    public void setmComments(String mComments) {
        this.mComments = mComments;
    }

    public int getmGroupA1() {
        return mGroupA1;
    }

    public void setmGroupA1(int mGroupA1) {
        this.mGroupA1 = mGroupA1;
    }

    public int getmGroupA2() {
        return mGroupA2;
    }

    public void setmGroupA2(int mGroupA2) {
        this.mGroupA2 = mGroupA2;
    }

    public int getmGroupB1() {
        return mGroupB1;
    }

    public void setmGroupB1(int mGroupB1) {
        this.mGroupB1 = mGroupB1;
    }

    public int getmGroupD1() {
        return mGroupD1;
    }

    public void setmGroupD1(int mGroupD1) {
        this.mGroupD1 = mGroupD1;
    }

    public int getmGroupC2() {
        return mGroupC2;
    }

    public void setmGroupC2(int mGroupC2) {
        this.mGroupC2 = mGroupC2;
    }

    public int getmGroupC1() {
        return mGroupC1;
    }

    public void setmGroupC1(int mGroupC1) {
        this.mGroupC1 = mGroupC1;
    }

    public int getmGroupB2() {
        return mGroupB2;
    }

    public void setmGroupB2(int mGroupB2) {
        this.mGroupB2 = mGroupB2;
    }

    public int getmGroupD2() {
        return mGroupD2;
    }

    public void setmGroupD2(int mGroupD2) {
        this.mGroupD2 = mGroupD2;
    }

    public int getmLowBar() {
        return mLowBar;
    }

    public void setmLowBar(int mLowBar) {
        this.mLowBar = mLowBar;
    }

    public int getmChallengeOrScale() {
        return mChallengeOrScale;
    }

    public void setmChallengeOrScale(int mChallengeOrScale) {
        this.mChallengeOrScale = mChallengeOrScale;
    }

    @Override
    public String toString() {

        return "" + getmTeamNum() + " : " + getmTeamName();
    }
}