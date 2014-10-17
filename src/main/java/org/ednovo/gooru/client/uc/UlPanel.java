package org.ednovo.gooru.client.uc;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Widget;
public class UlPanel extends ComplexPanel implements InsertPanel,HasMouseOverHandlers,HasMouseOutHandlers{
	public UlPanel() {
		setElement(Document.get().createULElement());
	}

	@Override
	public void add(Widget w){
		add(w,getElement());
	}
	
	public void insert(Widget w,int beforeIndex){
		insert(w, getElement(), beforeIndex, true);
	}

	
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, MouseOverEvent.getType());
	}

	
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, MouseOutEvent.getType());
	}

}