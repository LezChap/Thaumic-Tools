package lezchap.thaumictools.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class LoggerHelper {
private static Logger logger = Logger.getLogger("ThaumicToools");

public static void init() {
logger.setParent(FMLLog.getLogger());
}

public static void log(Level logLevel, String message) {
logger.log(logLevel, message);
}
}