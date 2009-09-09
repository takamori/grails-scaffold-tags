/*
 * Copyright 2004-2005 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Gant script that installs a ScaffoldTags skin
 * 
 * @author Daiji Takamori
 *
 * @since 0.6
 */

grailsHome = Ant.antProject.properties."environment.GRAILS_HOME"
includeTargets << new File ( "${grailsHome}/Init.groovy" )  

target ('default': "Installs a ScaffoldTags skin") {
    version = "0.7.3"
    pluginHome = "${pluginsHome}/scaffold-tags-${version}" 
    pluginSkins = "${pluginHome}/src/skins"
    appSkins = "${basedir}/src/skins"
    
    // Obtain the skin name; make sure it exists
 	skin = args
 	first = true
 	while (first || !skin) {
 	    first = false
 	    availableSkins = new File(pluginSkins).list().toList()
 	    if (new File(appSkins).exists()) {
	 	    availableSkins.addAll(new File(appSkins).list().toList())
 	    }
 	    if (!skin) {
 	        println "Available skins under grails-app/skins directories:"
 	        availableSkins.each {
 	            println "\t${it}"
 	        }
 	    	println "Skin name not specified. Please enter:"
 	    	skin = System.in.readLine()
 	    }
 	 	if (!availableSkins.contains(skin)) {
 		    println "Skin \"${skin}\" not available"
 		    skin = null
 		}
 	}
    skinDir = "${appSkins}/${skin}"
    if (!new File(skinDir).exists()) {
        skinDir = "${pluginSkins}/${skin}"
    }
    
    // establish initial vars
 	sources = [ scaffolding: "${skinDir}/scaffolding",
 	            templates: "${skinDir}/templates",
 	            layouts: "${skinDir}/layouts",
 	            groovycode: "${skinDir}/src/groovy",
 	            css: "${skinDir}/css" ]
 	targets = [ scaffolding: "${pluginHome}/grails-app/views/scaffolding",
 	            templates: "${basedir}/src/templates/scaffolding",
 	            layouts: "${basedir}/grails-app/views/layouts",
 	            groovycode: "${basedir}/src/groovy",
 	            css: "${basedir}/web-app/css"]
 	clearTargets = [ 'scaffolding' ]
 	sourceFiles = [ index: "${skinDir}/index.gsp" ]
	targetFiles = [ index: "${basedir}/web-app/index.gsp" ]
	
    // prompt the user if any overwrites will occur before starting copy
    println "Checking for pre-existing files..."
	sources.each { sourceType, sourcePath ->
		sourceDir = new File(sourcePath)
	    if (sourceDir.exists()) {
	    	targetPath = targets[sourceType]
	        targetDir = new File(targetPath)
	    	
	    	if (clearTargets.contains(sourceType)) {
	    	    // Prompt the user if the existing dir will be blown away.
	    	    if (targetDir.exists()) {
	    	        println "Warning: Directory ${targetPath} already exists and would be completely erased."
		    		Ant.input(addProperty: "skin.dir.${sourceType}.delete", 
		    		        message: "Delete existing ${sourceType}? ",
    		                validArgs: "y,n")
	    	    }
	    	} else if (targetDir.exists()) {
		    	// Prompt the user if an overwrite will occur
	            def sources = sourceDir.list().toList()
	            def targets = targetDir.list().toList()
	            def overlap = sources?.intersect(targets)
	            if (!overlap?.empty) {
	                println "Warning: Pre-existing files found in ${targetDir}"
	                def hasNewer = false
	                def hasOlder = false
	                def hasDir = false
	                overlap.each { f ->
	                	def overlapTarget = new File(targetDir, f)
	                	def isDir = overlapTarget.directory
	                	print "Warning: ${isDir ? 'Directory' : 'File' } ${f} exists already"
	                    if (overlapTarget.lastModified() <= new File(sourceDir, f).lastModified()) {
	                        println " and would be overwritten."
	                        hasOlder = true
	                    } else {
	                        println " and is newer than source."
	                        hasNewer = true
	                    }
	                	hasDir |= isDir
	                }
	                if (hasOlder || hasDir) {
			    		Ant.input(addProperty: "skin.dir.${sourceType}.copy", 
			    		        message: "Copy on top of existing ${sourceType}? ",
	    		                validArgs: "y,n")
	                }
	                if (Ant.antProject.properties."skin.dir.${sourceType}.copy" != "n" && hasNewer) {
			    		Ant.input(addProperty: "skin.dir.${sourceType}.overwrite", 
			    		        message: "Overwrite files in existing ${sourceType} even if newer? ",
	    		                validArgs: "y,n")
	                }
	            }
	        }
	    }
	}
	sourceFiles.each { sourceType, sourcePath ->
		sourceFile = new File(sourcePath)
	    if (sourceFile.exists()) {
	    	targetPath = targetFiles[sourceType]
	        targetFile = new File(targetPath)
	    	
	    	// Prompt the user if an overwrite will occur
	        if (targetFile.exists()) {
                print "Warning: File ${targetPath} exists already"
                if (targetFile.lastModified() <= sourceFile.lastModified()) {
                    println " and would be overwritten."
		    		Ant.input(addProperty: "skin.file.${sourceType}.copy", 
		    		        message: "Overwrite existing ${sourceType}? ",
			                validArgs: "y,n")
                } else {
                    println " and is newer than source file."
		    		Ant.input(addProperty: "skin.file.${sourceType}.overwrite", 
	    		        message: "Overwrite existing ${sourceType} even if newer? ",
		                validArgs: "y,n")
                }
	        }
	    }
	}

	def doCopy = false
    sources.each { sourceType, sourcePath ->
		sourceDir = new File(sourcePath)
	    if (sourceDir.exists() &&
	        (Ant.antProject.properties."skin.dir.${sourceType}.copy" != "n")) {
	        doCopy = true
	    }
    }
	sourceFiles.each { sourceType, sourcePath ->
		sourceFile = new File(sourcePath)
	    if (sourceFile.exists() &&
	        (Ant.antProject.properties."skin.file.${sourceType}.copy" != "n")) {
	        doCopy = true
	    }
    }
	if (doCopy) {
	    sources.each { sourceType, sourcePath ->
			sourceDir = new File(sourcePath)
		    if (sourceDir.exists() &&
	            (Ant.antProject.properties."skin.dir.${sourceType}.copy" != "n")) {
		    	targetPath = targets[sourceType]
 		        if (Ant.antProject.properties."skin.dir.${sourceType}.delete" == "y") {
		            println "Removing existing ${sourceType}..."
		            Ant.delete(dir: targetPath)
		        }
				def overwrite = (Ant.antProject.properties."skin.dir.${sourceType}.overwrite" != "n")
	    		if (overwrite) {
	    		    println "Installing skin '${skin}' ${sourceType}..."
	    		} else {
					println "Installing only newer files in skin '${skin}' ${sourceType}..."
	    		}
		    	
		    	// Create any directories that don't exist
		        if (!new File(targetPath).exists()) {
		    		Ant.mkdir(dir: targetPath)
		        }
		    	
	    		// copy files
		    	Ant.copy(todir: targetPath, overwrite: overwrite, preservelastmodified: true) {
		    		fileset(dir: sourcePath, includes: "**")
		    	}
		    }
	    }
		sourceFiles.each { sourceType, sourcePath ->
			sourceFile = new File(sourcePath)
		    if (sourceFile.exists() &&
	            (Ant.antProject.properties."skin.file.${sourceType}.copy" != "n")) {
		    	overwrite = false
		    	targetPath = targetFiles[sourceType]
		 		if (Ant.antProject.properties."skin.file.${sourceType}.overwrite" != "n")
					overwrite = true
	    		if (overwrite) {
	    		    println "Installing skin '${skin}' ${sourceType}..."
	    		} else {
					println "Installing skin '${skin}' ${sourceType} only if newer..."
	    		}
		    	
				// copy file
		    	Ant.copy(file: sourcePath, tofile: targetPath, overwrite: overwrite, preservelastmodified: true)
		    }
		}
		println "ScaffoldTags skin ${skin} installed successfully"
	} else {
	    println "No files installed"
	}
}
