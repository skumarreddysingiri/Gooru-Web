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
package org.ednovo.gooru.client.mvp.shelf.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.PopupPanel;
/**
 * @fileName : GetEditPageHeightEvent.java
 *
 * @description : This event is sent to the {@link com.gwtplatform.mvp.client.EventBus},
 * and it will check the edit page height is cleared or not.
 *
 * @version : 1.0
 *
 * @date: 02-Jan-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
	public class GetEditPageHeightEvent extends GwtEvent<GetEditPageHeightEventHandler> {
		public static final Type<GetEditPageHeightEventHandler> TYPE = new Type<GetEditPageHeightEventHandler>();
		private PopupPanel editQuestionPopupPanel;
		private boolean isHeightClear;
	public GetEditPageHeightEvent(PopupPanel editQuestionPopupPanel,boolean isHeightClear){
		this.editQuestionPopupPanel=editQuestionPopupPanel;
		this.isHeightClear=isHeightClear;
	}
	@Override
	public Type<GetEditPageHeightEventHandler> getAssociatedType() {
		return TYPE;
	}
	@Override
	protected void dispatch(GetEditPageHeightEventHandler handler) {
		handler.getEditPageHeight(editQuestionPopupPanel,isHeightClear);
	}
}
