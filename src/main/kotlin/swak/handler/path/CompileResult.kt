package swak.handler.path

internal data class CompileResult(
        val regex: Regex,
        val groupNames:List<String>
)