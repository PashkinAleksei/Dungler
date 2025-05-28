tasks.register("accept-result-files") {
    group = "custom"
    description = "Extract files from MergeResultFiles.txt and recreate them in their original locations"

    val mergedFile = layout.buildDirectory.file("generated/assets/ResultToAccept.txt").get().asFile

    doLast {
        if (!mergedFile.exists()) {
            println("Merge result file not found: ${mergedFile.absolutePath}")
            return@doLast
        }

        val markerRegex = Regex("^//// (.+) ////$")
        var currentPath: String? = null
        val contentBuffer = StringBuilder()

        fun flushBuffer() {
            currentPath?.let { relativePath ->
                val targetFile = projectDir.resolve(relativePath)
                targetFile.writeText(contentBuffer.toString())
                println("Modified file: $relativePath")
            }
            contentBuffer.clear()
        }

        mergedFile.forEachLine { line ->
            val match = markerRegex.matchEntire(line)
            if (match != null) {
                val matchStr = match.groupValues[1]
                if (matchStr == "CONTEXT END") {
                    return@forEachLine
                }
                if (currentPath != null) {
                    flushBuffer()
                }
                currentPath = matchStr
            } else {
                if (currentPath != null) {
                    contentBuffer.appendLine(line)
                }
            }
        }
        flushBuffer()
    }
}