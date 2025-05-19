import java.io.File

tasks.register("merge-context-files") {
    group = "custom"
    description = "Merge files and folders into one file with path markers"

    val paths = listOf(
        "app/src",
        //"",
    )

    val outputFile = layout.buildDirectory.file("generated/assets/MergedContextFiles.txt")
    val excludeDirSet = hashSetOf(
        file("src/main/res/drawable").absolutePath,
        file("src/main/res/raw").absolutePath,
        file("src/main/res/mipmap").absolutePath,
        file("src/main/res/font").absolutePath,
        file("src/main/res/xml").absolutePath,
    )

    fun writeContent(file: File, writer: java.io.BufferedWriter, base: String) {
        if (file.isDirectory) {
            val absolutePath = file.absolutePath
            if (file.absolutePath in excludeDirSet) return

            writer.write("//// DIR $base ////\n")
            file.listFiles()?.sortedBy { it.name }?.forEach { child ->
                writeContent(child, writer, "$base/${child.name}")
            }
        } else if (
            file.isFile &&
            (file.name.endsWith(".xml") || file.name.endsWith(".kt") || file.name.endsWith(".java"))
        ) {
            writer.write("//// FILE $base ////\n")
            file.forEachLine { writer.write(it + "\n") }
            writer.write("\n")
        }
    }


    doLast {
        val outFile = outputFile.get().asFile
        outFile.parentFile.mkdirs()
        outFile.bufferedWriter(Charsets.UTF_8).use { writer ->
            for (relative in paths) {
                val removedAppSegment = relative.substringAfter("/")
                val fileOrDir = file(removedAppSegment)
                println("file dir $fileOrDir")
                if (fileOrDir.exists()) {
                    writeContent(fileOrDir, writer, relative)
                } else {
                    writer.write("//// $relative ////\n")
                    writer.write("// FILE OR DIR NOT FOUND\n\n")
                }
            }
        }
        println("Merged files/folders into: ${outputFile.get().asFile.absolutePath}")
    }
}
