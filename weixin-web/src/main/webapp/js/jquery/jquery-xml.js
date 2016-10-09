jQuery.namespace2win("tonto.common.xml");
/**
 * 简单xml格式（没有嵌套标签）字符串转化为object
 * @param xml
 * @returns {___anonymous58_59}
 */
tonto.common.xml.xml2object=function(xml)
{		
	var xmlObject={};
	var matches=xml.match(/<(\w+)>.*<\/\1>/g);
	if(matches)
	{	
		for(var i=0;i<matches.length;i++)
		{
			var result=matches[i].match(/<(\w+)>(.*)<\/\1>/);
			if(result)
				xmlObject[result[1]]=result[2];
		}		
	}
	return xmlObject;
}

jQuery.extend(tonto.common.xml);
