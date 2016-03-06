package com.example.gearbox.scoutingappredux;

import java.io.Serializable;

/**
 * Created by nicknagi on 06/03/16.
 */
public class Statistics implements Serializable {

    private long mDBid;

    private int mTeamNum;

    private String mTeamName;

    private String mComments;

    private int TotalShots;

    private int LowGoals;

    private int HighGoals;

    private int PortCullisCrosses;

    private int ChivalDeFriseCrosses;

    private int RampartsCrosses;

    private int MoatCrosses;

    private int DrawBridgeCrosses;

    private int SallyPortCrosses;

    private int RoughTerrainCrosses;

    private int RockWallCrosses;

    private int LowBarCrosses;

    private int EndGameType;

    private int AutonomousUsage;

    public Statistics(int autonomousUsage, int chivalDeFriseCrosses, int drawBridgeCrosses, int endGameType,
                      int highGoals, int lowBarCrosses, int lowGoals, String mComments, long mDBid, int moatCrosses,
                      String mTeamName, int mTeamNum, int portCullisCrosses, int rampartsCrosses, int rockWallCrosses,
                      int sallyPortCrosses, int roughTerrainCrosses, int shots) {
        AutonomousUsage = autonomousUsage;
        ChivalDeFriseCrosses = chivalDeFriseCrosses;
        DrawBridgeCrosses = drawBridgeCrosses;
        EndGameType = endGameType;
        HighGoals = highGoals;
        LowBarCrosses = lowBarCrosses;
        LowGoals = lowGoals;
        this.mComments = mComments;
        this.mDBid = mDBid;
        MoatCrosses = moatCrosses;
        this.mTeamName = mTeamName;
        this.mTeamNum = mTeamNum;
        PortCullisCrosses = portCullisCrosses;
        RampartsCrosses = rampartsCrosses;
        RockWallCrosses = rockWallCrosses;
        SallyPortCrosses = sallyPortCrosses;
        RoughTerrainCrosses = roughTerrainCrosses;
        TotalShots = shots;
    }

    public int getAutonomousUsage() {
        return AutonomousUsage;
    }

    public void setAutonomousUsage(int autonomousUsage) {
        AutonomousUsage = autonomousUsage;
    }

    public int getChivalDeFriseCrosses() {
        return ChivalDeFriseCrosses;
    }

    public void setChivalDeFriseCrosses(int chivalDeFriseCrosses) {
        ChivalDeFriseCrosses = chivalDeFriseCrosses;
    }

    public int getDrawBridgeCrosses() {
        return DrawBridgeCrosses;
    }

    public void setDrawBridgeCrosses(int drawBridgeCrosses) {
        DrawBridgeCrosses = drawBridgeCrosses;
    }

    public int getEndGameType() {
        return EndGameType;
    }

    public void setEndGameType(int endGameType) {
        EndGameType = endGameType;
    }

    public int getHighGoals() {
        return HighGoals;
    }

    public void setHighGoals(int highGoals) {
        HighGoals = highGoals;
    }

    public int getLowBarCrosses() {
        return LowBarCrosses;
    }

    public void setLowBarCrosses(int lowBarCrosses) {
        LowBarCrosses = lowBarCrosses;
    }

    public String getmComments() {
        return mComments;
    }

    public void setmComments(String mComments) {
        this.mComments = mComments;
    }

    public int getLowGoals() {
        return LowGoals;
    }

    public void setLowGoals(int lowGoals) {
        LowGoals = lowGoals;
    }

    public long getmDBid() {
        return mDBid;
    }

    public void setmDBid(long mDBid) {
        this.mDBid = mDBid;
    }

    public int getMoatCrosses() {
        return MoatCrosses;
    }

    public void setMoatCrosses(int moatCrosses) {
        MoatCrosses = moatCrosses;
    }

    public String getmTeamName() {
        return mTeamName;
    }

    public void setmTeamName(String mTeamName) {
        this.mTeamName = mTeamName;
    }

    public int getmTeamNum() {
        return mTeamNum;
    }

    public void setmTeamNum(int mTeamNum) {
        this.mTeamNum = mTeamNum;
    }

    public int getPortCullisCrosses() {
        return PortCullisCrosses;
    }

    public void setPortCullisCrosses(int portCullisCrosses) {
        PortCullisCrosses = portCullisCrosses;
    }

    public int getRampartsCrosses() {
        return RampartsCrosses;
    }

    public void setRampartsCrosses(int rampartsCrosses) {
        RampartsCrosses = rampartsCrosses;
    }

    public int getRockWallCrosses() {
        return RockWallCrosses;
    }

    public void setRockWallCrosses(int rockWallCrosses) {
        RockWallCrosses = rockWallCrosses;
    }

    public int getSallyPortCrosses() {
        return SallyPortCrosses;
    }

    public void setSallyPortCrosses(int sallyPortCrosses) {
        SallyPortCrosses = sallyPortCrosses;
    }

    public int getRoughTerrainCrosses() {
        return RoughTerrainCrosses;
    }

    public void setRoughTerrainCrosses(int roughTerrainCrosses) {
        RoughTerrainCrosses = roughTerrainCrosses;
    }

    public int getTotalShots() {
        return TotalShots;
    }

    public void setTotalShots(int shots) {
        TotalShots = shots;
    }
}
