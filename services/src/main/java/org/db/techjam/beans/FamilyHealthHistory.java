package org.db.techjam.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class FamilyHealthHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3178353741996842690L;

	public ArrayList<FamilyMember> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(ArrayList<FamilyMember> familyMembers) {
		this.familyMembers = familyMembers;
	}

	private ArrayList<FamilyMember> familyMembers;
}
