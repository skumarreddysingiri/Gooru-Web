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
package org.ednovo.gooru.server.deserializer;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.server.serializer.JsonDeserializer;
import org.ednovo.gooru.shared.model.featured.FeaturedCollectionContentDo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.springframework.stereotype.Component;
/**
 * @fileName : FeaturedContentDeSerializer.java
 *
 * @description : This class is used to deserialize featured content.
 *
 * @version : 1.0
 *
 * @date: 31-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
@Component
public class FeaturedContentDeSerializer extends DeSerializer{
	/**
	 * Deserialize json object to list of {@link FeaturedCollectionContentDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return list of featured collections
	 */
	public List<FeaturedCollectionContentDo> deSerializer(JsonRepresentation jsonRep){
		
		List<FeaturedCollectionContentDo> featuredContents = new ArrayList<FeaturedCollectionContentDo>();
		try {
			if(jsonRep != null){
				JSONArray featuredContentJsonArray = jsonRep.getJsonArray();
					for(int index=0; index < featuredContentJsonArray.length(); index++){
						JSONObject featuredContentJsonObj = featuredContentJsonArray.getJSONObject(index);
						featuredContents.add(JsonDeserializer.deserialize(featuredContentJsonObj.toString(), FeaturedCollectionContentDo.class));
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return featuredContents;
	}
}
