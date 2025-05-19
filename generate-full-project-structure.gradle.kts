tasks.register("generate-full-project-structure") {
    group = "Custom Tasks"
    description =
        "Generates a unified list of all files under src/main/java and files directly in src/main for all Android modules."

    val outputFile = layout.buildDirectory.file("generated/assets/FullProjectStructure.txt")

    doLast {
        val outFile = outputFile.get().asFile
        outFile.parentFile.mkdirs()
        outFile.bufferedWriter(Charsets.UTF_8).use { writer ->
            // Precompile regex patterns for performance
            val classPattern = Regex(
                """\b(class|object|interface|enum)\s+(\w+)"""
                    .trimIndent()
            )
            val kotlinFieldPattern = Regex(
                """\b(public|protected)?\s*(static)?\s*(val|var)\s+(\w+)\s*:\s*([^\s=]+)"""
                    .trimIndent()
            )
            val kotlinFuncPattern = Regex(
                """\b(public|protected)?\s*(static)?\s*fun\s+(\w+)\s*\([^)]*\)\s*:\s*([^\s{]+)"""
                    .trimIndent()
            )
            val javaFieldPattern = Regex(
                """\b(public|protected)?\s*(static)?\s*([\w<>]+)\s+(\w+)\s*(?:=|;)"""
                    .trimIndent()
            )
            val javaFuncPattern = Regex(
                """\b(public|protected)?\s*(static)?\s*([\w<>]+)\s+(\w+)\s*\("""
                    .trimIndent()
            )

            // Iterate Android subprojects
            rootProject.subprojects
                .filter { proj ->
                    proj.plugins.hasPlugin("com.android.library") || proj.plugins.hasPlugin("com.android.application")
                }
                .forEach { mod ->
                    // Module header
                    writer.appendLine("=== Module: ${mod.name} ===")
                    writer.appendLine()

                    val moduleRoot = mod.projectDir
                    // Use fileTree to limit scanned files
                    val tree = mod.fileTree("src/main") {
                        include("java/**/*.kt", "java/**/*.java", "AndroidManifest.xml")
                    }

                    tree.sorted().forEach { file ->
                        val relPath = file.relativeTo(moduleRoot).path
                        writer.appendLine(relPath)

                        if (relPath.endsWith(".kt") || relPath.endsWith(".java")) {
                            val classStack = mutableListOf<String>()
                            file.useLines { lines ->
                                lines.forEach { line ->
                                    classPattern.find(line)?.destructured?.let { (type, name) ->
                                        writer.appendLine("    ".repeat(classStack.size) + "- $type: $name")
                                        classStack += name
                                    }
                                    if (!line.contains("private")) {
                                        if (relPath.endsWith(".kt")) {
                                            kotlinFieldPattern.find(line)?.destructured?.let { (_, staticKw, _, nameField, typeField) ->
                                                val prefix = if (staticKw.isNotBlank()) "static " else ""
                                                writer.appendLine("    ".repeat(classStack.size + 1) + "-$prefix field: $nameField : $typeField")
                                            }
                                            kotlinFuncPattern.find(line)?.destructured?.let { (_, staticKw, fnName, returnType) ->
                                                val prefix = if (staticKw.isNotBlank()) "static " else ""
                                                writer.appendLine("    ".repeat(classStack.size + 1) + "-$prefix function: $fnName() : $returnType")
                                            }
                                        } else {
                                            javaFieldPattern.find(line)?.destructured?.let { (_, staticKw, typeField, nameField) ->
                                                val prefix = if (staticKw.isNotBlank()) "static " else ""
                                                writer.appendLine("    ".repeat(classStack.size + 1) + "-$prefix field: $nameField : $typeField")
                                            }
                                            javaFuncPattern.find(line)?.destructured?.let { (_, staticKw, returnType, fnName) ->
                                                val prefix = if (staticKw.isNotBlank()) "static " else ""
                                                writer.appendLine("    ".repeat(classStack.size + 1) + "-$prefix function: $fnName() : $returnType")
                                            }
                                        }
                                    }
                                    if (line.trim() == "}") classStack.removeLastOrNull()
                                }
                            }
                        }
                        writer.appendLine()
                    }
                    println("✅ Generated unified file/class list for Module: ${mod.name}")
                }
        }
        println("✅ Generated unified file/class list at: ${outputFile.get().asFile.absolutePath}")
    }
}