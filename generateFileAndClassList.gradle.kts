tasks.register("generateFileAndClassList") {
    group = "Custom Tasks"
    description =
        "Generates a list of all files under src/main/java and files directly in src/main, with class/object structures, fields, and functions."

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
                                    writer.appendLine("    ".repeat(classStack.size) + "- $type: $name")
                                    classStack += name
                                }

                            // Поля
                            Regex("""\b(public|protected)?\s*(static)?\s*(val|var|[\w<>]+)\s+(\w+)\s*(=|;)""")
                                .find(line)
                                ?.takeIf { !line.contains("private") }
                                ?.let {
                                    val isStatic = if (it.groupValues[2].isNotEmpty()) "static " else ""
                                    val fieldName = it.groupValues[4]
                                    writer.appendLine("    ".repeat(classStack.size + 1) + "- ${isStatic}field: $fieldName")
                                }

                            // Функции
                            Regex("""\b(public|protected)?\s*(static)?\s*fun\s+(\w+)\s*\(""")
                                .find(line)
                                ?.takeIf { !line.contains("private") }
                                ?.let {
                                    val isStatic = if (it.groupValues[2].isNotEmpty()) "static " else ""
                                    val fnName = it.groupValues[3]
                                    writer.appendLine("    ".repeat(classStack.size + 1) + "- ${isStatic}function: $fnName")
                                }

                            // Закрытие вложенности
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
