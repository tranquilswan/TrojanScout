package com.example.gearbox.scoutingappredux;

/**
 * Created by gearbox on 04/02/16.
 */
public class Team {

    private long  mDBid;

    private int mTeamNum;

    private String mPicLoc;

    private String mDriveSystem;

    private String mFuncMech;

    private String mGoalType;

    private int visionExist;

    private int autonomousExist;

    public Team(long mDBid, int mTeamNum, String mPicLoc, String mDriveSystem, String mFuncMech, String mGoalType, int visionExistance, int autonomousExistance) {
        this.mDBid = mDBid;
        this.mTeamNum = mTeamNum;
        this.mPicLoc = mPicLoc;
        this.mDriveSystem = mDriveSystem;
        this.mFuncMech = mFuncMech;
        this.mGoalType = mGoalType;
        this.visionExist = visionExistance;
        this.autonomousExist = autonomousExistance;
    }

    public Team(int mTeamNum, String mPicLoc, String mDriveSystem, String mFuncMech, String mGoalType, int visionExistance, int autonomousExistance) {
        this.mTeamNum = mTeamNum;
        this.mPicLoc = mPicLoc;
        this.mDriveSystem = mDriveSystem;
        this.mFuncMech = mFuncMech;
        this.mGoalType = mGoalType;
        this.visionExist = visionExistance;
        this.autonomousExist = autonomousExistance;
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
}