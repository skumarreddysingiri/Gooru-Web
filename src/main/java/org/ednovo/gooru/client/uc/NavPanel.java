package org.ednovo.gooru.client.uc;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Widget;

public class NavPanel extends ComplexPanel implements InsertPanel {
	String role;
	public NavPanel() {
		setElement(Document.get().createElement("nav"));
    }
	
	@Override
	public void add(Widget w){
	add(w,getElement());
	}

	@Override
	public void insert(Widget w, int beforeIndex) {
		insert(w, getElement(), beforeIndex, true);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		getElement().setAttribute("role",role);
		this.role = role;
	}
	
}
