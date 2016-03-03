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

    private int mGroupA;

    private int mGroupB;

    private int mGroupC;

    private int mGroupD;

    private int mLowBar;

    private int mChallengeOrScale;

    public Team(long mDBid, int mTeamNum, String mPicLoc, String mDriveSystem, String mFuncMech, String mGoalType, int visionExistance, int autonomousExistance, String mTeamName, String comments, int groupA, int groupB, int groupC, int groupD, int lowBar, int challengeOrScale) {
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
        this.mGroupA = groupA;
        this.mGroupB = groupB;
        this.mGroupC = groupC;
        this.mGroupD = groupD;
        this.mLowBar = lowBar;
        this.mChallengeOrScale = challengeOrScale;
    }

    public Team(int mTeamNum, String mPicLoc, String mDriveSystem, String mFuncMech, String mGoalType, int visionExistance, int autonomousExistance, String mTeamName, String comments, int groupA, int groupB, int groupC, int groupD, int lowBar, int challengeOrScale) {
        this.mTeamNum = mTeamNum;
        this.mPicLoc = mPicLoc;
        this.mDriveSystem = mDriveSystem;
        this.mFuncMech = mFuncMech;
        this.mGoalType = mGoalType;
        this.visionExist = visionExistance;
        this.autonomousExist = autonomousExistance;
        this.mTeamName = mTeamName;
        this.mComments = comments;
        this.mGroupA = groupA;
        this.mGroupB = groupB;
        this.mGroupC = groupC;
        this.mGroupD = groupD;
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

    public int getmGroupA() {
        return mGroupA;
    }

    public void setmGroupA(int mGroupA) {
        this.mGroupA = mGroupA;
    }

    public int getmGroupB() {
        return mGroupB;
    }

    public void setmGroupB(int mGroupB) {
        this.mGroupB = mGroupB;
    }

    public int getmGroupC() {
        return mGroupC;
    }

    public void setmGroupC(int mGroupC) {
        this.mGroupC = mGroupC;
    }

    public int getmGroupD() {
        return mGroupD;
    }

    public void setmGroupD(int mGroupD) {
        this.mGroupD = mGroupD;
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