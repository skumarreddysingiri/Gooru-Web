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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.shared.model.search.SearchFilterDo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @fileName : SearchFilterDeSerialier.java
 *
 * @description : This class is used to deserialize search filters.
 *
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
public class SearchFilterDeSerialier extends DeSerializer {

	private static final String GRADE_LEVEL = "gradeLevels";
	private static final String SUBJECT = "subjects";
	private static final String RESOURCE = "resource";

	private static Map<String, String> COLLECTION_CATEGORIES = new LinkedHashMap<String, String>();

	private static Map<String, String> RESOURCE_CATEGORIES = new LinkedHashMap<String, String>();

	{
		COLLECTION_CATEGORIES.put("onlyQuestion", "Show only Quizzes");
		//COLLECTION_CATEGORIES.put("NoQuestion", "None (___)");
		//COLLECTION_CATEGORIES.put("all", "Mixed (both Quizzes and___)");

		RESOURCE_CATEGORIES.put("Video", "Videos");
		RESOURCE_CATEGORIES.put("Website", "Websites");
		RESOURCE_CATEGORIES.put("Interactive", "Interactives");
		RESOURCE_CATEGORIES.put("Question", "Questions");
		RESOURCE_CATEGORIES.put("Slide", "Slides");
		RESOURCE_CATEGORIES.put("Textbook", "Textbooks");
		RESOURCE_CATEGORIES.put("Handout", "Handouts");
		RESOURCE_CATEGORIES.put("Lesson", "Lessons");
		RESOURCE_CATEGORIES.put("Exam", "Exams");
	}

	/**
	 * Deserialize json object to {@link SearchFilterDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @param type of search
	 * @return search filter
	 */
	public SearchFilterDo deserializeSearchFilter(JsonRepresentation jsonRep, String type) {
		SearchFilterDo filterDo = new SearchFilterDo();

		try {
			JSONObject taxonomyJsonObject = jsonRep.getJsonObject();

			JSONObject grades = taxonomyJsonObject.getJSONObject(GRADE_LEVEL);
			HashMap<String, String> gradeLevels;
			try {
				gradeLevels = new ObjectMapper().readValue(grades.toString(), new TypeReference<LinkedHashMap<String, String>>() {
				});
				filterDo.setGradeLevels(gradeLevels);
			} catch (Exception e) {
				e.printStackTrace();
			}

			JSONArray subjectsJsonArray = taxonomyJsonObject.getJSONArray(SUBJECT);
			List<String> subjects = new ArrayList<String>();
			for (int subject = 0; subject < subjectsJsonArray.length(); subject++) {
				subjects.add(subjectsJsonArray.getString(subject));
			}
			filterDo.setSubjects(subjects);
			if (type.equals(RESOURCE)) {
				filterDo.setCategories(RESOURCE_CATEGORIES);
			} else {
				filterDo.setCategories(COLLECTION_CATEGORIES);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return filterDo;
	}
}
