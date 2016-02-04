package com.example.gearbox.scoutingappredux.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gearbox.scoutingappredux.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gearbox on 02/02/16.
 */
public class TeamDataSource {
    private SQLiteOpenHelper mDbOpenHelper;
    private SQLiteDatabase mDatabase;

    private Context mContext;

    public static final String TABLE_NAME = "Team";

    public static final String ID_COLUMN = "_ID";
    public static final int ID_COLUMN_POSITION = 0;

    public static final String TEAM_NUM_COLUMN = "teamNum";
    public static final int TEAM_NUM_ID_COLUMN_POSITION = 1;

    public static final String PIC_LOC_COLUMN = "picLoc";
    public static final int PIC_ID_COLUMN_POSITION = 2;

    public static final String DRIVE_SYSTEM_COLUMN = "driveSystem";
    public static final int DRIVE_SYSTEM_COLUMN_POSITION = 3;

    public static final String FUNC_MECH_TYPE_COLUMN = "funcMech";
    public static final int FUCN_MECH_TYPE_COLUMN_POSITION = 4;

    public static final String GOAL_TYPE_COLUMN = "goalType";
    public static final int GOAL_TYPE_COLUMN_POSITION = 5;

    public static final String VISION_COLUMN = "visionTF";
    public static final int VISION_COLUMN_POSITION = 6;

    public static final String AUTONOMOUS_COLUMN = "autonomousTF";
    public static final int AUTONOMOUS_COLUMN_POSITION = 7;

    //DDL statement for table creation
    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + " (" +
                    ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENTS, " +
                    TEAM_NUM_COLUMN + " INTEGER, " +
                    PIC_LOC_COLUMN + " TEXT, " +
                    DRIVE_SYSTEM_COLUMN + " TEXT, " +
                    FUNC_MECH_TYPE_COLUMN + " TEXT, " +
                    GOAL_TYPE_COLUMN + " INTEGER, " +
                    VISION_COLUMN + " INTEGER, " +
                    AUTONOMOUS_COLUMN + " INTEGER)";

    public TeamDataSource(Context context){
        mDbOpenHelper = new DbOpenHelper(context);
        mContext = context;
    }

    public long saveTeam(Team team){
        mDatabase = mDbOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(TEAM_NUM_COLUMN, team.getmTeamNum());
        cv.put(PIC_LOC_COLUMN, team.getmPicLoc());
        cv.put(DRIVE_SYSTEM_COLUMN, team.getmDriveSystem());
        cv.put(FUNC_MECH_TYPE_COLUMN, team.getmFuncMech());
        cv.put(GOAL_TYPE_COLUMN, team.getmGoalType());
        cv.put(VISION_COLUMN, team.isVisionExist());
        cv.put(AUTONOMOUS_COLUMN, team.isAutonomousExist());

        long teamId = mDatabase.insert(TABLE_NAME, null, cv);

        team.setmDBid(teamId);
        mDatabase.close();
        return teamId;
    }

//    public List<Team> getTeams(){
//        ArrayList<Team> teams = new ArrayList<>();
//
//        mDatabase = mDbOpenHelper.getReadableDatabase();
//
//        Cursor cursor = mDatabase.query(TABLE_NAME,null, null, null, null, null, null, )
//    }







}
