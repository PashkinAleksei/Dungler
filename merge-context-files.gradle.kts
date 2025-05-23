import java.io.File

tasks.register("merge-context-files") {
    group = "custom"
    description = "Merge files and folders into one file with path markers"

    val outputFile = layout.buildDirectory.file("generated/assets/MergedContextFiles.txt")

    val paths = listOf(
        "/Users/alekse.pashkin/AndroidStudioProjects/Dungler/app/src/main/java/ru/lemonapes/dungler/navigation/character/skills",
        "/Users/alekse.pashkin/AndroidStudioProjects/Dungler/app/src/main/java/ru/lemonapes/dungler/network/endpoints",
        "/Users/alekse.pashkin/AndroidStudioProjects/Dungler/app/src/main/java/ru/lemonapes/dungler/navigation/character/equipment",
        "/Users/alekse.pashkin/AndroidStudioProjects/Dungler/app/src/main/java/ru/lemonapes/dungler/navigation/Screens.kt",
        "/Users/alekse.pashkin/AndroidStudioProjects/Dungler/app/src/main/java/ru/lemonapes/dungler/network/endpoints/GetSkills.kt",
        "/Users/alekse.pashkin/AndroidStudioProjects/Dungler/app/src/main/java/ru/lemonapes/dungler/network/endpoints/PatchDeEquipFood.kt",
        "/Users/alekse.pashkin/AndroidStudioProjects/Dungler/app/src/main/java/ru/lemonapes/dungler/network/endpoints/PatchEquipFood.kt",
        "/Users/alekse.pashkin/AndroidStudioProjects/Dungler/app/src/main/java/ru/lemonapes/dungler/network/endpoints/GetEquipment.kt",
        //"",
    )

    val excludePaths = hashSetOf(
        "res/drawable",
        "res/raw",
        "res/mipmap",
        "res/font",
        "res/xml",
    )
    val excludePathSegments = hashSetOf(
        "build",
        "androidTest",
        "detekt-baseline.xml",
        "lint-baseline.xml",
    )

    fun writeContent(file: File, writer: java.io.BufferedWriter, base: String) {
        if (file.isDirectory) {
            if (file.path.split("/").any {
                    it in excludePathSegments || it.firstOrNull() == '.'
                }) return

            for (ep in excludePaths) {
                if (file.path.contains(ep)) return
            }

            //writer.write("//// DIR $base ////\n")
            file.listFiles()?.sortedBy { it.name }?.forEach { child ->
                writeContent(child, writer, "$base/${child.name}")
            }
        } else if (
            file.isFile && file.name.first() != '.' &&
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
            for (path in paths) {
                val fileOrDir = File(path)
                println("file dir $fileOrDir")
                if (fileOrDir.exists()) {
                    writeContent(fileOrDir, writer, path)
                } else {
                    writer.write("//// $path ////\n")
                    writer.write("// FILE OR DIR NOT FOUND\n\n")
                }
            }
        }
        println("Merged files/folders into: ${outputFile.get().asFile.absolutePath}")
    }
}
