package com.example.a3dstep.Model;

public class RankYear {
	private String nameRankYear;
	private int rankYear;
	private int stepYear;

	public RankYear(String nameRankYear, int rankYear, int stepYear) {
		this.nameRankYear = nameRankYear;
		this.rankYear = rankYear;
		this.stepYear = stepYear;
	}

	public String getNameRankYear() {
		return nameRankYear;
	}

	public void setNameRankYear(String nameRankYear) {
		this.nameRankYear = nameRankYear;
	}

	public int getRankYear() {
		return rankYear;
	}

	public void setRankYear(int rankYear) {
		this.rankYear = rankYear;
	}

	public int getStepYear() {
		return stepYear;
	}

	public void setStepYear(int stepYear) {
		this.stepYear = stepYear;
	}
}