package com.sunexample.demoforandroidxandkotlin.designPatterns

/**
 * factory
 */

interface Translator {
    fun identify(wavePath: String, identifyCallaBack: IdentifyCallaBack)
    fun translate(traText: String, translateCallaBack: TranslateCallaBack)
}

interface IdentifyCallaBack {
    fun identifyStart()
    fun identifyStop()
    fun identifyFail()

}

interface TranslateCallaBack {
    fun translateStart()
    fun translateStop()
    fun translateFail()

}


class GoogleTranslator : Translator {
    override fun identify(wavePath: String, identifyCallaback: IdentifyCallaBack) {

    }

    override fun translate(traText: String, translateCallaback: TranslateCallaBack) {

    }

}


class ChatGptTranslator : Translator {

    override fun identify(wavePath: String, identifyCallaBack: IdentifyCallaBack) {

    }

    override fun translate(traText: String, translateCallaback: TranslateCallaBack) {
    }

}


class TranslateFactory {
    fun getTranslator(gender: String): Translator {
        return when (gender) {
            "google" -> {
                GoogleTranslator()
            }

            "gpt" -> {
                ChatGptTranslator()
            }

            else -> {
                GoogleTranslator()
            }
        }
    }
}


fun main() {
    val factory = TranslateFactory()
    val translate: Translator = factory.getTranslator("google")
    translate.identify("path", object : IdentifyCallaBack {
        override fun identifyStart() {
        }

        override fun identifyStop() {
        }

        override fun identifyFail() {
        }

    })

    translate.translate("translateContent", object : TranslateCallaBack {
        override fun translateStart() {
        }

        override fun translateStop() {
        }

        override fun translateFail() {
        }

    })

}


