package com.db.techJam.client.careplans;

import java.util.ArrayList;
import java.util.List;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AllCarePlans extends Composite {

	AllCarePlansUiBinder uiBinder = GWT.create(AllCarePlansUiBinder.class);
	interface AllCarePlansUiBinder extends UiBinder<Widget, AllCarePlans>{}
	
	@UiField
	CellTable<String> preAdmn1;
	
	public AllCarePlans() {
		initWidget(uiBinder.createAndBindUi(this));
		setupGrid(preAdmn1);
		preAdmn1.setRowData(getListofStringForPreAdm1());
	}
	
	List<String >getListofStringForPreAdm1(){
		ArrayList<String> retList = new ArrayList<String>();
		retList.add("Discuss process ");
		retList.add("Verify MPA (medical power of attorney) ");
		retList.add("Verify advanced directives ");
		retList.add("Provide copies to health care team ");
		retList.add("Complete advanced directives ");
		retList.add("Notarize or witness advanced directives ");
		retList.add("Submit form to medical facility ");
		retList.add("Monitor progress ");
		
		return retList;
	}

	private void setupGrid(CellTable<String> preAdmn1) {
		
		
		preAdmn1.addColumn(new Column<String, String>(new TextCell()) {

			@Override
			public String getValue(String object) {
				return object;
			}
		},"Activities");
		
		preAdmn1.addColumn(new Column<String,Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(String object) {
				return false;
			}
		},"Status");
	}
	
	
}
