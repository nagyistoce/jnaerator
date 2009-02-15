/*
	Copyright (c) 2009 Olivier Chafik, All Rights Reserved
	
	This file is part of JNAerator (http://jnaerator.googlecode.com/).
	
	JNAerator is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	
	JNAerator is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with JNAerator.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.ochafik.lang.jnaerator;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.ochafik.admin.visualstudio.Configuration;
import com.ochafik.admin.visualstudio.Project;
import com.ochafik.admin.visualstudio.Solution;
import com.ochafik.admin.visualstudio.VisualStudioUtils;
import com.ochafik.lang.jnaerator.parser.Function;
import com.ochafik.lang.jnaerator.parser.Declaration.Modifier;
import com.ochafik.util.SystemUtils;
import com.ochafik.util.listenable.Adapter;
import com.ochafik.util.string.RegexUtils;

public class JNAeratorConfigUtils {

	public static final class MSC_VER {
		public final int 
			VC4 = 1000,
			VC5 = 1100,
			VC6 = 1200,
			VC70 = 1300,
			VC71 = 1310;
	}

	static String DEFAULT_FRAMEWORKS_PATH = 
		"/System/Library/Frameworks/CoreServices.framework/Versions/Current/Frameworks:" +
		"/System/Library/Frameworks/ApplicationServices.framework/Versions/Current/Frameworks:" + 
		"/System/Library/Frameworks:" +
		"/Library/Frameworks:" +
		System.getProperty("user.home") + "/Library/Frameworks"
	;
	static String DEFAULT_INCLUDE_PATH = 
		"/Developer/SDKs/MacOSX10.4u.sdk/usr/include:" +
		"."
	;

	static final Set<String> objCppExtensions = new TreeSet<String>();
	static final Set<String> headerExtensions = new TreeSet<String>();
	static final Set<String> implemsExtensions = new TreeSet<String>();
	static {
		implemsExtensions.add("cpp");
		implemsExtensions.add("cxx");
		headerExtensions.add("hpp");
		headerExtensions.add("hxx");
		headerExtensions.add("h");
		implemsExtensions.add("m");
		implemsExtensions.add("mm");
		
		objCppExtensions.addAll(headerExtensions);
		objCppExtensions.addAll(implemsExtensions);
	}
	

	public static FileFilter OBJCPP_FILE_FILTER = new FileFilter() {
		public boolean accept(File file) {
			String name = file.getName().toLowerCase();
			int i = name.lastIndexOf('.');
			if (i > 0) {
				String ext = name.substring(i + 1);
				return objCppExtensions.contains(ext);
			}
			return false;
		}
		
	};
	
	public static FileFilter HEADERS_FILE_FILTER = new FileFilter() {
		public boolean accept(File file) {
			String name = file.getName().toLowerCase();
			int i = name.lastIndexOf('.');
			if (i > 0) {
				String ext = name.substring(i + 1);
				return headerExtensions.contains(ext);
			}
			return false;
		}
		
	};
	
	public static void addCPlusPlus(JNAeratorConfig.PreprocessorConfig config) {
		config.macros.put("__cplusplus", null);
	}

	public static void addGCCPredefinedMacros(JNAeratorConfig.PreprocessorConfig config) {
		//gcc -dM -E - < /dev/null
		config.macros.put("__DBL_MIN_EXP__", "(-1021)");
		config.macros.put("__FLT_MIN__", "1.17549435e-38F");
		config.macros.put("__CHAR_BIT__", "8");
		config.macros.put("__WCHAR_MAX__", "2147483647");
		config.macros.put("__DBL_DENORM_MIN__", "4.9406564584124654e-324");
		config.macros.put("__FLT_EVAL_METHOD__", "0");
		config.macros.put("__DBL_MIN_10_EXP__", "(-307)");
		config.macros.put("__FINITE_MATH_ONLY__", "0");
		config.macros.put("__SHRT_MAX__", "32767");
		config.macros.put("__LDBL_MAX__", "1.18973149535723176502e+4932L");
		config.macros.put("__APPLE_CC__", "5484");
		config.macros.put("__UINTMAX_TYPE__", "long long unsigned int");
		config.macros.put("__SCHAR_MAX__", "127");
		config.macros.put("__USER_LABEL_PREFIX__", "_");
		config.macros.put("__STDC_HOSTED__", "1");
		config.macros.put("__DBL_DIG__", "15");
		config.macros.put("__FLT_EPSILON__", "1.19209290e-7F");
		config.macros.put("__LDBL_MIN__", "3.36210314311209350626e-4932L");
		config.macros.put("__strong", "");
		config.macros.put("__APPLE__", "1");
		config.macros.put("__DECIMAL_DIG__", "21");
		config.macros.put("__LDBL_HAS_QUIET_NAN__", "1");
		config.macros.put("__DYNAMIC__", "1");
		config.macros.put("__GNUC__", "4");
		config.macros.put("__MMX__", "1");
		config.macros.put("__DBL_MAX__", "1.7976931348623157e+308");
		config.macros.put("__DBL_HAS_INFINITY__", "1");
		config.macros.put("OBJC_NEW_PROPERTIES", "1");
		config.macros.put("__weak", "");
		config.macros.put("__DBL_MAX_EXP__", "1024");
		config.macros.put("__SSE2_MATH__", "1");
		config.macros.put("__LONG_LONG_MAX__", "9223372036854775807LL");
		config.macros.put("__GXX_ABI_VERSION", "1002");
		config.macros.put("__FLT_MIN_EXP__", "(-125)");
		config.macros.put("__DBL_MIN__", "2.2250738585072014e-308");
		config.macros.put("__DBL_HAS_QUIET_NAN__", "1");
		config.macros.put("__REGISTER_PREFIX__", "");
		config.macros.put("__NO_INLINE__", "1");
		config.macros.put("__i386", "1");
		config.macros.put("__FLT_MANT_DIG__", "24");
		config.macros.put("__VERSION__", "\"4.0.1 (Apple Inc. build 5484)\"");
		config.macros.put("i386", "1");
		config.macros.put("__i386__", "1");
		config.macros.put("__ENVIRONMENT_MAC_OS_X_VERSION_MIN_REQUIRED__", "1055");
		config.macros.put("__SIZE_TYPE__", "long unsigned int");
		config.macros.put("__FLT_RADIX__", "2");
		config.macros.put("__LDBL_EPSILON__", "1.08420217248550443401e-19L");
		config.macros.put("__SSE_MATH__", "1");
		config.macros.put("__FLT_HAS_QUIET_NAN__", "1");
		config.macros.put("__FLT_MAX_10_EXP__", "38");
		config.macros.put("__LONG_MAX__", "2147483647L");
		config.macros.put("__FLT_HAS_INFINITY__", "1");
		config.macros.put("__LITTLE_ENDIAN__", "1");
		config.macros.put("__LDBL_MANT_DIG__", "64");
		config.macros.put("__CONSTANT_CFSTRINGS__", "1");
		config.macros.put("__WCHAR_TYPE__", "int");
		config.macros.put("__FLT_DIG__", "6");
		config.macros.put("__INT_MAX__", "2147483647");
		config.macros.put("__FLT_MAX_EXP__", "128");
		config.macros.put("__DBL_MANT_DIG__", "53");
		config.macros.put("__WINT_TYPE__", "int");
		config.macros.put("__SSE__", "1");
		config.macros.put("__LDBL_MIN_EXP__", "(-16381)");
		config.macros.put("__MACH__", "1");
		config.macros.put("__LDBL_MAX_EXP__", "16384");
		config.macros.put("__LDBL_MAX_10_EXP__", "4932");
		config.macros.put("__DBL_EPSILON__", "2.2204460492503131e-16");
		config.macros.put("__GNUC_PATCHLEVEL__", "1");
		config.macros.put("__LDBL_HAS_INFINITY__", "1");
		config.macros.put("__INTMAX_MAX__", "9223372036854775807LL");
		config.macros.put("__FLT_DENORM_MIN__", "1.40129846e-45F");
		config.macros.put("__PIC__", "1");
		config.macros.put("__FLT_MAX__", "3.40282347e+38F");
		config.macros.put("__SSE2__", "1");
		config.macros.put("__FLT_MIN_10_EXP__", "(-37)");
		config.macros.put("__INTMAX_TYPE__", "long long int");
		config.macros.put("__GNUC_MINOR__", "0");
		config.macros.put("__DBL_MAX_10_EXP__", "308");
		config.macros.put("__LDBL_DENORM_MIN__", "3.64519953188247460253e-4951L");
		config.macros.put("__PTRDIFF_TYPE__", "int");
		config.macros.put("__LDBL_MIN_10_EXP__", "(-4931)");
		config.macros.put("__LDBL_DIG__", "18");
		config.macros.put("__GNUC_GNU_INLINE__", "1");
	}

	public static void autoConfigure(final JNAeratorConfig config) {
		config.preprocessorConfig.includes.addAll(Arrays.asList(JNAeratorConfigUtils.DEFAULT_INCLUDE_PATH.split(":")));
		config.preprocessorConfig.frameworksPath.addAll(Arrays.asList(JNAeratorConfigUtils.DEFAULT_FRAMEWORKS_PATH.split(":")));
		if (SystemUtils.isWindows()) {
			//http://support.microsoft.com/kb/65472
			config.preprocessorConfig.macros.put("_CHAR_UNSIGNED", null);;
			config.preprocessorConfig.macros.put("_MSC_VER", "800");
			config.functionsAccepter = new Adapter<Function, Boolean>() {
	
				public Boolean adapt(Function value) {
					return true;
					/*
					Set<Modifier> mods = value.getModifiers();
	
					return mods.contains(Modifier.DllExport) ||
						mods.contains(Modifier.DllImport) ||
						mods.contains(Modifier.Extern);*/
				}
				
			};
			//_CPPRTTI
			//_DLL
			//_M_IX86
			//_MT
			//_CPPUNWIND
			
		} else {
			config.preprocessorConfig.macros.put("__GNUC__", null);
			
			if (SystemUtils.isMacOSX())
				config.preprocessorConfig.macros.put("TARGET_API_MAC_OSX", null);
			
			config.functionsAccepter = new Adapter<Function, Boolean>() {
	
				public Boolean adapt(Function value) {
					Set<Modifier> mods = value.getModifiers();
					return !mods.contains(Modifier.Inline) && !mods.contains(Modifier.__inline__);
				}
			};
		}
		
		JNAeratorConfigUtils.autoConfigureArchitecture(config);
	}

	/**
	 * <ul>
	 * <li> endianness
	 * <li> TARGET_CPU_* : see /System/Library/Frameworks/CoreServices.framework/Versions/Current/Frameworks/CarbonCore.framework/Headers/fp.h
	 * </ul>
	 * @param config
	 */
	
	static void autoConfigureArchitecture(JNAeratorConfig config) {
		
		String arch = System.getProperty("os.arch").toLowerCase();
		if (arch.equals("x86_64") || arch.equals("amd64")) {
			config.preprocessorConfig.macros.put("TARGET_CPU_X86_64", null);
			config.preprocessorConfig.macros.put("__x86_64__", null);
			config.preprocessorConfig.macros.put("__amd64__", null);
			config.preprocessorConfig.macros.put("__LITTLE_ENDIAN__", null);
			config.preprocessorConfig.macros.put("M_X64", "1");
			config.preprocessorConfig.macros.put("_M_X64", "1");
			config.preprocessorConfig.macros.put("_WIN64", "1");
		} else if (arch.equals("i386") || arch.equals("x86")) {
			config.preprocessorConfig.macros.put("TARGET_CPU_X86", null);
			config.preprocessorConfig.macros.put("__i386__", null);
			config.preprocessorConfig.macros.put("__LITTLE_ENDIAN__", null);
			config.preprocessorConfig.macros.put("M_I86", "1");
			config.preprocessorConfig.macros.put("_M_I86", "1");
			config.preprocessorConfig.macros.put("_WIN32", "1");
		} else if (arch.equals("ppc")) {
			config.preprocessorConfig.macros.put("TARGET_CPU_PPC", null);
			config.preprocessorConfig.macros.put("__PPC__", null);
			config.preprocessorConfig.macros.put("__powerpc__", null);
			config.preprocessorConfig.macros.put("__BIG_ENDIAN__", null);
		} else if (arch.equals("ppc64")) {
			config.preprocessorConfig.macros.put("TARGET_CPU_PPC64", null);
			config.preprocessorConfig.macros.put("__PPC_64__", null);
//			config.preprocessorConfig.macros.put("__powerpc64__", null);
			config.preprocessorConfig.macros.put("__BIG_ENDIAN__", null);
		}
		
	}

	public static void readProjectConfig(File projectFile, String configName, final JNAeratorConfig config) throws Exception {
		String projectFileName = projectFile.getName();
		config.rootDirectoriesPrefixesForSourceComments.add(projectFile.getParentFile().getCanonicalPath() + File.separator);
		
		if (projectFileName.endsWith(".sln")) {
			for (String include : VisualStudioUtils.getMicrosoftIncludes()) {
				include = new File(include).getCanonicalPath();
				config.preprocessorConfig.includes.add(include);
				if (!include.endsWith(File.separator))
					include = include + File.separator;
				config.rootDirectoriesPrefixesForSourceComments.add(include);
			}
			
			Solution solution = new Solution(projectFile);
			solution.parseProjects(config.fileFilter);
			
			//final Map<String, FileConfiguration> configsByFile = new HashMap<String, FileConfiguration>();
			//final Map<File, String> libraryDLLByFile = new HashMap<File, String>();
			
			for (Project project : solution.getProjects()) {
				String projectConfigName = project.activeConfigurationNameBySolutionConfigurationName.get(configName);
				if (projectConfigName == null)
					projectConfigName = configName;
				
				//	throw new IOException("Solution configuration with name '" + configName + "' does correspond to any configuration in project '" + project.name + "' (available configs : " + 
				//			StringUtils.implode(project.activeConfigurationNameBySolutionConfigurationName.keySet(), ", ") + ")");
		
				Configuration configuration = project.configurations.get(projectConfigName);
				String libraryFile = configuration.outputFile == null ? project.name : RegexUtils.findFirst(configuration.outputFile, Pattern.compile("^(.*?)(\\.[^.]*)?$"), 1);
				config.libraryProjectSources.put(libraryFile, project.projectFile.getCanonicalFile());
				
				System.out.println("project " + project.name + ": library = " + libraryFile);
				if (configuration != null) {
					System.out.println("preprocessorDefinitions : " + configuration.preprocessorDefinitions);
					for (String def : configuration.preprocessorDefinitions)
						config.preprocessorConfig.macros.put(def, "");
				}
				for (File file : project.files) {
					try {
						file = file.getCanonicalFile();
						System.out.println(file + "\n\t-> " + libraryFile);
						config.addFile(file, libraryFile);
						
						//config.preprocessorConfig
						//config.libraryByFile.put(file, libraryFile)
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
			
			if (config.preprocessorConfig.macros.containsKey("WIN32") || config.preprocessorConfig.macros.containsKey("_WIN32"))
				config.preprocessorConfig.macros.put("_M_IX86", "");
			else if (config.preprocessorConfig.macros.containsKey("WIN64") || config.preprocessorConfig.macros.containsKey("_WIN64"))
				config.preprocessorConfig.macros.put("_M_AMD64", "");
			
		}
	}

	public static void addFramework(JNAeratorConfig config, String framework) throws IOException {

		File file = new File(framework);
		if (!file.exists()) {
			for (String pathEl : config.preprocessorConfig.frameworksPath) {
				File f = new File(pathEl, framework + ".framework");
				if (f.exists() && f.isDirectory()) {
					file = f;
					break;
				}
			}
		}
		if (!file.exists())
			throw new IOException("Could not find framework '" + framework + "' in path " + config.preprocessorConfig.frameworksPath);

		File headers = new File(file, "Headers");
		if (headers.exists())
			config.addFile(headers, framework);
		else
			throw new IOException("No Headers subdirectory in framework '" + framework + "' found here : " + file);
	}

}
