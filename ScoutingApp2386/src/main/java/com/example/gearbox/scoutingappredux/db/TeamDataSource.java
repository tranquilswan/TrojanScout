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
    public static final String TABLE_NAME = "Team";
    public static final String TABLE_NAME_STATISTICS = "TeamStats";
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
    public static final String TEAM_NAME_COLUMN = "teamName";
    public static final int  TEAM_NAME_COLUMN_POSITION = 8;
    public static final String COMMENTS_COLUMN = "comments";
    public static final int COMMENTS_COLUMN_POSITION = 9;
    public static final String CHALLENGE_OR_SCALE_COLUMN = "chllengeOrScale";
    public static final int  CHALLENGE_OR_SCALE_COLUMN_POSTITION = 10;
    public static final String GROUP_A1_COLUMN = "groupA1";
    public static final int GROUP_A1_COLUMN_POSITION = 11;
    public static final String GROUP_A2_COLUMN = "groupA2";
    public static final int GROUP_A2_COLUMN_POSITION = 12;
    public static final String GROUP_B1_COLUMN = "groupB1";
    public static final int GROUP_B1_COLUMN_POSITION = 13;
    public static final String GROUP_B2_COLUMN = "groupB2";
    public static final int GROUP_B2_COLUMN_POSITION = 14;
    public static final String GROUP_C1_COLUMN = "groupC1";
    public static final int GROUP_C1_COLUMN_POSITION = 15;
    public static final String GROUP_C2_COLUMN = "groupC2";
    public static final int GROUP_C2_COLUMN_POSITION = 16;
    public static final String GROUP_D1_COLUMN = "groupD1";
    public static final int GROUP_D1_COLUMN_POSITION = 17;
    public static final String GROUP_D2_COLUMN = "groupD2";
    public static final int GROUP_D2_COLUMN_POSITION = 18;
    public static final String LOW_BAR_COLUMN = "lowBar";
    public static final int LOW_BAR_COLUMN_POSITION = 19;
    //DDL statement for table creation
    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + " (" +
                    ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TEAM_NUM_COLUMN + " INTEGER, " +
                    PIC_LOC_COLUMN + " TEXT, " +
                    DRIVE_SYSTEM_COLUMN + " TEXT, " +
                    FUNC_MECH_TYPE_COLUMN + " TEXT, " +
                    GOAL_TYPE_COLUMN + " INTEGER, " +
                    VISION_COLUMN + " INTEGER, " +
                    AUTONOMOUS_COLUMN + " INTEGER, " +
                    TEAM_NAME_COLUMN + " TEXT, " +
                    COMMENTS_COLUMN + " TEXT, " +
                    CHALLENGE_OR_SCALE_COLUMN + " INTEGER, " +
                    GROUP_A1_COLUMN + " INTEGER, " +
                    GROUP_A2_COLUMN + " INTEGER, " +
                    GROUP_B1_COLUMN + " INTEGER, " +
                    GROUP_B2_COLUMN + " INTEGER, " +
                    GROUP_C1_COLUMN + " INTEGER, " +
                    GROUP_C2_COLUMN + " INTEGER, " +
                    GROUP_D1_COLUMN + " INTEGER, " +
                    GROUP_D2_COLUMN + " INTEGER, " +
                    LOW_BAR_COLUMN + " INTEGER )";
    private SQLiteOpenHelper mDbOpenHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;



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
        cv.put(TEAM_NAME_COLUMN, team.getmTeamName());
        cv.put(COMMENTS_COLUMN, team.getmComments());
        cv.put(CHALLENGE_OR_SCALE_COLUMN, team.getmChallengeOrScale());
        cv.put(GROUP_A1_COLUMN, team.getmGroupA1());
        cv.put(GROUP_A2_COLUMN, team.getmGroupA2());
        cv.put(GROUP_B1_COLUMN, team.getmGroupB1());
        cv.put(GROUP_B2_COLUMN, team.getmGroupB2());
        cv.put(GROUP_C1_COLUMN, team.getmGroupC1());
        cv.put(GROUP_C2_COLUMN, team.getmGroupC2());
        cv.put(GROUP_D1_COLUMN, team.getmGroupD1());
        cv.put(GROUP_D2_COLUMN, team.getmGroupD2());
        cv.put(LOW_BAR_COLUMN, team.getmLowBar());



        long teamId = mDatabase.insert(TABLE_NAME, null, cv);

        team.setmDBid(teamId);
        mDatabase.close();

        return teamId;
    }

    public long updateTeam(Team team){
        mDatabase = mDbOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        //cv.put(TEAM_NUM_COLUMN, team.getmTeamNum());
        cv.put(PIC_LOC_COLUMN, team.getmPicLoc());
        cv.put(DRIVE_SYSTEM_COLUMN, team.getmDriveSystem());
        cv.put(FUNC_MECH_TYPE_COLUMN, team.getmFuncMech());
        cv.put(GOAL_TYPE_COLUMN, team.getmGoalType());
        cv.put(VISION_COLUMN, team.isVisionExist());
        cv.put(AUTONOMOUS_COLUMN, team.isAutonomousExist());
        cv.put(TEAM_NAME_COLUMN, team.getmTeamName());
        cv.put(COMMENTS_COLUMN, team.getmComments());
        cv.put(CHALLENGE_OR_SCALE_COLUMN, team.getmChallengeOrScale());
        cv.put(GROUP_A1_COLUMN, team.getmGroupA1());
        cv.put(GROUP_A2_COLUMN, team.getmGroupA2());
        cv.put(GROUP_B1_COLUMN, team.getmGroupB1());
        cv.put(GROUP_B2_COLUMN, team.getmGroupB2());
        cv.put(GROUP_C1_COLUMN, team.getmGroupC1());
        cv.put(GROUP_C2_COLUMN, team.getmGroupC2());
        cv.put(GROUP_D1_COLUMN, team.getmGroupD1());
        cv.put(GROUP_D2_COLUMN, team.getmGroupD2());
        cv.put(LOW_BAR_COLUMN, team.getmLowBar());

        long teamId = mDatabase.update(TABLE_NAME, cv, TEAM_NUM_COLUMN + " = ?", new String[]{Integer.toString(team.getmTeamNum())});

        return teamId;
    }

    public List<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();

        mDatabase = mDbOpenHelper.getReadableDatabase();

        Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, TEAM_NUM_COLUMN);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(ID_COLUMN_POSITION);
            int teamNum = cursor.getInt(TEAM_NUM_ID_COLUMN_POSITION);
            String picLoc = cursor.getString(PIC_ID_COLUMN_POSITION);
            String driveSystem = cursor.getString(DRIVE_SYSTEM_COLUMN_POSITION);
            String funcMech = cursor.getString(FUCN_MECH_TYPE_COLUMN_POSITION);
            String goalType = cursor.getString(GOAL_TYPE_COLUMN_POSITION);
            int vision = cursor.getInt(VISION_COLUMN_POSITION);
            int autonomous = cursor.getInt(AUTONOMOUS_COLUMN_POSITION);
            String teamName = cursor.getString(TEAM_NAME_COLUMN_POSITION);
            String comments = cursor.getString(COMMENTS_COLUMN_POSITION);
            int challengeOrScale = cursor.getInt(CHALLENGE_OR_SCALE_COLUMN_POSTITION);
            int groupA1 = cursor.getInt(GROUP_A1_COLUMN_POSITION);
            int groupB1 = cursor.getInt(GROUP_B1_COLUMN_POSITION);
            int groupC1 = cursor.getInt(GROUP_C1_COLUMN_POSITION);
            int groupD1 = cursor.getInt(GROUP_D1_COLUMN_POSITION);
            int groupA2 = cursor.getInt(GROUP_A2_COLUMN_POSITION);
            int groupB2 = cursor.getInt(GROUP_B2_COLUMN_POSITION);
            int groupC2 = cursor.getInt(GROUP_C2_COLUMN_POSITION);
            int groupD2 = cursor.getInt(GROUP_D2_COLUMN_POSITION);
            int lowBar = cursor.getInt(LOW_BAR_COLUMN_POSITION);

            teams.add(new Team(id, teamNum, picLoc, driveSystem, funcMech, goalType, vision, autonomous, teamName,
                    comments, groupA1, groupA2, groupB1, groupB2, groupC1, groupC2, groupD1, groupD2, lowBar, challengeOrScale));
        }
        cursor.close();
        mDatabase.close();
        return teams;
    }

    public Team getTeam(int teamNum){
        Team team = null;
        mDatabase = mDbOpenHelper.getReadableDatabase();
        //Cursor rawCursor = null;
        //String queryString = "SELECT * FROM Team WHERE teamNum = " + teamNum + ";";
        Cursor rawCursor
                = mDatabase.query(TABLE_NAME,
                                    null,
                                    TEAM_NUM_COLUMN+" = " + teamNum,
                                    null, null, null, TEAM_NUM_COLUMN);
        while (rawCursor.moveToNext()) {
            long id = rawCursor.getLong(ID_COLUMN_POSITION);
            int teamNumber = rawCursor.getInt(TEAM_NUM_ID_COLUMN_POSITION);
            String picLoc = rawCursor.getString(PIC_ID_COLUMN_POSITION);
            String driveSystem = rawCursor.getString(DRIVE_SYSTEM_COLUMN_POSITION);
            String funcMech = rawCursor.getString(FUCN_MECH_TYPE_COLUMN_POSITION);
            String goalType = rawCursor.getString(GOAL_TYPE_COLUMN_POSITION);
            int vision = rawCursor.getInt(VISION_COLUMN_POSITION);
            int autonomous = rawCursor.getInt(AUTONOMOUS_COLUMN_POSITION);
            String teamName = rawCursor.getString(TEAM_NAME_COLUMN_POSITION);
            String comments = rawCursor.getString(COMMENTS_COLUMN_POSITION);
            int challengeOrScale = rawCursor.getInt(CHALLENGE_OR_SCALE_COLUMN_POSTITION);
            int groupA1 = rawCursor.getInt(GROUP_A1_COLUMN_POSITION);
            int groupB1 = rawCursor.getInt(GROUP_B1_COLUMN_POSITION);
            int groupC1 = rawCursor.getInt(GROUP_C1_COLUMN_POSITION);
            int groupD1 = rawCursor.getInt(GROUP_D1_COLUMN_POSITION);
            int groupA2 = rawCursor.getInt(GROUP_A2_COLUMN_POSITION);
            int groupB2 = rawCursor.getInt(GROUP_B2_COLUMN_POSITION);
            int groupC2 = rawCursor.getInt(GROUP_C2_COLUMN_POSITION);
            int groupD2 = rawCursor.getInt(GROUP_D2_COLUMN_POSITION);
            int lowBar = rawCursor.getInt(LOW_BAR_COLUMN_POSITION);
            team = new Team(id, teamNum, picLoc, driveSystem, funcMech, goalType, vision, autonomous, teamName, comments,
                    groupA1, groupA2, groupB1, groupB2, groupC1, groupC2, groupD1, groupD2, lowBar, challengeOrScale);
        }

            rawCursor.close();
            mDatabase.close();

        return team;
    }

    public void deleteTeam(int teamNumber){
        mDatabase = mDbOpenHelper.getWritableDatabase();
        mDatabase.delete(TABLE_NAME, "teamNum = ?", new String[]{Integer.toString(teamNumber)});
        mDatabase.delete(TABLE_NAME_STATISTICS, "teamNum = ?", new String[]{Integer.toString(teamNumber)});
    }

    public boolean CheckIfTeamExists(String teamNum) {
        mDatabase = mDbOpenHelper.getWritableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + TEAM_NUM_COLUMN + " = " + teamNum;
        Cursor cursor = mDatabase.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}