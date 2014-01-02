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
/**
 * 
 */
package org.ednovo.gooru.client.mvp.shelf.event;

import org.ednovo.gooru.shared.model.content.CollectionItemDo;

import com.google.gwt.event.shared.GwtEvent;
/**
 * @fileName : AssignmentEvent.java
 *
 * @description :  This event is sent to the {@link com.gwtplatform.mvp.client.EventBus},
 * whenever the user clicks on the assignment it will handle the event.
 *
 * @version : 1.0
 *
 * @date: 02-Jan-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class AssignmentEvent extends GwtEvent<AssignmentHandler> {

	public static final Type<AssignmentHandler> TYPE = new Type<AssignmentHandler>();
	
	CollectionItemDo collectionItemDo = null;

	/**
	 * Class constructor
	 */
	public AssignmentEvent(CollectionItemDo collectionItemDo) {
		this.collectionItemDo = collectionItemDo;
	}

	@Override
	public Type<AssignmentHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AssignmentHandler handler) {
		handler.insertAssignment(collectionItemDo);
	}

}
