package up.visulog.config;

import java.util.Map;
import java.util.HashMap;

/**
 * @version 1.0
 */
public interface PluginConfig {
	Map <String,String> PARAMETERS = new HashMap<String,String>();

	/** 
	 * Get the value for specified plugin.
	 * @param plugin 
	 * @return the String valye associated with plugin
	 */
	public static String getValueOfCommand(String plugin){
		if(PARAMETERS.isEmpty())return null;
		return PARAMETERS.get(plugin);
	}

	/**
	 * Add a value of plugin with this parameter
	 * @param plugin 
	 * @param value
	 */
	public static void addValueInMap(String plugin,String value){
		PARAMETERS.put(plugin,value); 
	}
}

