import java.io.File

tasks.register("merge-context-files") {
    group = "custom"
    description = "Merge files and folders into one file with path markers"

    val assetsDir = layout.buildDirectory.dir("generated/assets")
    val assetsDirFile = assetsDir.get().asFile
    if (!assetsDirFile.exists()) {
        assetsDirFile.mkdirs()
    }

    val includePathsFile = assetsDirFile.resolve("includePaths.txt")
    if (!includePathsFile.exists()) {
        includePathsFile.writeText(
            "src" + System.lineSeparator()
        )
    }

    val excludePathsFile = assetsDirFile.resolve("excludePaths.txt")
    if (!excludePathsFile.exists()) {
        excludePathsFile.writeText(
            """
            res/drawable
            res/raw
            res/mipmap
            res/font
            res/xml
            """.trimIndent() + System.lineSeparator()
        )
    }

    val excludePathSegmentsFile = assetsDirFile.resolve("excludePathSegments.txt")
    if (!excludePathSegmentsFile.exists()) {
        excludePathSegmentsFile.writeText(
            """
            build
            androidTest
            detekt-baseline.xml
            lint-baseline.xml
            """.trimIndent() + System.lineSeparator()
        )
    }

    val includePaths = includePathsFile.readLines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
    val excludePaths = excludePathsFile.readLines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .toHashSet()
    val excludePathSegments = excludePathSegmentsFile.readLines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .toHashSet()

    val outputFile = layout.buildDirectory.file("generated/assets/MergedContextFiles.txt")

    fun writeContent(file: File, writer: java.io.BufferedWriter) {
        if (file.name.first() == '.') return
        if (excludePaths.any { ep -> file.path.contains(ep) }) return
        if (file.path.split("/").any { seg -> seg in excludePathSegments }) return

        if (file.isDirectory) {
            file.listFiles()?.forEach { child -> writeContent(child, writer) }
        } else if (
            file.isFile && (file.name.endsWith(".xml") || file.name.endsWith(".kt") || file.name.endsWith(".java"))
        ) {
            writer.write("//// ${file.relativeTo(projectDir)} ////\n")
            file.forEachLine { writer.write(it + "\n") }
        }
    }

    outputFile.get().asFile.bufferedWriter().use { writer ->
        includePaths.forEach { path ->
            val fileOrDir = File(path)
            if (fileOrDir.exists()) {
                writeContent(fileOrDir, writer)
            } else {
                writer.write("//// ${fileOrDir.relativeTo(projectDir)} ////\n")
                writer.write("// FILE OR DIR NOT FOUND\n\n")
            }
        }
        writer.write("//// CONTEXT END ////\n")
    }

    println("Merged files/folders into: ${outputFile.get().asFile.absolutePath}")

}

