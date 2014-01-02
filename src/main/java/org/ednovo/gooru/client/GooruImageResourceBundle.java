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
package org.ednovo.gooru.client;

import org.ednovo.gooru.client.gin.AppClientFactory;
/**
 * 
 * @fileName : GooruImageResourceBundle.java
 *
 * @description : This file is used to get the images.
 *
 * @version : 1.0
 *
 * @date: 26-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class GooruImageResourceBundle{
	public static final String CDN_END_POINT = AppClientFactory.getLoggedInUser().getSettings().getCdnEndPoint();
	
	public static String getGooruLogoSmall(){
	     return "url("+CDN_END_POINT+"/images/core/gooru-logo-small.png)";
	}
	
	public static String getLandingPageSprite(){
	     return "url("+CDN_END_POINT+"/images/landing-page-sprite.png)";
	}
	
	public static String getLandingPageBanner(){
	     return "url("+CDN_END_POINT+"/images/landing-page/banner.png)";
	}
	
	public static String getDotsImage(){
	     return "url("+CDN_END_POINT+"/images/landing-page/dots.png)";
	}
	
	public static String getReturnToTop(){
	     return "url("+CDN_END_POINT+"/images/landing-page/to-top.png)";
	}

	public static String getStartedImage(){
	     return "url("+CDN_END_POINT+"/images/landing-page/get-started.png)";
	}
	
	public static String getSupportersSprite(){
	     return "url("+CDN_END_POINT+"/images/landing-page/supportersSprite.png)";
	}
}
