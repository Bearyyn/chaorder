package com.chaorder.aitech.pojo;

public class Zyzb {
	
	public Zyzb(String ssgsdm, String ssgsmc, String bbzycpmc, double zb, String tpdycpmc, String zycpgxsj,
			String ssgsgshy, String qtdthyflgs) {
		this.ssgsdm = ssgsdm;
		this.ssgsmc = ssgsmc;
		this.bbzycpmc = bbzycpmc;
		this.zb = zb;
		this.tpdycpmc = tpdycpmc;
		this.zycpgxsj = zycpgxsj;
		this.ssgsgshy = ssgsgshy;
		this.qtdthyflgs = qtdthyflgs;
	}
	
	public Zyzb() {
		this("", "", "", 0, "", "", "", "");
	}

	public String getSsgsdm() {
		return ssgsdm;
	}
	public void setSsgsdm(String ssgsdm) {
		this.ssgsdm = ssgsdm;
	}
	public String getSsgsmc() {
		return ssgsmc;
	}
	public void setSsgsmc(String ssgsmc) {
		this.ssgsmc = ssgsmc;
	}
	public String getBbzycpmc() {
		return bbzycpmc;
	}
	public void setBbzycpmc(String bbzycpmc) {
		this.bbzycpmc = bbzycpmc;
	}
	public double getZb() {
		return zb;
	}
	public void setZb(double zb) {
		this.zb = zb;
	}
	public String getTpdycpmc() {
		return tpdycpmc;
	}
	public void setTpdycpmc(String tpdycpmc) {
		this.tpdycpmc = tpdycpmc;
	}
	public String getZycpgxsj() {
		return zycpgxsj;
	}
	public void setZycpgxsj(String zycpgxsj) {
		this.zycpgxsj = zycpgxsj;
	}
	public String getSsgsgshy() {
		return ssgsgshy;
	}
	public void setSsgsgshy(String ssgsgshy) {
		this.ssgsgshy = ssgsgshy;
	}
	public String getQtdthyflgs() {
		return qtdthyflgs;
	}
	public void setQtdthyflgs(String qtdthyflgs) {
		this.qtdthyflgs = qtdthyflgs;
	}
	
	private String ssgsdm;
	private String ssgsmc;
	private String bbzycpmc;
	private double zb;
	private String tpdycpmc;
	private String zycpgxsj;
	private String ssgsgshy;
	private String qtdthyflgs;
}
