package com.db.techJam.client.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class MyCarePlan extends Composite {

	MyCarePlanUiBinder uiBinder = GWT.create(MyCarePlanUiBinder.class);
	interface MyCarePlanUiBinder extends UiBinder<Widget, MyCarePlan>{}
	
	@UiField
	CellTable<MyCarePlanModel> cellList;
	
	public MyCarePlan() {
		initWidget(uiBinder.createAndBindUi(this));
		setUpGrid(cellList);
		cellList.getSelectionModel().addSelectionChangeHandler(new Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				
			}
		});
		cellList.setRowData(getRowData());
	}
	
	private List<MyCarePlanModel> getRowData() {
		List<MyCarePlanModel> retList = new ArrayList<MyCarePlanModel>();

		MyCarePlanModel row1 = new MyCarePlanModel("Pre-Admission ","Communication and Translation ","Assess barrier ");
		retList.add(row1);
		MyCarePlanModel row2 = new MyCarePlanModel("Post-Admission ","Physical Therapy ","Secure medical appointments ");
		retList.add(row2);
		MyCarePlanModel row3 = new MyCarePlanModel("Stable - Physically","Education ","Identify educational benefits ");
		retList.add(row3);
		MyCarePlanModel row4 = new MyCarePlanModel("Pre-Discharge ","Awards and Decorations ","Complete necessary paperwork ");
		retList.add(row4);
		return retList;
	}

	private void setUpGrid(final CellTable<MyCarePlanModel> preAdmn1) {
		
		preAdmn1.setSelectionModel(new MultiSelectionModel());

		preAdmn1.addColumn(new Column<MyCarePlanModel, String>(new TextCell()) {

			@Override
			public String getValue(MyCarePlanModel object) {
				return object.getSequenceNo();
			}

		},"Sequence");

		preAdmn1.addColumn(new Column<MyCarePlanModel, String>(new TextCell()) {

			@Override
			public String getValue(MyCarePlanModel object) {
				return object.getCategory();
			}
		},"Category");

		
		preAdmn1.addColumn(new Column<MyCarePlanModel, String>(new TextCell()) {

			@Override
			public String getValue(MyCarePlanModel object) {
				return object.getActivity();
			}
		},"Activities");
		
		preAdmn1.addColumn(new Column<MyCarePlanModel,Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(MyCarePlanModel object) {
				return preAdmn1.getSelectionModel().isSelected(object);
			}
		},"Status");
	}
	
	@UiHandler("doneBtn")
	void handleDoneClicked(ClickEvent event){
		Set<MyCarePlanModel> selectedSet = ((MultiSelectionModel<MyCarePlanModel>)cellList.getSelectionModel()).getSelectedSet();
		List<MyCarePlanModel> visibleList = new ArrayList<MyCarePlanModel>(cellList.getVisibleItems());
		for(MyCarePlanModel model : selectedSet){
			visibleList.remove(model);
		}
		cellList.setRowData(visibleList);
	}


	
}
