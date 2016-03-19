package com.trojans.gearbox.scoutingappredux.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVWriter;
import com.trojans.gearbox.scoutingappredux.Statistics;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicknagi on 06/03/16.
 */
public class TeamStatsDataSource {

    public static final String TABLE_NAME = "TeamStats";
    public static final String ID_COLUMN = "_ID";
    public static final int ID_COLUMN_POSITION = 0;
    public static final String TEAM_NUM_COLUMN = "teamNum";
    public static final int TEAM_NUM_COLUMN_POSITION = 1;
    public static final String TEAM_NAME_COLUMN = "teamName";
    public static final int TEAM_NAME_COLUMN_POSITION = 2;
    public static final String COMMENTS_COLUMN = "comments";
    public static final int COMMENTS_COLUMN_POSITION = 3;
    public static final String TOTAL_SHOTS_COLUMN = "TotalShots";
    public static final int TOTAL_SHOTS_COLUMN_POSITION = 4;
    public static final String LOW_GOALS_COLUMN = "LowGoals";
    public static final int LOW_GOALS_COLUMN_POSITION = 5;
    public static final String HIGH_GOALS_COLUMN = "HighGoals";
    public static final int HIGH_GOALS_COLUMN_POSITION = 6;
    public static final String PORT_CULLIS_CROSSES_COLUMN = "PortCullisCrosses";
    public static final int PORT_CULLIS_CROSSES_COLUMN_POSITION = 7;
    public static final String CHIVAL_DE_FRISE_CROSSES_COLUMN = "ChivalDeFriseCrosses";
    public static final int CHIVAL_DE_FRISE_CROSSES_COLUMN_POSITION = 8;
    public static final String RAMPARTS_CROSSES_COLUMN = "RampartsCrosses";
    public static final int RAMPARTS_CROSSES_COLUMN_POSITION = 9;
    public static final String MOAT_CROSSES_COLUMN = "MoatCrosses";
    public static final int MOAT_CROSSES_COLUMN_POSITION = 10;
    public static final String DRAW_BRIDGE_CROSSES_COLUMN = "DrawBridgeCrosses";
    public static final int DRAW_BRIDGE_CROSSES_COLUMN_POSITION = 11;
    public static final String SALLY_PORT_CROSSES_COLUMN = "SallyPortCrosses";
    public static final int SALLY_PORT_CROSSES_COLUMN_POSITION = 12;
    public static final String ROUGH_TERRAIN_CROSSES_COLUMN = "RoughTerrainCrosses";
    public static final int ROUGH_TERRAIN_CROSSES_COLUMN_POSITION = 13;
    public static final String ROCK_WALL_CROSSES_COLUMN = "RockWallCrosses";
    public static final int ROCK_WALL_CROSSES_COLUMN_POSITION = 14;
    public static final String LOW_BAR_CROSSES_COLUMN = "LowBarCrosses";
    public static final int LOW_BAR_CROSSES_COLUMN_POSITION = 15;
    public static final String END_GAME_TYPE_COLUMN = "EndGameType";
    public static final int END_GAME_TYPE_COLUMN_POSITION = 16;
    public static final String MATCH_ID_COLUMN = "MatchID";
    public static final int MATCH_ID_COLUMN_POSITION = 17;
    public static final String AUTONOMOUS_USAGE_COLUMN = "AutonomousUsage";
    public static final int AUTONOMOUS_USAGE_COLUMN_POSITION = 18;
    public static final String SCORE_COLUMN = "Score";
    public static final int SCORE_COLUMN_POSITION = 19;

    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + " (" +
                    ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TEAM_NUM_COLUMN + " INTEGER, " +
                    TEAM_NAME_COLUMN + " TEXT, " +
                    COMMENTS_COLUMN + " TEXT, " +
                    TOTAL_SHOTS_COLUMN + " INTEGER, " +
                    LOW_GOALS_COLUMN + " INTEGER, " +
                    HIGH_GOALS_COLUMN + " INTEGER, " +
                    PORT_CULLIS_CROSSES_COLUMN + " INTEGER, " +
                    CHIVAL_DE_FRISE_CROSSES_COLUMN + " INTEGER, " +
                    RAMPARTS_CROSSES_COLUMN + " INTEGER, " +
                    MOAT_CROSSES_COLUMN + " INTEGER, " +
                    DRAW_BRIDGE_CROSSES_COLUMN + " INTEGER, " +
                    SALLY_PORT_CROSSES_COLUMN + " INTEGER, " +
                    ROUGH_TERRAIN_CROSSES_COLUMN + " INTEGER, " +
                    ROCK_WALL_CROSSES_COLUMN + " INTEGER, " +
                    LOW_BAR_CROSSES_COLUMN + " INTEGER, " +
                    END_GAME_TYPE_COLUMN + " INTEGER, " +
                    MATCH_ID_COLUMN + " INTEGER, " +
                    AUTONOMOUS_USAGE_COLUMN + " INTEGER, " +
                    SCORE_COLUMN + " INTEGER )";


    private SQLiteOpenHelper mDbOpenHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public TeamStatsDataSource(Context context) {
        mDbOpenHelper = new DbOpenHelper(context);
        mContext = context;
    }

    public long saveTeam(Statistics stats) {
        mDatabase = mDbOpenHelper.getWritableDatabase();

        int count = 0;
        Cursor rawCursor
                = mDatabase.query(TABLE_NAME,
                null,
                TEAM_NUM_COLUMN + " = " + stats.getmTeamNum(),
                null, null, null, TEAM_NUM_COLUMN);

        count = rawCursor.getCount();

        int MatchID = count + 1;

        ContentValues cv = new ContentValues();

        cv.put(TEAM_NUM_COLUMN, stats.getmTeamNum());
        cv.put(TEAM_NAME_COLUMN, stats.getmTeamName());
        cv.put(COMMENTS_COLUMN, stats.getmComments());
        cv.put(TOTAL_SHOTS_COLUMN, stats.getTotalShots());
        cv.put(LOW_GOALS_COLUMN, stats.getLowGoals());
        cv.put(HIGH_GOALS_COLUMN, stats.getHighGoals());
        cv.put(PORT_CULLIS_CROSSES_COLUMN, stats.getPortCullisCrosses());
        cv.put(CHIVAL_DE_FRISE_CROSSES_COLUMN, stats.getChivalDeFriseCrosses());
        cv.put(RAMPARTS_CROSSES_COLUMN, stats.getRampartsCrosses());
        cv.put(MOAT_CROSSES_COLUMN, stats.getMoatCrosses());
        cv.put(DRAW_BRIDGE_CROSSES_COLUMN, stats.getDrawBridgeCrosses());
        cv.put(SALLY_PORT_CROSSES_COLUMN, stats.getSallyPortCrosses());
        cv.put(ROUGH_TERRAIN_CROSSES_COLUMN, stats.getRoughTerrainCrosses());
        cv.put(ROCK_WALL_CROSSES_COLUMN, stats.getRoughTerrainCrosses());
        cv.put(LOW_BAR_CROSSES_COLUMN, stats.getLowBarCrosses());
        cv.put(END_GAME_TYPE_COLUMN, stats.getEndGameType());
        cv.put(AUTONOMOUS_USAGE_COLUMN, stats.getAutonomousUsage());
        cv.put(MATCH_ID_COLUMN, MatchID);
        cv.put(SCORE_COLUMN, stats.getScore());


        long teamId = mDatabase.insert(TABLE_NAME, null, cv);

        stats.setmDBid(teamId);
        mDatabase.close();

        return teamId;
    }

    public long updateMatchID(int teamNum, int oldMatchID, int newMatchID) {
        mDatabase = mDbOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        //cv.put(TEAM_NUM_COLUMN, team.getmTeamNum());
        cv.put(MATCH_ID_COLUMN, newMatchID);


        long teamId = mDatabase.update(TABLE_NAME, cv, TEAM_NUM_COLUMN + " = ?" + " AND " + MATCH_ID_COLUMN + " = ?"
                , new String[]{Integer.toString(teamNum), Integer.toString(oldMatchID)});

        mDatabase.close();

        return teamId;
    }

    public long updateScore(int teamNum, int MatchID, int score) {
        mDatabase = mDbOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        //cv.put(TEAM_NUM_COLUMN, team.getmTeamNum());
        cv.put(SCORE_COLUMN, score);


        long teamId = mDatabase.update(TABLE_NAME, cv, TEAM_NUM_COLUMN + " = ?" + " AND " + MATCH_ID_COLUMN + " = ?"
                , new String[]{Integer.toString(teamNum), Integer.toString(MatchID)});

        mDatabase.close();

        return teamId;
    }

    public long updateTeamName(String teamName, int teamNum) {
        mDatabase = mDbOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        //cv.put(TEAM_NUM_COLUMN, team.getmTeamNum());
        cv.put(TEAM_NAME_COLUMN, teamName);


        long teamId = mDatabase.update(TABLE_NAME, cv, TEAM_NUM_COLUMN + " = ?"
                , new String[]{Integer.toString(teamNum)});

        mDatabase.close();

        return teamId;
    }

    //Could be unstable
    public List<Statistics> getTeams() {
        ArrayList<Statistics> stats = new ArrayList<>();

        mDatabase = mDbOpenHelper.getReadableDatabase();

        Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, TEAM_NUM_COLUMN);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(ID_COLUMN_POSITION);
            int teamNum = cursor.getInt(TEAM_NUM_COLUMN_POSITION);
            String teamName = cursor.getString(TEAM_NAME_COLUMN_POSITION);
            String comments = cursor.getString(COMMENTS_COLUMN_POSITION);
            int totalShots = cursor.getInt(TOTAL_SHOTS_COLUMN_POSITION);
            int highGoals = cursor.getInt(HIGH_GOALS_COLUMN_POSITION);
            int lowGoals = cursor.getInt(LOW_GOALS_COLUMN_POSITION);
            int defPortCullisCrosses = cursor.getInt(PORT_CULLIS_CROSSES_COLUMN_POSITION);
            int defChivalDeFriseCrosses = cursor.getInt(CHIVAL_DE_FRISE_CROSSES_COLUMN_POSITION);
            int defRampartsCrosses = cursor.getInt(RAMPARTS_CROSSES_COLUMN_POSITION);
            int defRockWallCrosses = cursor.getInt(ROCK_WALL_CROSSES_COLUMN_POSITION);
            int defRoughTerrainCrosses = cursor.getInt(ROUGH_TERRAIN_CROSSES_COLUMN_POSITION);
            int defSallyPortCrosses = cursor.getInt(SALLY_PORT_CROSSES_COLUMN_POSITION);
            int defDrawBridgeCrosses = cursor.getInt(DRAW_BRIDGE_CROSSES_COLUMN_POSITION);
            int defMoatCrosses = cursor.getInt(MOAT_CROSSES_COLUMN_POSITION);
            int defLowBarCrosses = cursor.getInt(LOW_BAR_CROSSES_COLUMN_POSITION);
            int endGameType = cursor.getInt(END_GAME_TYPE_COLUMN_POSITION);
            int autonomousUsage = cursor.getInt(AUTONOMOUS_USAGE_COLUMN_POSITION);
            int MatchID = cursor.getInt(MATCH_ID_COLUMN_POSITION);
            int Score = cursor.getInt(SCORE_COLUMN_POSITION);

            stats.add(new Statistics(autonomousUsage, defChivalDeFriseCrosses, defDrawBridgeCrosses, endGameType,
                    highGoals, defLowBarCrosses, lowGoals, comments, id, defMoatCrosses, teamName, teamNum, defPortCullisCrosses,
                    defRampartsCrosses, defRockWallCrosses, defSallyPortCrosses, defRoughTerrainCrosses, totalShots, MatchID, Score));
        }
        cursor.close();
        mDatabase.close();
        return stats;
    }

    public List<Statistics> getTeam(int teamNum) {
        ArrayList<Statistics> stats = new ArrayList<>();
        mDatabase = mDbOpenHelper.getReadableDatabase();
        //Cursor rawCursor = null;
        //String queryString = "SELECT * FROM Team WHERE teamNum = " + teamNum + ";";
        Cursor rawCursor
                = mDatabase.query(TABLE_NAME,
                null,
                TEAM_NUM_COLUMN + " = " + teamNum,
                null, null, null, TEAM_NUM_COLUMN);

        while (rawCursor.moveToNext()) {
            long id = rawCursor.getLong(ID_COLUMN_POSITION);
//            int teamNum = rawCursor.getInt(TEAM_NUM_COLUMN_POSITION);
            String teamName = rawCursor.getString(TEAM_NAME_COLUMN_POSITION);
            String comments = rawCursor.getString(COMMENTS_COLUMN_POSITION);
            int totalShots = rawCursor.getInt(TOTAL_SHOTS_COLUMN_POSITION);
            int highGoals = rawCursor.getInt(HIGH_GOALS_COLUMN_POSITION);
            int lowGoals = rawCursor.getInt(LOW_GOALS_COLUMN_POSITION);
            int defPortCullisCrosses = rawCursor.getInt(PORT_CULLIS_CROSSES_COLUMN_POSITION);
            int defChivalDeFriseCrosses = rawCursor.getInt(CHIVAL_DE_FRISE_CROSSES_COLUMN_POSITION);
            int defRampartsCrosses = rawCursor.getInt(RAMPARTS_CROSSES_COLUMN_POSITION);
            int defRockWallCrosses = rawCursor.getInt(ROCK_WALL_CROSSES_COLUMN_POSITION);
            int defRoughTerrainCrosses = rawCursor.getInt(ROUGH_TERRAIN_CROSSES_COLUMN_POSITION);
            int defSallyPortCrosses = rawCursor.getInt(SALLY_PORT_CROSSES_COLUMN_POSITION);
            int defDrawBridgeCrosses = rawCursor.getInt(DRAW_BRIDGE_CROSSES_COLUMN_POSITION);
            int defMoatCrosses = rawCursor.getInt(MOAT_CROSSES_COLUMN_POSITION);
            int defLowBarCrosses = rawCursor.getInt(LOW_BAR_CROSSES_COLUMN_POSITION);
            int endGameType = rawCursor.getInt(END_GAME_TYPE_COLUMN_POSITION);
            int autonomousUsage = rawCursor.getInt(AUTONOMOUS_USAGE_COLUMN_POSITION);
            int matchID = rawCursor.getInt(MATCH_ID_COLUMN_POSITION);
            int Score = rawCursor.getInt(SCORE_COLUMN_POSITION);

            stats.add(new Statistics(autonomousUsage, defChivalDeFriseCrosses, defDrawBridgeCrosses, endGameType,
                    highGoals, defLowBarCrosses, lowGoals, comments, id, defMoatCrosses, teamName, teamNum, defPortCullisCrosses,
                    defRampartsCrosses, defRockWallCrosses, defSallyPortCrosses, defRoughTerrainCrosses, totalShots, matchID, Score));
        }

        rawCursor.close();
        mDatabase.close();

        return stats;
    }

    public Statistics getTeam(int teamNum, int MatchID) {
        Statistics stats = null;
        mDatabase = mDbOpenHelper.getReadableDatabase();
        //Cursor rawCursor = null;
        //String queryString = "SELECT * FROM Team WHERE teamNum = " + teamNum + ";";
        Cursor rawCursor
                = mDatabase.query(TABLE_NAME,
                null,
                TEAM_NUM_COLUMN + " = " + teamNum + " AND " + MATCH_ID_COLUMN + " = " + MatchID,
                null, null, null, TEAM_NUM_COLUMN);
        while (rawCursor.moveToNext()) {
            long id = rawCursor.getLong(ID_COLUMN_POSITION);
//            int teamNum = rawCursor.getInt(TEAM_NUM_COLUMN_POSITION);
            String teamName = rawCursor.getString(TEAM_NAME_COLUMN_POSITION);
            String comments = rawCursor.getString(COMMENTS_COLUMN_POSITION);
            int totalShots = rawCursor.getInt(TOTAL_SHOTS_COLUMN_POSITION);
            int highGoals = rawCursor.getInt(HIGH_GOALS_COLUMN_POSITION);
            int lowGoals = rawCursor.getInt(LOW_GOALS_COLUMN_POSITION);
            int defPortCullisCrosses = rawCursor.getInt(PORT_CULLIS_CROSSES_COLUMN_POSITION);
            int defChivalDeFriseCrosses = rawCursor.getInt(CHIVAL_DE_FRISE_CROSSES_COLUMN_POSITION);
            int defRampartsCrosses = rawCursor.getInt(RAMPARTS_CROSSES_COLUMN_POSITION);
            int defRockWallCrosses = rawCursor.getInt(ROCK_WALL_CROSSES_COLUMN_POSITION);
            int defRoughTerrainCrosses = rawCursor.getInt(ROUGH_TERRAIN_CROSSES_COLUMN_POSITION);
            int defSallyPortCrosses = rawCursor.getInt(SALLY_PORT_CROSSES_COLUMN_POSITION);
            int defDrawBridgeCrosses = rawCursor.getInt(DRAW_BRIDGE_CROSSES_COLUMN_POSITION);
            int defMoatCrosses = rawCursor.getInt(MOAT_CROSSES_COLUMN_POSITION);
            int defLowBarCrosses = rawCursor.getInt(LOW_BAR_CROSSES_COLUMN_POSITION);
            int endGameType = rawCursor.getInt(END_GAME_TYPE_COLUMN_POSITION);
            int autonomousUsage = rawCursor.getInt(AUTONOMOUS_USAGE_COLUMN_POSITION);
            int matchID = rawCursor.getInt(MATCH_ID_COLUMN_POSITION);
            int Score = rawCursor.getInt(SCORE_COLUMN_POSITION);
            stats = new Statistics(autonomousUsage, defChivalDeFriseCrosses, defDrawBridgeCrosses, endGameType,
                    highGoals, defLowBarCrosses, lowGoals, comments, id, defMoatCrosses, teamName, teamNum, defPortCullisCrosses,
                    defRampartsCrosses, defRockWallCrosses, defSallyPortCrosses, defRoughTerrainCrosses, totalShots, matchID, Score);
        }

        rawCursor.close();
        mDatabase.close();

        return stats;
    }

    public int getCount(int teamNum) {

        mDatabase = mDbOpenHelper.getWritableDatabase();

        int count = 0;
        Cursor rawCursor
                = mDatabase.query(TABLE_NAME,
                null,
                TEAM_NUM_COLUMN + " = " + teamNum,
                null, null, null, TEAM_NUM_COLUMN);

        count = rawCursor.getCount();
        rawCursor.close();
        mDatabase.close();

        return count;
    }

    public void deleteTeam(int teamNumber) {
        mDatabase = mDbOpenHelper.getWritableDatabase();
        mDatabase.delete(TABLE_NAME, "teamNum = ?", new String[]{Integer.toString(teamNumber)});
    }

    public void deleteTeam(int teamNumber, int matchID, List<Statistics> stats1) {
        mDatabase = mDbOpenHelper.getWritableDatabase();

        if (!mDatabase.isOpen()) Log.w("DB stats not open", "Database Problem");

        List<Statistics> stats = stats1;
        mDatabase.delete(TABLE_NAME, "teamNum = ?" + " AND " + "MatchID = ?",
                new String[]{Integer.toString(teamNumber), Integer.toString(matchID)});


        int i;

        for (i = (matchID); i < stats.size(); i++) {
            Statistics statistics = stats.get(i);
            int oldMatchID = statistics.getMatchID();
            int newMatchID = oldMatchID - 1;
            updateMatchID(teamNumber, oldMatchID, newMatchID);
        }
    }

    public void GenerateCSV() {

        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        File dbFile = new File(db.getPath());
        File exportDir = new File(Environment.getExternalStorageDirectory() + File.separator + "ScoutingCSV");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "StatisticsExport" + System.currentTimeMillis() + ".csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = db.rawQuery("SELECT * FROM TeamStats", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2),
                        curCSV.getString(3), curCSV.getString(4), curCSV.getString(5), curCSV.getString(6)
                        , curCSV.getString(7), curCSV.getString(8), curCSV.getString(9), curCSV.getString(10)
                        , curCSV.getString(11), curCSV.getString(12), curCSV.getString(13), curCSV.getString(14)
                        , curCSV.getString(15), curCSV.getString(16), curCSV.getString(17), curCSV.getString(18)
                        , curCSV.getString(19)};
                csvWrite.writeNext(arrStr);
            }
//            SharedPreferences prefs = getSharedPreferences("Weights", MODE_PRIVATE);
//            int ChallengeWeight = prefs.getInt("ChallengeWeight", 0);
//            int AutonomousWeight = prefs.getInt("AutonomousWeight", 0);
//            int ScaleWeight = prefs.getInt("ScaleWeight", 0);
//            int LowGoalWeight = prefs.getInt("LowGoalWeight", 0);
//            int HighGoalWeight = prefs.getInt("HighGoalWeight", 0);
//            int ChivalDeFriseWeight = prefs.getInt("ChivalDeFriseWeight", 0);
//            int MoatWeight = prefs.getInt("MoatWeight", 0);
//            int RampartsWeight = prefs.getInt("RampartsWeight", 0);
//            int LowBarWeight = prefs.getInt("LowBarWeight", 0);
//            int SallyPortWeight = prefs.getInt("SallyPortWeight", 0);
//            int PortCullisWeight = prefs.getInt("PortCullisWeight", 0);
//            int RockWallWeight = prefs.getInt("RockWallWeight", 0);
//            int RoughTerrainWeight = prefs.getInt("RoughTerrainWeight", 0);
//            int DrawBridgeWeight = prefs.getInt("DrawBridgeWeight", 0);
//            String arrStrHeadings[] = {"Weights" , };
//            String arrStr[] = {};
            csvWrite.close();
            curCSV.close();
        } catch (Exception sqlEx) {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }

    }

}
