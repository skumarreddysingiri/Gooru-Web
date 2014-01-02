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
package org.ednovo.gooru.client.mvp.home.event;

import com.google.gwt.event.shared.GwtEvent;
/**
 * 
 * @fileName : SetDiscoverLinkEvent.java
 *
 * @description : This event is sent to the {@link com.gwtplatform.mvp.client.EventBus},
 * whenever the user click on the discover tab it will be processed by presenters that are responsible for displaying related views.
 *
 * @version : 1.0
 *
 * @date: 30-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class SetDiscoverLinkEvent extends GwtEvent<SetDiscoverLinkHandler> {

	public static final Type<SetDiscoverLinkHandler> TYPE = new Type<SetDiscoverLinkHandler>();

	private String discoverLink;

	/**
	 * Class constructor , assign tab type
	 */
	public SetDiscoverLinkEvent(String discoverLink) {
		this.discoverLink = discoverLink;
	}
	/**
	 * This method is used to get the discover link.
	 */
	@Override
	public Type<SetDiscoverLinkHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SetDiscoverLinkHandler handler) {
		handler.setDiscoverLink(discoverLink);
	}

}
