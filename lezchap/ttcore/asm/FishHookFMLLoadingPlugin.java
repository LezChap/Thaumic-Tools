package lezchap.ttcore.asm;

import java.io.File;
import java.util.Map;

import lezchap.ttcore.TTCore;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion(value = "1.6.4")
public class FishHookFMLLoadingPlugin implements cpw.mods.fml.relauncher.IFMLLoadingPlugin {
	
	//declare a placeholder for the name and location of the jar
	public static File location;

	@Override
	public String[] getASMTransformerClass() {
	return new String[]{FishHookClassTransformer.class.getName()};
	}


	@Override
	public void injectData(Map<String, Object> data) {
	//This will return the jar file of this mod
	location = (File) data.get("coremodLocation");
	}


	@Override
	public String[] getLibraryRequestClass() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getModContainerClass() {
		return TTCore.class.getName();
	}


	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
