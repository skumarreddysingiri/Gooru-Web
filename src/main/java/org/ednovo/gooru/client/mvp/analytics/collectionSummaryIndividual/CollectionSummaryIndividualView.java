package org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ednovo.gooru.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsReactionWidget;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsTabContainer;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.client.mvp.analytics.util.DataView;
import org.ednovo.gooru.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.shared.model.analytics.MetaDataDo;
import org.ednovo.gooru.shared.model.analytics.UserDataDo;

import com.google.gwt.ajaxloader.client.Properties;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;

public class CollectionSummaryIndividualView  extends BaseViewWithHandlers<CollectionSummaryIndividualUiHandlers> implements IsCollectionSummaryIndividualView  {

	private static CollectionSummaryIndividualViewUiBinder uiBinder = GWT
			.create(CollectionSummaryIndividualViewUiBinder.class);

	interface CollectionSummaryIndividualViewUiBinder extends
			UiBinder<Widget, CollectionSummaryIndividualView> {
	}
	
	@UiField HTMLPanel totalAvgReactionlbl,tabContainer,individualScoredData,individualOpenendedData,individualScoredDatapnl,individualResourceBreakdownDatapnl,individualResourceBreakdownData;
	@UiField ListBox filterDropDown;
	@UiField Label totalTimeSpentlbl,totalViewlbl;
	
	AnalyticsTabContainer individualTabContainer;
	DataView operationsView;
	CollectionSummaryIndividualCBundle res;
	private int collectionProgressCount=1;
	
	final List<Integer> questionRowIndex=new ArrayList<Integer>();
	final List<Integer> resourceRowIndex=new ArrayList<Integer>();
	
	final String INCORRECT="#db0f0f",CORRECT="#4d9645",ONMULTIPULEATTEMPTS="#FBB03B";
	final String SCORED="scoredTab",OPENENDED="openendedTab",BREAKDOWN="breakdownTab";
	
	ArrayList<UserDataDo> questionsData=new ArrayList<UserDataDo>();
	ArrayList<UserDataDo> openendedData=new ArrayList<UserDataDo>();
	
	public CollectionSummaryIndividualView() {
		this.res = CollectionSummaryIndividualCBundle.INSTANCE;
		res.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		setData();
	}
	void hideAllPanels(){
		individualScoredDatapnl.setVisible(false);
		individualOpenendedData.setVisible(false);
		individualResourceBreakdownDatapnl.setVisible(false);
	}
	void setData(){
		individualTabContainer=new AnalyticsTabContainer() {
			@Override
			public void onTabClick(String tabClicked) {
				if(tabClicked.equalsIgnoreCase(SCORED)){
					hideAllPanels();
					individualScoredDatapnl.setVisible(true);
				}else if(tabClicked.equalsIgnoreCase(OPENENDED)){
					hideAllPanels();
					individualOpenendedData.setVisible(true);
				}else if(tabClicked.equalsIgnoreCase(BREAKDOWN)){
					hideAllPanels();
					individualResourceBreakdownDatapnl.setVisible(true);
				}
			}
		};
		tabContainer.add(individualTabContainer);
		filterDropDown.clear();
        filterDropDown.addItem("All", "All");
        filterDropDown.addItem("Questions", "Questions");
        filterDropDown.addItem("Resources", "Resources");
	}
	@Override
	public void setIndividualData(ArrayList<UserDataDo> result) {
			hideAllPanels();
			individualScoredDatapnl.setVisible(true);
			
			individualScoredData.clear();
			individualOpenendedData.clear();
			individualResourceBreakdownData.clear();
			questionsData.clear();
			openendedData.clear();

			collectionProgressCount=0;
			questionRowIndex.clear();
			resourceRowIndex.clear();
			
			for (UserDataDo userDataDo : result) {
				if(userDataDo.getCategory().equalsIgnoreCase("question")){
					if(!userDataDo.getType().equalsIgnoreCase("OE")){
						questionsData.add(userDataDo);
					}else{
						openendedData.add(userDataDo);
					}
					questionRowIndex.add(collectionProgressCount);
				}else{
					resourceRowIndex.add(collectionProgressCount);
				}
				collectionProgressCount++;
			}
			setQuestionsData(questionsData);
			setOpenendedQuestionsData(openendedData);
			setCollectionBreakDown(result);
	}
	void setCollectionBreakDown(ArrayList<UserDataDo> result){
		
		final int[] primitivesQuestions = AnalyticsUtil.toIntArray(questionRowIndex);
		final int[] primitivesResources = AnalyticsUtil.toIntArray(resourceRowIndex);
		
			UserDataDo maxAvgValue=Collections.max(result,new Comparator<UserDataDo>() {
	        	public int compare(UserDataDo o1, UserDataDo o2) {
	        		 Long obj1 = new Long(o1.getTimeSpent());
	        	     Long obj2 = new Long(o2.getTimeSpent());
	        	     return obj1.compareTo(obj2);
	        	}
	        });
	        UserDataDo maxViews=Collections.max(result,new Comparator<UserDataDo>() {
	        	public int compare(UserDataDo o1, UserDataDo o2) {
	        		 Integer obj1 = new Integer(o1.getViews());
	        		 Integer obj2 = new Integer(o2.getViews());
	        	     return obj1.compareTo(obj2);
	        	}
	        });
		    final DataTable data = DataTable.create();
		    data.addColumn(ColumnType.NUMBER, "No.");
	        data.addColumn(ColumnType.STRING, "Format");
	        data.addColumn(ColumnType.STRING, "Title");
	        data.addColumn(ColumnType.STRING, "Avg.Time&nbsp;Spent");
	        data.addColumn(ColumnType.STRING, "Views");
	        data.addColumn(ColumnType.STRING, "Reaction");
	        data.addRows(result.size());
	        
	        for(int i=0;i<result.size();i++) {
	        	data.setCell(i, 0, i+1, null, getPropertiesCell());
	            //set Format
	              String  resourceCategory =result.get(i).getCategory();
	              String categoryStyle="";
				  if(resourceCategory.equalsIgnoreCase("website")){
				      resourceCategory = "webpage";
				      categoryStyle=res.css().category_new_type_webpage();
				  } else if(resourceCategory.equalsIgnoreCase("slide")){
				      resourceCategory = "image";
				      categoryStyle=res.css().category_new_type_image();
				  } else if(resourceCategory.equalsIgnoreCase("handout") || resourceCategory.equalsIgnoreCase("lesson") || resourceCategory.equalsIgnoreCase("textbook")) {
				      resourceCategory = "text";
				      categoryStyle=res.css().category_new_type_text();
				  }  else if(resourceCategory.equalsIgnoreCase("exam")) {
				      resourceCategory = "webpage";
				      categoryStyle=res.css().category_new_type_webpage();
				  } else if(resourceCategory.equalsIgnoreCase("video")) {
				      resourceCategory = "webpage";
				      categoryStyle=res.css().category_new_type_video();
				  } else if(resourceCategory.equalsIgnoreCase("interactive")) {
				      resourceCategory = "webpage";
				      categoryStyle=res.css().category_new_type_interactive();
				  } else{
					  categoryStyle=res.css().category_new_type_other();
				  }
	            Label categorylbl=new Label();
	            categorylbl.addStyleName(categoryStyle);
	            categorylbl.addStyleName(res.css().setMarginAuto());
	            data.setValue(i, 1,categorylbl.toString());
	            
	            //Set Question Title
	            Label questionTitle=new Label( AnalyticsUtil.html2text(result.get(i).getTitle()));
	            questionTitle.setStyleName(res.css().alignCenterAndBackground());
	            data.setValue(i, 2, questionTitle.toString());
	          
	           //Set time spent
	            HorizontalPanel timeSpentpnl=new HorizontalPanel();
	            timeSpentpnl.add(getTimeStampLabel(result.get(i).getTimeSpent()));
	            Label progressBar=new Label();
	            progressBar.setStyleName(res.css().setProgressBar());
	            timeSpentpnl.add(progressBar);
	            double maxAvgVal = ((double) result.get(i).getTimeSpent())/((double) maxAvgValue.getTimeSpent());
	            progressBar.getElement().getStyle().setWidth(maxAvgVal*100, Unit.PX);
	            data.setValue(i, 3, timeSpentpnl.toString());
	           
	            //set Views label
	            HorizontalPanel viewpnl=new HorizontalPanel();
	            Label viewlbl=new Label(Integer.toString(result.get(i).getViews()));
	            viewlbl.setStyleName(res.css().alignCenterAndBackground());
	            viewpnl.add(viewlbl);
	            Label viewProgressBar=new Label();
	            viewProgressBar.setStyleName(res.css().setProgressBar());
	            viewpnl.add(viewProgressBar);
	            float maxViewVal = ((float) result.get(i).getViews())/((float) maxViews.getViews());
	            viewProgressBar.getElement().getStyle().setWidth(maxViewVal*100, Unit.PX);
	            data.setValue(i, 4, viewpnl.toString());
	            
	            //Set reactions
	            int reaction=result.get(i).getReaction();
	            data.setValue(i, 5, new AnalyticsReactionWidget(reaction).toString());
	        }
	        final Options options = Options.create();
	        options.setAllowHtml(true);
	        Table table = new Table(data, options);
	        individualResourceBreakdownData.add(table);
	        filterDropDown.addChangeHandler(new ChangeHandler() {
	    		
				@Override
				public void onChange(ChangeEvent event) {
					  individualResourceBreakdownData.clear();
						int selectedIndex=filterDropDown.getSelectedIndex();
					 	operationsView=DataView.create(data);
						 if(selectedIndex==1){
							 operationsView.hideRows(primitivesResources); 
						 }
						 if(selectedIndex==2){
							 operationsView.hideRows(primitivesQuestions); 
						 }
						 Table table = new Table(operationsView, options);
					     table.setStyleName("collectionProgressTable");
					     individualResourceBreakdownData.add(table);	
					     table.addDomHandler(new ClickOnTableCell(), ClickEvent.getType());
				}
			});
	}
	void setOpenendedQuestionsData(ArrayList<UserDataDo> result){
		    DataTable data = DataTable.create();
		    data.addColumn(ColumnType.NUMBER, "No.");
	        data.addColumn(ColumnType.STRING, "Question");
	        data.addColumn(ColumnType.STRING, "Completion");
	        data.addColumn(ColumnType.STRING, "Time&nbsp;Spent");
	        data.addColumn(ColumnType.STRING, "Reaction");
	        data.addColumn(ColumnType.STRING, "Response");
	        data.addRows(result.size());
	        for(int i=0;i<result.size();i++) {
	        	data.setCell(i, 0, i+1, null, getPropertiesCell());
	        	
	            //Set Question Title
	            Label questionTitle=new Label( AnalyticsUtil.html2text(result.get(i).getTitle()));
	            questionTitle.setStyleName(res.css().alignCenterAndBackground());
	            data.setValue(i, 1, questionTitle.toString());
	          
	            //Set completion
	            int noOfAttempts=result.get(i).getAttempts();
	            Label completion=new Label();
	            completion.setStyleName(res.css().alignCenterAndBackground());
	            if(noOfAttempts>0){
	            	completion.setText("Yes");
	            }else{
	            	completion.setText("No");
	            }
	            data.setValue(i, 2, completion.toString());
	          
	            //Set time spent
	            data.setValue(i, 3, getTimeStampLabel(result.get(i).getTimeSpent()).toString());
	           
	            //Set reactions
	            int reaction=result.get(i).getReaction();
	            data.setValue(i, 4, new AnalyticsReactionWidget(reaction).toString());
	           
	            //set View response label
	            Label viewResponselbl=new Label("View Response");
	            viewResponselbl.setStyleName(res.css().viewResponseTextOpended());
	            data.setValue(i, 5, viewResponselbl.toString());
	        }
	        Options options = Options.create();
	        options.setAllowHtml(true);
	        final Table table = new Table(data, options);
	        individualOpenendedData.add(table);
	        table.addDomHandler(new ClickOnTableCell(), ClickEvent.getType());
	}
	/**
	 * This class is used to handle the click event on the table cell
	 */
	class ClickOnTableCell implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			Element ele=event.getNativeEvent().getEventTarget().cast();
			if(ele.getInnerText().equalsIgnoreCase("View Response")){
				//Window.alert("ele:"+ele.getAttribute("id"));
			}
		}
	}
	void setQuestionsData(ArrayList<UserDataDo> result){
		    DataTable data = DataTable.create();
		    data.addColumn(ColumnType.NUMBER, "No.");
	        data.addColumn(ColumnType.STRING, "Question");
	        data.addColumn(ColumnType.STRING, "Answer&nbsp;Choice");
	        data.addColumn(ColumnType.STRING, "#Attempts");
	        data.addColumn(ColumnType.STRING, "Time&nbsp;Spent");
	        data.addColumn(ColumnType.STRING, "Reactions");
	       
	        data.addRows(result.size());
	      
	        for(int i=0;i<result.size();i++) {
	            data.setCell(i, 0, i+1, null, getPropertiesCell());
	           
	            Label questionTitle=new Label(AnalyticsUtil.html2text(result.get(i).getTitle()));
	            questionTitle.setStyleName(res.css().alignCenterAndBackground());
	            data.setValue(i, 1, questionTitle.toString());
	            int noOfAttempts=result.get(i).getAttempts();
	           
	            //Set Answer choices
	            String questionType= result.get(i).getType();
	            String correctAnser = null;
	        	if(questionType.equalsIgnoreCase("MC") ||questionType.equalsIgnoreCase("TF")){ 
	        		Label anserlbl=new Label();
	        		if(result.get(i).getMetaData()!=null && result.get(i).getOptions()!=null){
	        			 JSONValue value = JSONParser.parseStrict(result.get(i).getOptions());
	        			 JSONObject authorObject = value.isObject();
	        			 if(authorObject.keySet().size()!=0){
	        				 String userSelectedOption=authorObject.keySet().iterator().next();
		        			 correctAnser=getCorrectAnswer(result.get(i).getMetaData());
		        			 if(userSelectedOption!=null && correctAnser!=null){
		        				 anserlbl.setText(userSelectedOption);
		        				 if(userSelectedOption.equalsIgnoreCase(correctAnser) && noOfAttempts==1){
		        					 anserlbl.getElement().getStyle().setColor(CORRECT);
		        				 }else if(userSelectedOption.equalsIgnoreCase(correctAnser) && noOfAttempts>1){
		        					 anserlbl.getElement().getStyle().setColor(ONMULTIPULEATTEMPTS);
		        				 }else{
		        					 anserlbl.getElement().getStyle().setColor(INCORRECT);
		        				 }
		        			 }
	        			 }
	        		}
	        		anserlbl.setStyleName(res.css().alignCenterAndBackground());
	        		data.setValue(i, 2, anserlbl.toString());
	        	}else if (questionType.equalsIgnoreCase("FIB")){
	        		VerticalPanel answerspnl=new VerticalPanel();
	        		if(result.get(i).getMetaData()!=null && result.get(i).getOptions()!=null){
	        			String answerTextFormat = "";
	        			String[] answersArry = null;
	        			ArrayList<MetaDataDo> questionList=result.get(i).getMetaData();
						for (MetaDataDo metaDataDo : questionList) {
							String answerText = "";
							if((metaDataDo.getAnswer_text() != null)) {
								answerText = metaDataDo.getAnswer_text();
							}
							answerTextFormat += '[' + answerText +']';
							if(questionList.size()  != metaDataDo.getSequence()){
								  answerTextFormat += ",";
							}
						}
						String[] userFibOption = null;
						if(result.get(i).getText() != null) {
							answersArry=answerTextFormat.split(",");
							userFibOption =result.get(i).getText().split(",");
						}
						if(answersArry!=null){
							for (int k = 0; k < answersArry.length; k++) { 
								Label answerChoice=new Label();
								if((answersArry[k].toLowerCase().trim().equalsIgnoreCase(userFibOption[k].toLowerCase().trim())) && (noOfAttempts == 1)){
									answerChoice.setText(userFibOption[k]);
									answerChoice.getElement().getStyle().setColor(CORRECT);
								}else if((answersArry[k].toLowerCase().trim().equalsIgnoreCase(userFibOption[k].toLowerCase().trim())) && (noOfAttempts > 1)) {
									answerChoice.setText(userFibOption[k]);
									answerChoice.getElement().getStyle().setColor(ONMULTIPULEATTEMPTS);
								}else{
									answerChoice.setText(userFibOption[k]);
									answerChoice.getElement().getStyle().setColor(INCORRECT);
								}
								answerChoice.setStyleName(res.css().alignCenterAndBackground());
								answerspnl.add(answerChoice);
							}
						}
	        		}
	        		 answerspnl.setStyleName(res.css().setMarginAuto());
	        		 data.setValue(i, 2, answerspnl.toString());
	        	}else  if(questionType.equalsIgnoreCase("MA")){
	        		VerticalPanel answerspnl=new VerticalPanel();
	        		if(result.get(i).getAnswerObject()!=null) {
	        			 JSONValue value = JSONParser.parseStrict(result.get(i).getAnswerObject());
	        			 JSONObject answerObject = value.isObject();
	        			 Set<String> keys=answerObject.keySet();
	        			 Iterator<String> itr = keys.iterator();
	        		      while(itr.hasNext()) {
	        		         JSONArray attemptsObj=(JSONArray) answerObject.get(itr.next().toString());
	        		         for(int j=0;j<attemptsObj.size();j++){
	        		        	Label answerChoice=new Label();
	        		            String showMessage = null;
	        		            boolean skip = attemptsObj.get(j).isObject().get("skip").isBoolean().booleanValue();
	        		        	String status =attemptsObj.get(j).isObject().get("status").isString().stringValue();
	        		        	String matext =attemptsObj.get(j).isObject().get("text").isString().stringValue();
	 	        		         if(skip == false)
	 							  {
	 	        		        	if(matext.equalsIgnoreCase("0")) {
										  showMessage = "No";
									} else if(matext.equalsIgnoreCase("1")) {
										   showMessage = "Yes";
									}
									answerChoice.setText(showMessage);
	 									if(status.equalsIgnoreCase("0")) {
	 										answerChoice.getElement().getStyle().setColor(INCORRECT);
	 									} else if(status.equalsIgnoreCase("1") && (noOfAttempts == 1)) {
	 										answerChoice.getElement().getStyle().setColor(CORRECT);
	 									} else if(status.equalsIgnoreCase("1") && (noOfAttempts > 1)) {
	 										answerChoice.getElement().getStyle().setColor(ONMULTIPULEATTEMPTS);
	 									}
	 							  }
	 	        		        answerChoice.setStyleName(res.css().alignCenterAndBackground());
	 	        		        answerspnl.add(answerChoice);
	        		         }
	        		      }
	        		}
	        		 answerspnl.setStyleName(res.css().setMarginAuto());
	        		 data.setValue(i, 2, answerspnl.toString());
	        	}
	           
	            //Set attempts
	            Label attempts=new Label(Integer.toString(noOfAttempts));
	            attempts.setStyleName(res.css().alignCenterAndBackground());
	            data.setValue(i, 3, attempts.toString());
	            
	            //Set time spent
	            data.setValue(i, 4, getTimeStampLabel(result.get(i).getTimeSpent()).toString());
	            
	            //Set reactions
	            int reaction=result.get(i).getReaction();
	            data.setValue(i, 5, new AnalyticsReactionWidget(reaction).toString());
	        }
	        Options options = Options.create();
	        options.setAllowHtml(true);
	        Table table = new Table(data, options);
	        individualScoredData.add(table);
	}
	String getCorrectAnswer(ArrayList<MetaDataDo> metaDataObj){
		for (MetaDataDo metaDataDo : metaDataObj) {
			if(metaDataDo.getIs_correct()==1){
				return AnalyticsUtil.getCharForNumber(metaDataDo.getSequence()-1);
			}
		}
		return null;
	}
	com.google.gwt.visualization.client.Properties getPropertiesCell(){
		  Properties properties=Properties.create();
		  properties.set("style", "text-align:center;");
		  com.google.gwt.visualization.client.Properties p=properties.cast();
		  return p;
	}
	Label getTimeStampLabel(long timeSpent){
		 Label timeStamplbl=new Label(AnalyticsUtil.getTimeSpent(timeSpent));
         timeStamplbl.setStyleName(res.css().alignCenterAndBackground());
         return timeStamplbl;
	}
	@Override
	public void setIndividualCollectionMetaData(
			ArrayList<CollectionSummaryMetaDataDo> result) {
		//Set collection meta data
		totalTimeSpentlbl.setText(AnalyticsUtil.getTimeSpent(result.get(0).getAvgTimeSpent()));
		totalViewlbl.setText(Integer.toString(result.get(0).getViews()));
		totalAvgReactionlbl.clear();
		totalAvgReactionlbl.add(new AnalyticsReactionWidget(result.get(0).getAvgReaction()));
		
	}
}
