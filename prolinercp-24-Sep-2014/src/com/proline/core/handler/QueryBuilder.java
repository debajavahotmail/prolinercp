package com.proline.core.handler;

import java.util.List;

import com.proline.defn.schema.models.ProlineDefinition.SystemDefinition.Level.Condition;
import com.proline.models.DBModel;
import com.proline.rcp.util.ProlineLogger;

public class QueryBuilder {
	
	public String buildQuery(String primaryQuery,List<Condition> conditionsList,DBModel dbModel,List<String> columnNames) {
		ProlineLogger.logInfo("QueryBuilder class buildQuery()");
		String query="";
		String append="";
		String appendlike="";
		String actualValue="";
		/*if( conditionsList.size() != 0 ) {
			for(int i=0;i<conditionsList.size();i++){
			Condition condition = conditionsList.get(i);
			//System.out.println(condition);
			int columnNo = condition.getSourceColumnIndex();
			String qcondition = condition.getQcondition();
			System.out.println(condition.getQcondition());
			String operator = condition.getOperator();
			String actualValue = dbModel.getAllData().get(columnNo);
//			String columnName = columnNames.get(columnNo);
			String destnColName = condition.getDestinationColName();
			//select * from emp where name = 'john'
			
			if(condition.getOperator().contains("like")){
				appendlike+=(" "+qcondition+" "+destnColName+" "
						+operator+"\'"+"%"+actualValue.substring(0, 1)+"%"+"\'").toString();
				query=" "+new StringBuilder(primaryQuery)+appendlike;
			}
			else{
				append+=(" "+qcondition+" "+destnColName
						+operator+"\'"+actualValue+"\'").toString();
				query=" "+new StringBuilder(primaryQuery)+append;
			}*/
		query=primaryQuery;
		String item=query;
		if(item.contains("@")){
			System.out.println("Inside the query @");
			ProlineLogger.logInfo("QueryBuilder class buildQuery() Inside the query @ ");
			String[] items=item.split("@*@");
			for (int j = 0; j < items.length; j++) {
				if(j%2==1){
					System.out.println(items[j]);
					int value=Integer.parseInt(items[j]);
					actualValue = dbModel.getAllData().get(value);
					if(actualValue!=null){
						String addValueOf="'"+actualValue+"'";
						query=query.replace("@"+items[j]+"@", addValueOf);
						System.out.println("The Finala Query = "+query);
					}
				}
				
			}
		}
			System.out.println("Formed query--->"+query);
			ProlineLogger.logInfo("QueryBuilder class buildQuery() Inside the query() Formed query--->"+query);
		/*	}
		}*/
		return query;
	}

	
}
