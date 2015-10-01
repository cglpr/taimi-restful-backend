package taimi.domain.skill;

/**
 * 
 * @author vpotry
 *
 */
public class SkillDemand implements Comparable<SkillDemand> {
	private String skill;
	private int demandCnt;
	
	public SkillDemand() {
	}
	
	public SkillDemand(String skill, int demandCnt) {
		this.skill = skill;
		this.demandCnt = demandCnt;
	}
	
	public String getSkill() {
		return skill;
	}
	
	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	public int getDemandCnt() {
		return demandCnt;
	}
	
	public void setDemandCnt(int demandCnt) {
		this.demandCnt = demandCnt;
	}

	@Override
	public int compareTo(SkillDemand sd) {
		return sd.getDemandCnt() - this.demandCnt;
	}
	
	
}
