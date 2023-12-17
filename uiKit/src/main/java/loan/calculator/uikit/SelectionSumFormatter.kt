package loan.calculator.uikit

class SelectionSumFormatter {

    data class SumTextModel(
        val sumText: String,
        val sumSelection: Int,
    )

    private var lastMoneyString = ""

    fun getSumSelectionAndText(moneyStringRaw: String, selectionEnd: Int): SumTextModel? {
        if (moneyStringRaw != lastMoneyString) {
            val selectionIndex = selectionEnd
            //during checkout!!! can be reset to false or traversed to true
            var focusInLastIndex = moneyStringRaw.length == selectionIndex
            //split strings into a list of pairs in which focus will be also saved
            val moneyStringMutableList = moneyStringRaw.toMutableList()
                .mapIndexed { index, char -> Pair<Char, Boolean>(char, index == selectionIndex /*we will always put the cursor BEFORE the element itself, in which the focus is true*/) }
                .toMutableList()
            //we change all the elements that are with the chars that we need to invalidate
            moneyStringMutableList.forEachIndexed { index, pair ->
                moneyStringMutableList[index] = when (pair.first) {
                    ',' -> pair.copy(first = '.')
                    else -> pair
                }
            }
            //remove non-numbers and move focus if element with focus is removed
            var focusOnNextDigit: Boolean = false
            moneyStringMutableList.apply {
                val localList = mapNotNull {
                    if (!it.first.isDigit() && it.first != '.') {
                        //to be deleted, not a dot or a number
                        if (it.second) focusOnNextDigit = true
                        null
                    } else {
                        //dot or digit, not to be deleted
                        if (focusOnNextDigit) {
                            focusOnNextDigit = false
                            it.copy(second = true)
                        } else
                            it
                    }
                }
                clear()
                addAll(localList)
            }

            //now we have filtered elements and with correct focus, but they can still contain
            // 0. two or more dots
            // 1. doesnt contains dots at all
            // 2. many decimal places, chars after dot
            // 3. no decimal places before dot
            // 3.1. one decimal places after digit, one char after dot
            // 4. no decimal places after digit, no chars after dot
            // 5. zeros ahead
            // 6. start input after zero

            //0. two dots, we can't put a second dot!
            //first find a new point, then delete a new point
            //there may not be a new point, but there may be two or more points, the value can be inserted! - this is impossible to process, in this case we replace the entire line with zeros
            if (moneyStringMutableList.count { it.first == '.' } > 1) {
                val dotsCount = moneyStringMutableList.count { it.first == '.' } //it's better to search twice than one more global variable
                moneyStringMutableList.apply {
                    //check that the dot was set by the user
                    val userEnteredDot = this.indexOfFirst { it.second }.let { this.getOrNull(it - 1)?.first == '.' }
                    when {
                        //if there are more than two points, then zeros will have to be inserted here, since it is impossible to process this correctly
                        dotsCount > 2 -> {
                            this.clear()
                            this.addAll(listOf(Pair('0', false), Pair('.', true), Pair('0', false), Pair('0', false)))
                        }
                        //we know for sure that the user entered the second point
                        userEnteredDot -> {
                            val indexWithFocus = this.indexOfFirst { it.second }
                            // probably, the point may not necessarily be the first, because we just inserted it
                            if (0 < indexWithFocus && moneyStringMutableList.lastIndex >= indexWithFocus) { //we are sure that we will not jump beyond the list
                                this.removeAt(indexWithFocus - 1) //remove point at previous index
                                //rearrange the focus by point, that is, the focus will always move to the end
                                moneyStringMutableList.forEachIndexed { index, item ->
                                    if (item.second) moneyStringMutableList[index] = item.copy(second = false)
                                }
                                val newIndexWithFocus = this.indexOfFirst { it.first == '.' } + 1
                                moneyStringMutableList.getOrNull(newIndexWithFocus)?.let { moneyStringMutableList[newIndexWithFocus] = it.copy(second = true) }
                            }
                        }
                        //not the user entered the second dot, we can't do anything
                        else -> {
                            this.clear()
                            this.addAll(listOf(Pair('0', false), Pair('.', true), Pair('0', false), Pair('0', false)))
                        }
                    }
                }
            }

            //1. here is no dots
            //check if the dot has been erased
            if (moneyStringMutableList.count { it.first == '.' } == 0) {
                when (moneyStringMutableList.size) {
                    //new elements
                    1 -> {
                        val focusWasSetOnLastElement = focusInLastIndex
                        if (focusWasSetOnLastElement) {
                            focusInLastIndex = false
                        }
                        moneyStringMutableList.add(Pair('.', focusWasSetOnLastElement))
                        moneyStringMutableList.add(Pair('0', false))
                        moneyStringMutableList.add(Pair('0', false))
                    }
                    //dot erased
                    else -> {
                        //remove focus from all elements
                        moneyStringMutableList.forEachIndexed { index, pair ->
                            if (pair.second) {
                                moneyStringMutableList[index] = pair.copy(second = false)
                            }
                        }
                        //put a point on the penultimate index with focus, since it was erased
                        if (moneyStringMutableList.lastIndex > 0)
                            moneyStringMutableList.add(moneyStringMutableList.lastIndex - 1, Pair('.', true))
                    }
                }
            }

            //2. many decimal places, chars after dot
            //there may be more than three characters if value was inserted, if so, just discard the far values
            //if there are three decimal places, we process them on the input
            if (moneyStringMutableList.lastIndex - 2 > moneyStringMutableList.indexOfFirst { it.first == '.' }) {
                //number of characters after the dot
                val symbolsAfterDoyQuality = moneyStringMutableList.lastIndex - moneyStringMutableList.indexOfFirst { it.first == '.' }
                when {
                    symbolsAfterDoyQuality > 3 -> {
                        //count how much to remove from the end
                        val howMuchToRemove = symbolsAfterDoyQuality - 2
                        //check if there is focus in the elements that we will remove, if there is, put the focus at the end
                        (1..howMuchToRemove).forEach { _ -> moneyStringMutableList.removeLast() }
                        if (moneyStringMutableList.indexOfFirst { it.second } == -1) focusInLastIndex = true
                    }
                    symbolsAfterDoyQuality == 3 -> {
                        val typedFirst = moneyStringMutableList[moneyStringMutableList.lastIndex - 1].second //check that the first element of the three has been entered
                        val typedSecond = moneyStringMutableList[moneyStringMutableList.lastIndex].second //check that the second element of the three has been entered
                        val typeThird = focusInLastIndex //check that a trait element of the three was introduced

                        //if we have entered the first element, then we must remove the second, the focus is after the first
                        if (typedFirst) {
                            moneyStringMutableList.removeAt(moneyStringMutableList.lastIndex - 1)
                            moneyStringMutableList[moneyStringMutableList.lastIndex] =
                                moneyStringMutableList[moneyStringMutableList.lastIndex].copy(second = true)
                        }
                        //if we entered the second element, then we must remove the third, focus to the end
                        if (typedSecond) {
                            moneyStringMutableList.removeAt(moneyStringMutableList.lastIndex)
                            focusInLastIndex = true
                        }
                        //if we entered the third element, then we must remove the second, focus to the end
                        if (typeThird) {
                            moneyStringMutableList.removeAt(moneyStringMutableList.lastIndex - 1)
                            focusInLastIndex = true
                        }
                        //if none of the three, just delete the last one
                        if (!typedFirst && !typedSecond && !typeThird) {
                            moneyStringMutableList.removeAt(moneyStringMutableList.lastIndex)
                        }
                    }
                }
            }

            //3. no decimal places before dot
            //put the selector on the next one, and zero on the first element
            if (moneyStringMutableList.firstOrNull()?.first == '.') {
                //if the selector is already somewhere, rearrange it
                val alreadySelectedIndex = moneyStringMutableList.indexOfFirst { it.second }
                if (alreadySelectedIndex != -1) moneyStringMutableList[alreadySelectedIndex] = moneyStringMutableList[alreadySelectedIndex].copy(second = false)
                moneyStringMutableList[0] = moneyStringMutableList[0].copy(second = true)
                moneyStringMutableList.add(0, Pair('0', false))
            }

            //3.1. one decimal place after dot
            if (moneyStringMutableList.getOrNull(moneyStringMutableList.lastIndex - 1)?.first == '.') {
                //if the focus is at the end, then we have removed the last one, and if the focus is on the element, then we have removed the penultimate one
                when {
                    focusInLastIndex -> {
                        focusInLastIndex = false
                        moneyStringMutableList.add(Pair('0', true))
                    }
                    else -> {
                        moneyStringMutableList.add(Pair('0', false))
                    }
                }
            }

            //4. no decimal place after dot
            if (moneyStringMutableList.lastOrNull()?.first == '.') {
                //adding two zeroes
                //if it was determined that the selector is at the last index, put the selector before the dot
                //if there is a selector, set do not set it again
                val gotSelector = moneyStringMutableList.find { it.second }
                moneyStringMutableList.add(Pair('0', gotSelector == null))
                moneyStringMutableList.add(Pair('0', false))
            }

            //5. zeroes before dot
            if (moneyStringMutableList.firstOrNull()?.first == '0') {
                var removedElementWithFocus = false
                while (moneyStringMutableList[0].first == '0' && moneyStringMutableList.getOrNull(1)?.first != '.') {
                    if (moneyStringMutableList[0].second) removedElementWithFocus = true
                    moneyStringMutableList.removeAt(0)
                }
                if (removedElementWithFocus) moneyStringMutableList[0] = moneyStringMutableList[0].copy(second = true)
            }

            //6. We start entering over zero
            //if the second element is zero, has focus and the next point, then we enter over the zero
            if (
                moneyStringMutableList.getOrNull(1)?.first == '0' &&
                moneyStringMutableList.getOrNull(1)?.second == true &&
                moneyStringMutableList.getOrNull(2)?.first == '.'
            ) {
                moneyStringMutableList.removeAt(1)
                moneyStringMutableList[1] = moneyStringMutableList[1].copy(second = true)
            }

            //new format
            moneyStringMutableList.formatString()

            val moneyString = moneyStringMutableList.joinToString("") { it.first.toString() }
            val sumTextModel = SumTextModel(
                sumText = moneyString,
                sumSelection = run {
                    val index = moneyStringMutableList.indexOfFirst { it.second }
                    if (index <= 0) {
                        if (focusInLastIndex) moneyStringMutableList.size else 0
                    } else index
                }
            )

            lastMoneyString = sumTextModel.sumText

            return sumTextModel
        } else {
            return null
        }
    }

    /**
     * @param moneyString have to be convertable to double, not spaces or some other stuff
     */

    fun format(moneyString: String): String =
        (getSumSelectionAndText(moneyString, 0)
            ?.sumText ?: "0.00")

    private fun MutableList<Pair<Char, Boolean>>.formatString() {
        val lastIndex = this.lastIndex
        val length = this.size
        //if the focus is on the previous one, we give it to the next one, the lambda is taken out for reuse
        val checkFocus: (index: Int) -> Unit = { index ->
            if (this.getOrNull(index + 1)?.second == true) {
                this[index] = this[index].copy(second = true)
                this[index + 1] = this[index + 1].copy(second = false)
            }
        }
        for (index in lastIndex downTo 0) {
            //put a space first from the right, but not at the zero position
            if (lastIndex - 5 == index && index != 0) {
                add(index, Pair(first = ' ', second = false))
                //if the focus is on the previous one, we give it to the next one
                checkFocus(index)
            } else
            //put a space, not the first from the right, not in the zero position
                if (index < lastIndex - 5 && (length - index) % 3 == 0 && index != 0) {
                    add(index, Pair(first = ' ', second = false))
                    //if the focus is on the previous one, we give it to the next one
                    checkFocus(index)
                }
        }
    }
}
