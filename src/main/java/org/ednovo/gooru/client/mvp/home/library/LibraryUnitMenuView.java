/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.client.mvp.home.library;

import org.ednovo.gooru.shared.model.library.PartnerFolderDo;
import org.ednovo.gooru.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.shared.model.library.UnitDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LibraryUnitMenuView extends Composite {

	@UiField Label unitMenuItem;
	
	@UiField LibraryStyleBundle libraryStyleUc;
	
	private String unitId;
	
	private Integer childCount;
	
	private Integer widgetCount;
	
	private String type;
	
	private static LibraryUnitMenuViewUiBinder uiBinder = GWT
			.create(LibraryUnitMenuViewUiBinder.class);

	interface LibraryUnitMenuViewUiBinder extends
			UiBinder<Widget, LibraryUnitMenuView> {
	}

	public LibraryUnitMenuView(UnitDo unitDo) {
		initWidget(uiBinder.createAndBindUi(this));
		if(unitDo.getLabel()=="Popular"){
			unitMenuItem.setStyleName(libraryStyleUc.popularStarImage());
		}
		unitMenuItem.setText(unitDo.getLabel());
		unitMenuItem.getElement().setAttribute("alt",unitDo.getLabel());
		unitMenuItem.getElement().setAttribute("title",unitDo.getLabel());
		setUnitId(""+unitDo.getCodeId());
		if(unitDo.getCount()==null) {
			setChildCount(0);	
		} else {
			setChildCount(unitDo.getCount());
		}
		setIds();
	}

	public LibraryUnitMenuView(PartnerFolderDo folderDo) {
		initWidget(uiBinder.createAndBindUi(this));
		unitMenuItem.setText(folderDo.getTitle());
		unitMenuItem.getElement().setAttribute("alt",folderDo.getTitle());
		unitMenuItem.getElement().setAttribute("title",folderDo.getTitle());
		setUnitId(folderDo.getGooruOid());
		setIds();
	}

	public LibraryUnitMenuView(ProfileLibraryDo folderDo) {
		initWidget(uiBinder.createAndBindUi(this));
		unitMenuItem.setText(folderDo.getTitle());
		unitMenuItem.getElement().setAttribute("alt",folderDo.getTitle());
		unitMenuItem.getElement().setAttribute("title",folderDo.getTitle());
		setUnitId(folderDo.getGooruOid());
		setIds();
	}
	public void setIds(){
		unitMenuItem.getElement().setId("lblUnitMenuItem");
	}
	public Label getUnitMenuItemPanel() {
		return unitMenuItem;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Integer getWidgetCount() {
		return widgetCount;
	}

	public void setWidgetCount(Integer widgetCount) {
		this.widgetCount = widgetCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}