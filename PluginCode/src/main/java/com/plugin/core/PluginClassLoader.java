package com.plugin.core;

import com.plugin.content.PluginProviderInfo;
import com.plugin.util.LogUtil;

import dalvik.system.DexClassLoader;

/**
 * 为了支持Receiver和ContentProvider，增加此类。
 * 
 * @author Administrator
 * 
 */
public class PluginClassLoader extends DexClassLoader {

	public PluginClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
		super(dexPath, optimizedDirectory, libraryPath, parent);
	}

	@Override
	public String findLibrary(String name) {
		LogUtil.d("findLibrary", name);
		return super.findLibrary(name);
	}

	@Override
	protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {

		//Just for Receiver
		if (className.startsWith(PluginIntentResolver.prefix)) {
			String realName = className.replace(PluginIntentResolver.prefix, "");
			LogUtil.d("className ", className, "target", realName);
			Class clazz = PluginLoader.loadPluginClassByName(realName);
			if (clazz != null) {
				return clazz;
			}
		} else if (className.startsWith(PluginProviderInfo.prefix)) {
			//Just for contentprovider
			String realName = className.replace(PluginProviderInfo.prefix, "");
			LogUtil.d("className ", className, "target", realName);
			Class clazz = PluginLoader.loadPluginClassByName(realName);
			if (clazz != null) {
				return clazz;
			}
		}
		return super.loadClass(className, resolve);
	}

}
