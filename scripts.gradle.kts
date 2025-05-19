tasks.register("generateFileAndClassList") {
    group = "Custom Tasks"
    description =
        "Generates a list of all files under src/main/java and files directly in src/main, with class/object structures, fields (with types) and functions (with return types)."

    val outputDir = layout.buildDirectory.dir("generated/assets")
    val outputFile = outputDir.map { it.file("file_and_class_list.txt") }

    doLast {
        val moduleRoot = project.projectDir
        outputDir.get().asFile.mkdirs()

        outputFile.get().asFile.bufferedWriter(Charsets.UTF_8).use { writer ->
            moduleRoot.walkTopDown()
                .filter { file ->
                    file.isFile && (
                            file.relativeTo(moduleRoot).path.startsWith("src/main/java/") ||
                                    file.parentFile == File(moduleRoot, "src/main")
                            )
                }
                .forEach { file ->
                    val relPath = file.relativeTo(moduleRoot).path
                    writer.appendLine(relPath)

                    if (relPath.endsWith(".kt") || relPath.endsWith(".java")) {
                        val classStack = mutableListOf<String>()
                        file.readLines().forEach { line ->
                            // Классы/объекты/интерфейсы/enum
                            Regex("""\b(class|object|interface|enum)\s+(\w+)""")
                                .find(line)
                                ?.destructured
                                ?.let { (type, name) ->
                                    val indent = "    ".repeat(classStack.size)
                                    writer.appendLine("$indent- $type: $name")
                                    classStack += name
                                }

                            if (!line.contains("private")) {
                                if (relPath.endsWith(".kt")) {
                                    // Kotlin: properties val/var with types
                                    Regex("""\b(public|protected)?\s*(static)?\s*(val|var)\s+(\w+)\s*:\s*([^\s=]+)""")
                                        .find(line)
                                        ?.destructured
                                        ?.let { (_, staticKw, _, nameField, typeField) ->
                                            val prefix = if (staticKw.isNotBlank()) "static " else ""
                                            val indent = "    ".repeat(classStack.size + 1)
                                            writer.appendLine("$indent- ${prefix}field: $nameField : $typeField")
                                        }
                                    // Kotlin: functions with return types
                                    Regex(""""\b(public|protected)?\s*(static)?\s*fun\s+(\w+)\s*\([^)]*\)\s*:\s*([^\s{]+)""")
                                        .find(line)
                                        ?.destructured
                                        ?.let { (_, staticKw, nameFn, returnType) ->
                                            val prefix = if (staticKw.isNotBlank()) "static " else ""
                                            val indent = "    ".repeat(classStack.size + 1)
                                            writer.appendLine("$indent- ${prefix}function: $nameFn() : $returnType")
                                        }
                                } else if (relPath.endsWith(".java")) {
                                    // Java: fields with types
                                    Regex(""""\b(public|protected)?\s*(static)?\s*([\w<>]+)\s+(\w+)\s*(=|;)""")
                                        .find(line)
                                        ?.destructured
                                        ?.let { (_, staticKw, typeField, nameField, _) ->
                                            val prefix = if (staticKw.isNotBlank()) "static " else ""
                                            val indent = "    ".repeat(classStack.size + 1)
                                            writer.appendLine("$indent- ${prefix}field: $nameField : $typeField")
                                        }
                                    // Java: methods with return types
                                    Regex("""\b(public|protected)?\s*(static)?\s*([\w<>]+)\s+(\w+)\s*\(""")
                                        .find(line)
                                        ?.destructured
                                        ?.let { (_, staticKw, returnType, nameFn) ->
                                            val prefix = if (staticKw.isNotBlank()) "static " else ""
                                            val indent = "    ".repeat(classStack.size + 1)
                                            writer.appendLine("$indent- ${prefix}function: $nameFn() : $returnType")
                                        }
                                }
                            }

                            if (line.trim() == "}") {
                                if (classStack.isNotEmpty()) classStack.removeLast()
                            }
                        }
                    }

                    writer.appendLine()
                }
        }

        println("✅ Generated file/class list at: ${outputFile.get().asFile.absolutePath}")
    }
}
