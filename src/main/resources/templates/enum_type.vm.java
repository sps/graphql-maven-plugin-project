package ${package};

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @author generated by graphql-maven-plugin
 */
public enum ${enum.name} {
	#foreach ($value in $enum.values)${value}#if($foreach.hasNext), #end#end;
}
