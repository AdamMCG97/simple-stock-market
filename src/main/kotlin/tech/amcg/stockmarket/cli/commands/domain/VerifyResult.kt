package tech.amcg.stockmarket.cli.commands.domain

class VerifyResult private constructor(val resultType: ResultType, val errorMessage: String?) {

    companion object {
        fun fail(errorMessage: String): VerifyResult {
            return VerifyResult(ResultType.FAIL, errorMessage)
        }
        fun success(): VerifyResult {
            return VerifyResult(ResultType.SUCCESS, null)
        }
    }
}

enum class ResultType {
    SUCCESS, FAIL
}
