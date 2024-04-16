package rodrigo.taskapp.core.presentation.utils

fun String?.useNonBreakingSpace() = this.orEmpty()
    .replace(
        Constants.REGULAR_SPACE_CHARACTER,
        Constants.NON_BREAKABLE_SPACE_UNICODE
    )

object Constants {
    const val REGULAR_SPACE_CHARACTER = ' '
    const val NON_BREAKABLE_SPACE_UNICODE = '\u00A0'
}
