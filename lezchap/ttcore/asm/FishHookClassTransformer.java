package lezchap.ttcore.asm;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class FishHookClassTransformer implements net.minecraft.launchwrapper.IClassTransformer {

	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) {

	//Check if the JVM is about to process the te.class or the EntityCreeper.class
	if (arg0.equals("ul") || arg0.equals("net.minecraft.entity.projectile.EntityFishHook")) {
	System.out.println("********* INSIDE FISHHOOK TRANSFORMER ABOUT TO PATCH: " + arg0);
	arg2 = patchClassInJar(arg0, arg2, arg0, FishHookFMLLoadingPlugin.location);
	}
	return arg2;
	}

	//a small helper method that takes the class name we want to replace and our jar file.
	//It then uses the java zip library to open up the jar file and extract the classes.
	//Afterwards it serializes the class in bytes and pushes it on to the JVM.
	//with the original bytes that JVM was about to process ignored completly

	public byte[] patchClassInJar(String name, byte[] bytes, String ObfName, File location) {
	try {
	//open the jar as zip
	ZipFile zip = new ZipFile(location);
	//find the file inside the zip that is called ul.class or net.minecraft.entity.projectile.EntityFishHook.class
	//replacing the . to / so it would look for net/minecraft/entity/projectile/FishHook.class
	ZipEntry entry = zip.getEntry(name.replace('.', '/') + ".class");


	if (entry == null) {
	System.out.println(name + " not found in " + location.getName());
	} else {

	//serialize the class file into the bytes array
	InputStream zin = zip.getInputStream(entry);
	bytes = new byte[(int) entry.getSize()];
	zin.read(bytes);
	zin.close();
	System.out.println("[" + "FishHookCore" + "]: " + "Class " + name + " patched!");
	}
	zip.close();
	} catch (Exception e) {
	throw new RuntimeException("Error overriding " + name + " from " + location.getName(), e);
	}

	//return the new bytes
	return bytes;
	}
}
