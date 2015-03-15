

# JNAerator Files #

As the number of arguments needed for a project might quickly grow and produce insane command lines, you might consider putting some or all of your arguments in a `*.jnaerator` file (preferably, `config.jnaerator`).

Please read [JNAeratorFiles](JNAeratorFiles.md) for more details about these files, and [JNAeratorFAQ](JNAeratorFAQ.md) for more general questions.

# Syntax #

` java -jar jnaerator.jar options headerFiles dynamicLibrariesFiles `

# Options #

  *  <file: OptionalFile>
> > Any header (or directory containing headers at any level of hierarchy), shared library, `*`.bridgesupport file or `*`.jnaerator file
  * **(?i)-noComp**
> > (deprecated) Do not compile JNAerated headers
  * **(?i)-noJar**
> > (deprecated) Do not create an output JAR
  * **(?i)-noLibBundle**
> > Do not bundle libraries in output JAR
  * **-?-h(?:elp)?**
> > Show command line arguments help
  * **-D([^=]**)(?:=(.**))?** <name: String> <value: String>
> > Define a macro symbol
  * **-F(.+)?** <dir: File>
> > Add a directory to the frameworks path. See doc of JNAERATOR\_FRAMEWORKS\_PATH
  * **-I(.+)?** <dir: File>
> > Add a directory to the include path or include a file. See doc of JNAERATOR\_INCLUDE\_PATH
  * **-T([^=]**)(?:=(.**))?** <name: String> <value: String>
> > Define a type symbol
  * **-U(.+)** <symbolName: String>
> > Undefine a preprocessor symbol after the autoconfiguration phase.
  * **-addRootDir** <dir: ExistingDir>
> > Remove this directory from the path of descendant source files in the generated documentation.
  * **-allowedFileExts** <extensions: String>
> > Colon-separated list of file extensions used to restrict files used when recursing on directories, or "`*`" to parse all files (by default = h:hpp:hxx)
  * **-arch** <archName: linux\_x64 | linux\_x86 | armeabi | sunos\_x86 | sunos\_sparc | darwin\_universal | win32 | win64>
> > Define the current architecture for libraries (state variable)
  * **-beanStructs**
> > Generate getters and setters for struct fields (JNA & JNAerator runtimes only)
  * **-beautifyNames**
> > Transform C names to Java-looking names : some\_func() => someFunc()
  * **-bridgeSupportOut** <outFile: OutputFile>
> > Write the definitions extracted from bridgesupport files in a file (automatically set when ${Verbose} is used).
  * **-callbacksInvokeMethodName** <methodName: String>
> > Name of the invocation method of callbacks ('apply' by default)
  * **-choices** <choicesFile: ExistingFile>
> > Read the function alternative choices from a file in the format used by -choicesOut.
  * **-choicesOut** <outFile: OutputFile>
> > Write the function alternative choices made (automatically set when ${Verbose} is used).
  * **-com**
> > Generate Microsoft COM (C++) bindings.
  * **-convertBodies**
> > Experimental conversion of function bodies to equivalent Java code (BridJ only).
  * **-defaultLibrary** <libName: String>
> > Name of output library for elements declared in files not covered by a ${CurrentLibrary} switch
  * **-direct**
> > JNAerate libraries that use JNA's faster direct call convention
  * **-dontCastConstants**
> > Don't cast generated constants
  * **-emptyStructsAsForwardDecls**
> > Treat empty structs as forward declarations
  * **-entryClass** <entryClassName: String>
> > Generate a class _entryclassName.EntryClassName_ that will contain all of the jnaerated libraries instances. User code will just need to static import or derive from this class to access to the instances.
  * **-extractionOut** <outFile: OutputFile>
> > Write the symbols extracted from libraries in a file (automatically set when ${Verbose} is used).
  * **-f**
> > Force the overwrite of existing files
  * **-fpreprocessed**
> > Consider source files as being already preprocessed (preprocessor won't be run)
  * **-framework** <frameworkName: String>
> > JNAerate a framework using its headers and its `*`.bridgesupport files if available
  * **-frameworksPath** <path1:path2...: String>
> > See doc of JNAERATOR\_FRAMEWORKS\_PATH
  * **-gccLong**
> > Use GCC convention for size of 'long' (4 bytes on 32 bits platforms, 8 bytes on 64 bits platforms).
  * **-genCPlusPlus**
> > [Experimental, Not working at all] Generate C++ classes.
  * **-genPrivateMembers**
> > Generate wrappers for private fields and methods (will be protected and deprecated).
  * **-genRawBindings**
> > Generate raw bindings amenable for assembler optimizations.
  * **-gui**
> > Show minimalist progression GUI
  * **-ifRegexMatch** <javaProperty: String> <regex: String> <thenArg: String> <elseArg: String>
> > Conditional evaluation of an argument if a java system property matches a regular expression
  * **-jar** <outFile: OutputFile>
> > Jar file where all generated sources and the compiled classes go
  * **-libFile** <resourceFile: ExistingFile>
> > Bundle the provided file with the JNAerated JAR so that it is extracted with the library when it is first used.
  * **-library** <libName: String>
> > Define the name of the output library. This is a state parameter, it will affect all files listed after it, until another -library switch is provided. It does not affect sources included from a project file (Visual Studio...).
> > C functions exported in library "test" will end up in class "TestLibrary", for instance.
> > The name of the library is the one fed to JNA to find the shared library, so library "test" must be in "test.dll" on Windows, "libtest.dylib" on Mac OS X and  "libtest.so" on other Unices.
> > Note that a special hack is done for library "c" on Windows systems : the output name is set to "msvcrt" instead of "c".

  * **-libraryNamingPrefixes** <commaSeparatedPrefixes: String>
> > Define prefixes commonly used in the library so that reification of functions is optimal (See -reification)
  * **-limitComments**
> > Avoid useless comments (source file + line, skipped items...)
  * **-macrosOut** <outFile: OutputFile>
> > Write the preprocessor macros in a file (automatically set when ${Verbose} is used).
  * **-mavenArtifactId** <artifactId: String>
> > Set artifact id of the generated Maven project
  * **-mavenGroupId** <groupId: String>
> > Set group id of the generated Maven project
  * **-mavenVersion** <version: String>
> > Set version of the generated Maven project
  * **-maxConstrFields** <fieldCount: Int>
> > Maximum number of fields allowed for structure fields constructors. If a struct has more fields, it will only get a default constructor.
  * **-mode** <mode: 'Jar' : JAR with bindings only | 'StandaloneJar' : JAR with bindings and runtime dependencies | 'Directory' : Bindings sources in simple file hierarchy | 'Maven' : Bindings sources in Maven project ready to build | 'AutoGeneratedMaven' : Maven project that automatically regenerates its bindings>
> > Choose the output mode of JNAerator
  * **-noAuto**
> > No auto-configuration of preprocessor symbols and paths
  * **-noAutoImport**
> > Don't add import statements automatically to output java source files
  * **-noComments**
> > Don't output any member comment.
  * **-noMangling**
> > Don't output any C++ name mangling information (may cause C++-decorated symbols not to be found at execution time).
  * **-noPrimitiveArrays**
> > Never output primitive arrays for function arguments (use NIO buffers instead)
  * **-noStringReturns**
> > Prevent const char`*` and const wchar\_t`*` return types from being converted to String and WString.
  * **-nocpp**
> > Do not define the cplusplus symbol
  * **-o** <outDir: OutputDir>
> > Output directory for all artifacts
  * **-onlineDoc** <linkDisplayFormat: MessageFormat> <urlMessageFormat: MessageFormat>
> > Define a format for online documentation URLs (uses MessageFormat syntax, with arg 0 being the name of the function / structure).
  * **-package** <forcedPackageName: String>
> > Set the Java package in which all the output will reside (by default, set to the library name).
  * **-parseChunks**
> > Splits the pre-processor output into multiple smaller parts and parse them separately (in theory everything should be parsed in one chunk, but in practice this means errors are not isolated from the rest of the parsing)
  * **-preferJavac**
> > Use Sun's Javac compiler instead of Eclipse's ecj, if possible
  * **-preprocessingOut** <outFile: OutputFile>
> > Write the preprocessor output in a file (automatically set when ${Verbose} is used).
  * **-project** <solutionFile: ExistingFile> <"Config|Platform": String>
> > Read Visual Studio 2008 project or solution file and use the configuration specified (e.g. "Release|Win32").
  * **-reification**
> > Automatically create OO shortcuts for functions that look like methods (typedPtr.someFunc() for someFunc(typedPtr))
  * **-removeInlineAsm**
> > Remove inline asm from preprocessed source, useful when its unsupported syntax makes parsing to fail.
  * **-root(?:Package)?** <package: String>
> > Define the root package for all output classes
  * **-runtime** <enum: JNA | JNAerator (based on JNA) | BridJ (faster runtime that supports C++)>
> > Choose target runtime library between JNA, JNAerator (based on JNA), BridJ (faster runtime that supports C++) (default: BridJ (faster runtime that supports C++)).
  * **-scalaOut** <outDir: OutputDir>
> > [Experimental](Experimental.md) Output Scala wrappers (callbacks implicits...)
  * **-scalaStructSetters**
> > Generate Scala-style setters for BridJ structs (with a name like fieldName_$eq)
  * **-scanSymbols**
> > Extract, unmangle and parse the symbols all listed shared libraries
  * **-sizeAsLong**
> > Treat size\_t and ptrdiff\_t values as 'long' values. ONLY HERE FOR COMPATIBILITY WITH PREVIOUS VERSIONS, WILL EVENTUALLY BE REMOVED.
  * **-skipIncludedFrameworks**
> > Skip Included Frameworks
  * **-skipLibraryInstance**
> > Skip library instance declarations
  * **-structsInLibrary**
> > Force structs to be JNAerated as inner classes of their declaring libraries (otherwise, each top-level structure is defined as a top-level class in its library's package)
  * **-studio**
> > Launch JNAeratorStudio
  * **-synchronized**
> > Generate synchronized native methods
  * **-v(?:erbose)?**
> > Verbose output (both console and files)
  * **-wcharAsShort**
> > Force treatment of wchar\_t as short (char by default)
  * **-wikiHelp**
> > Output a wiki-friendly help
  * **@(.+)?** <argumentsFile.jnaerator: ExistingFile>
> > Read command-line arguments from a file. File may contain multiple lines (those beginning with "//" will be skipped), file wildcards will be resolved within the file content, as well as variables substitutions : $(someEnvOrJavaVarName), with $(DIR) being the parent directory of the current arguments file._

# Environment Variables #

All of these variables may be overridden by setting the environment variable (` set VAR=value ` on Windows, ` export VAR=value ` on most unices) or through Java properties (` java -DVAR=value -jar jnaerator.jar ... `).

  * VISUAL\_STUDIO\_HOME =
```
C:\Program Files\Microsoft Visual Studio 9.0 
```
  * WINDOWS\_SDK\_HOME
```
C:\Program Files\Microsoft SDKs\Windows\v6.0A
```
  * VISUAL\_STUDIO\_INCLUDES = VISUAL\_STUDIO\_HOME;WINDOWS\_SDK\_HOME
  * JNAERATOR\_INCLUDE\_PATH : has the following platform-dependent default values :
    * Windows : uses VISUAL\_STUDIO\_INCLUDES
    * Mac OS X : ` /Developer/SDKs/MacOSX10.4u.sdk/usr/include:. `
    * Current dir on any other platform

  * JNAERATOR\_FRAMEWORKS\_PATH is only set on Mac OS X :
```
/System/Library/Frameworks/CoreServices.framework/Versions/Current/Frameworks:\
/System/Library/Frameworks/ApplicationServices.framework/Versions/Current/Frameworks:\
/System/Library/Frameworks:\
/Library/Frameworks:\
~/Library/Frameworks 
```
  * library.Xxx (Java property) or XXX\_LIBRARY (env. variable) : path to the binary  of library Xxx.