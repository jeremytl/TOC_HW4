/**
 * @author 陳緯峻 Wei-Jun Chen
 * student number : F74006129
 * description:
 * 		Parse the .json data from the URL(args[0]),
 * generate the data and find the max_distinct_months road, street, or lane,
 * then output the name, the max_deal_price and min_deal_price of the road, street, or lane 
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

import org.json.*;

public class TocHw4 {
	
	private static int findmdm(int[][] in){
		int u,index=0,rt=0;
		for(u=0;in[u][0]!=0;u++)
			if(in[u][0]>rt){
				rt=in[u][0];
				index=u;
			}
		return index;	
	}
	
	private static int mulmaxindex(int maxind,int[][] in)
	{
		int u;
		for(u=maxind+1;in[u][0]!=0;u++)
			if(in[u][0]==in[maxind][0])return u;
		return -1;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, JSONException {

		try {
			if (args.length < 1)
				throw new Exception();
			
			BufferedReader readjson = new BufferedReader(new InputStreamReader(
					new URL(args[0]).openStream(), "UTF-8"));
			StringBuilder jsontext = new StringBuilder();
			String curline;
			while ((curline = readjson.readLine()) != null)
				jsontext.append(curline);

			readjson.close();
			
			int[][] mdm = new int[2001][100];
			
			int i,j,k,maxi;
			
			ArrayList<String> name = new ArrayList<String>();
			
			//System.out.println(jsontext.toString());
			
			Pattern road = Pattern.compile("(.*路).*");//group(1) indicate the string inside the parentheses "()"
			Pattern street =Pattern.compile("(.*街).*");
			Pattern bigst =Pattern.compile("(.*大道).*");
			Pattern lane = Pattern.compile("(.*巷).*");
			
			Matcher m1, m2, m3, m4;
			
			JSONArray realpricejson = new JSONArray(jsontext.toString());
			String str,matchstr;
			int dy,dmon;
			
			//System.out.println(name.size()+" "+mdm[0][0]);
			for (i = 0; i < realpricejson.length(); i++) {
					str=realpricejson.getJSONObject(i).getString("土地區段位置或建物區門牌");
					dy=realpricejson.getJSONObject(i).getInt("交易年月");
					dmon=realpricejson.getJSONObject(i).getInt("總價元");
					//System.out.println(str);
					m1=road.matcher(str);
					m2=street.matcher(str);
					m3=bigst.matcher(str);
					m4=lane.matcher(str);
					if(m1.matches())
					{
						matchstr=new String(m1.group(1));
						for(j=0;j<name.size();j++)
							if(matchstr.equals(name.get(j))){
								for(k=3;mdm[j][k]!=0;k++)
									if(dy==mdm[j][k])break;
								
								if(dmon>mdm[j][1])
									mdm[j][1]=dmon;
								else if(dmon<mdm[j][2])
									mdm[j][2]=dmon;
								if(mdm[j][k]!=0)break;
								mdm[j][0]++;
								mdm[j][k]=dy;
								break;
							}
						if(j!=name.size())continue;
						name.add(matchstr);
						//System.out.println(matchstr);
						mdm[j]=new int[100];
						mdm[j][0]=1;
						mdm[j][1]=dmon;
						mdm[j][2]=dmon;
						mdm[j][3]=dy;
					}
					else if(m2.matches())
					{
						matchstr=new String(m2.group(1));
						for(j=0;j<name.size();j++)
							if(matchstr.equals(name.get(j))){
								for(k=3;mdm[j][k]!=0;k++)
									if(dy==mdm[j][k])break;
								
								if(dmon>mdm[j][1])
									mdm[j][1]=dmon;
								else if(dmon<mdm[j][2])
									mdm[j][2]=dmon;
								if(mdm[j][k]!=0)break;
								mdm[j][0]++;
								mdm[j][k]=dy;
								break;
							}
						if(j!=name.size())continue;
						name.add(matchstr);
						//System.out.println(matchstr);
						mdm[j][0]=1;
						mdm[j][1]=dmon;
						mdm[j][2]=dmon;
						mdm[j][3]=dy;
					}
					else if(m3.matches())
					{
						matchstr=new String(m3.group(1));
						for(j=0;j<name.size();j++)
							if(matchstr.equals(name.get(j))){
								for(k=3;mdm[j][k]!=0;k++)
									if(dy==mdm[j][k])break;
								
								if(dmon>mdm[j][1])
									mdm[j][1]=dmon;
								else if(dmon<mdm[j][2])
									mdm[j][2]=dmon;
								if(mdm[j][k]!=0)break;
								mdm[j][0]++;
								mdm[j][k]=dy;
								break;
							}
						if(j!=name.size())continue;
						name.add(matchstr);
						//System.out.println(matchstr);
						mdm[j][0]=1;
						mdm[j][1]=dmon;
						mdm[j][2]=dmon;
						mdm[j][3]=dy;
					}
					else if(m4.matches())
					{
						matchstr=new String(m4.group(1));
						for(j=0;j<name.size();j++)
							if(matchstr.equals(name.get(j))){
								for(k=3;mdm[j][k]!=0;k++)
									if(dy==mdm[j][k])break;
								if(dmon>mdm[j][1])
									mdm[j][1]=dmon;
								else if(dmon<mdm[j][2])
									mdm[j][2]=dmon;
								if(mdm[j][k]!=0)break;
								mdm[j][0]++;
								mdm[j][k]=dy;
								break;
							}
						if(j!=name.size())continue;
						name.add(matchstr);
						//System.out.println(matchstr);
						mdm[j][0]=1;
						mdm[j][1]=dmon;
						mdm[j][2]=dmon;
						mdm[j][3]=dy;
					}
			}
			
			maxi=findmdm(mdm);
			while(maxi!=-1)
			{
				System.out.println(name.get(maxi)/*+mdm[maxi][0]*/+", 最高成交價: "+mdm[maxi][1]+", 最低成交價: "+mdm[maxi][2]);
				maxi=mulmaxindex(maxi,mdm);
			}
			
			
			/*for(j=0;j<name.size();j++)
				System.out.println(name.get(j)+" "+mdm[j][0]+" "+mdm[j][1]+" "+mdm[j][2]);*/
			
			
		} catch (Exception e) {

			System.out.println("Too few arguments or error argument format!");
			e.printStackTrace();

		}
	}
	

}
